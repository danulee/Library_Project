package com.khj.exam.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khj.exam.demo.service.BoardService;
import com.khj.exam.demo.service.ReactionPointService;
import com.khj.exam.demo.service.BookService;
import com.khj.exam.demo.service.ReplyService;
import com.khj.exam.demo.utill.Ut;
import com.khj.exam.demo.vo.Board;
import com.khj.exam.demo.vo.Reply;
import com.khj.exam.demo.vo.ResultData;
import com.khj.exam.demo.vo.Rq;
import com.khj.exam.demo.vo.Book;

@Controller
public class UsrBookController {
	private BoardService boardService;
	private BookService bookService;
	private Rq rq;
	private ReactionPointService reactionPointService;
	private ReplyService replyService;

	public UsrBookController(BookService bookService, Rq rq) {
		this.bookService = bookService;
		this.rq = rq;
	}

	@RequestMapping("/usr/book/list")
	public String showList(Model model, @RequestParam(defaultValue = "title,writer") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		int booksCount = bookService.getBooksCount(searchKeywordTypeCode, searchKeyword);

		int itemsCountInAPage = 10;
		int pagesCount = (int) Math.ceil((double) booksCount / itemsCountInAPage);
		List<Book> books = bookService.getForPrintBooks(searchKeyword, searchKeywordTypeCode, itemsCountInAPage, page);
		model.addAttribute("rq",this.rq);
		model.addAttribute("page", page);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("booksCount", booksCount);
		model.addAttribute("books", books);
		
		return "usr/book/list";
	}
	@RequestMapping("/usr/book/doRental")
	@ResponseBody
	public String RentalBook(int id,String replaceUri) {
		ResultData<Integer> RentalBook = bookService.rentalBook(id);
		return  rq.jsReplace(RentalBook.getMsg(), replaceUri);
	}
	
	@RequestMapping("/usr/book/doReturn")
	@ResponseBody
	public String ReturnBook(int id,String replaceUri) {
		ResultData<Integer> ReturnBook = bookService.returnBook(id);

		return  rq.jsReplace(ReturnBook.getMsg(), replaceUri);
	}
	

	@RequestMapping("/usr/book/regist")
	public String RegistBook() {
		return "usr/book/regist";
	}
	
	
	@RequestMapping("/usr/book/doRegist")
	@ResponseBody
	public String doRegist(String title, String writer, String publisher, @RequestParam(defaultValue = "/") String afterLoginUri) {
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("F-1", "??????(???)??? ??????????????????.");
		}

		if (Ut.empty(writer)) {
			return rq.jsHistoryBack("F-2", "??????(???)??? ??????????????????.");
		}

		if (Ut.empty(publisher)) {
			return rq.jsHistoryBack("F-3", "?????????(???)??? ??????????????????.");
		}

	
		ResultData<Integer> registRd = bookService.registBook(title, writer, publisher);

		if (registRd.isFail()) {
			return rq.jsHistoryBack(registRd.getResultCode(), registRd.getMsg());
		}

		String afterJoinUri = "../book/regist?afterLoginUri=" + Ut.getUriEncoded(afterLoginUri);

		return rq.jsReplace("???????????? ?????? ???????????????.", afterJoinUri);
	}
	
//	@RequestMapping("/usr/article/detail")
//		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
//
//		model.addAttribute("article", article);
//		
//		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id);
//		int repliesCount = replies.size();
//		model.addAttribute("replies", replies);
//
//		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(),
//				"article", id);
//
//		model.addAttribute("actorCanMakeReaction", actorCanMakeReactionPointRd.isSuccess());
//
//		if (actorCanMakeReactionPointRd.getResultCode().equals("F-2")) {
//			int sumReactionPointByeMemberId = (int) actorCanMakeReactionPointRd.getData1();
//
//			if (sumReactionPointByeMemberId > 0) {
//				model.addAttribute("actorCanCancelGoodReaction", true);
//			} else {
//				model.addAttribute("actorCanCancelBadReaction", true);
//			}
//		}
//
//		return "usr/article/detail";
//	}
//
//	@RequestMapping("/usr/article/doIncreaseHitCountRd")
//	@ResponseBody
//	public ResultData<Integer> doIncreaseHitCountRd(int id) {
//		ResultData<Integer> increaseHitCountRd = articleService.increaseHitCount(id);
//
//		if (increaseHitCountRd.isFail()) {
//			return increaseHitCountRd;
//		}
//
//		ResultData<Integer> rd = ResultData.newData(increaseHitCountRd, "hitCount",
//				articleService.getArticleHitCount(id));
//
//		rd.setData2("id", id);
//
//		return rd;
//	}
//
//	@RequestMapping("/usr/article/getArticle")
//	@ResponseBody
//	public ResultData<Article> getArticle(int id) {
//		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
//
//		if (article == null) {
//			return ResultData.from("F-1", Ut.f("%d??? ???????????? ???????????? ????????????.", id));
//		}
//
//		return ResultData.from("S-1", Ut.f("%d??? ??????????????????.", id), "article", article);
//	}
//
//	@RequestMapping("/usr/article/doDelete")
//	@ResponseBody
//	public String doDelete(int id) {
//		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
//
//		if (article == null) {
//			ResultData.from("F-1", Ut.f("%d??? ???????????? ???????????? ????????????.", id));
//		}
//
//		if (article.getMemberId() != rq.getLoginedMemberId()) {
//			return rq.jsHistoryBack("????????? ????????????.");
//		}
//
//		articleService.deleteArticle(id);
//
//		return rq.jsReplace(Ut.f("%d??? ???????????? ?????????????????????.", id), "../article/list");
//	}
//
//	@RequestMapping("/usr/article/modify")
//	public String showModify(Model model, int id) {
//		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
//
//		if (article == null) {
//			return rq.historyBackJsOnView(Ut.f("%d??? ???????????? ???????????? ????????????.", id));
//		}
//
//		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
//
//		if (actorCanModifyRd.isFail()) {
//			return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
//		}
//
//		model.addAttribute("article", article);
//
//		return "usr/article/modify";
//	}
//
//	@RequestMapping("/usr/article/doModify")
//	@ResponseBody
//	public String doModify(int id, String title, String body) {
//		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
//
//		if (article == null) {
//			return rq.jsHistoryBack(Ut.f("%d??? ???????????? ???????????? ????????????.", id));
//		}
//
//		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
//
//		if (actorCanModifyRd.isFail()) {
//			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
//		}
//
//		articleService.modifyArticle(id, title, body);
//
//		return rq.jsReplace(Ut.f("%d??? ?????? ?????????????????????.", id), Ut.f("../article/detail?id=%d", id));
//	}
//
//	@RequestMapping("/usr/article/write")
//	public String showWrite(Model model) {
//		return "usr/article/write";
//	}
//
//	@RequestMapping("/usr/article/doWrite")
//	@ResponseBody
//	public String doWrite(@RequestParam(defaultValue = "1") int boardId, String title, String body, String replaceUri) {
//		if (Ut.empty(boardId)) {
//			return rq.jsHistoryBack("???????????? ??????????????????.");
//		}
//		
//		if (Ut.empty(title)) {
//			return rq.jsHistoryBack("title(???)??? ??????????????????.");
//		}
//
//		if (Ut.empty(body)) {
//			return rq.jsHistoryBack("body(???)??? ??????????????????.");
//		}
//
//		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), boardId, title, body);
//		int id = writeArticleRd.getData1();
//
//		if (Ut.empty(replaceUri)) {
//			replaceUri = Ut.f("../article/detail?id=%d", id);
//		}
//
//		return rq.jsReplace(Ut.f("%d??? ?????? ?????????????????????.", id), replaceUri);
//	}
}