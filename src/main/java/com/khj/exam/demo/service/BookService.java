package com.khj.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.khj.exam.demo.repository.BookRepository;
import com.khj.exam.demo.utill.Ut;
import com.khj.exam.demo.vo.Book;
import com.khj.exam.demo.vo.ResultData;
import com.khj.exam.demo.vo.Rq;

@Service
public class BookService {
	private BookRepository bookRepository;
	private Rq rq;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

//	public ResultData<Integer> writeArticle(int memberId, int boardId, String title, String body) {
//		articleRepository.writeArticle(memberId, boardId, title, body);
//		int id = articleRepository.getLastInsertId();
//
//		return ResultData.from("S-1", Ut.f("%d번 게시물이 생성되었습니다.", id), "id", id);
//	}

	public List<Book> getForPrintBooks(String searchKeyword,
			String searchKeywordTypeCode, int itemsCountInAPage, int page) {
		int limitStart = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		List<Book> books = bookRepository.getForPrintBooks(searchKeyword, searchKeywordTypeCode, limitStart, limitTake);
//
//	     for (Book book : books) {
//			updateForPrintData(book);
//		}

		return books;
	}

	public Book getForPrintBook(int id) {
		Book book = bookRepository.getForPrintBook(id);

  	updateForPrintData(book);
		return book;
	}

	private void updateForPrintData(Book book) {
		if (book == null) {
			return;
		}

	}
	
	public ResultData rentalBook(int id) {
		int RentalBook=	bookRepository.rentalBook(id);
		bookRepository.returnDate(id);
		if(RentalBook==0)
			return ResultData.from("F-1", "대여 실패하셨습니다.");
		Book book = getForPrintBook(id);
		book.setUser(rq.getLoginedMember());
		return ResultData.from("S-1", "대여 완료되었습니다.", "RentalBook", RentalBook);
	}
	
	public ResultData returnBook(int id) {
		int ReturnBook=	bookRepository.returnBook(id);
		if(ReturnBook==0)
			return ResultData.from("F-1", "반납 실패하셨습니다.");
		return ResultData.from("S-1", "반납 완료되었습니다.", "ReturnBook", ReturnBook);
	}
	
	public ResultData registBook(String title, String writer, String publisher) {
		int RegistBook=	bookRepository.registBook(title,writer,publisher);
		if(RegistBook==0)
			return ResultData.from("F-1", "등록 실패하셨습니다.");
		return ResultData.from("S-1", "등록 완료되었습니다.", "RegistBook", RegistBook);
	}
////
//	public void deleteArticle(int id) {
//		articleRepository.deleteArticle(id);
//	}
//
//	public ResultData<Article> modifyArticle(int id, String title, String body) {
//		articleRepository.modifyArticle(id, title, body);
//
//		Article article = getForPrintArticle(0, id);
//
//		return ResultData.from("S-1", Ut.f("%d번 게시물이 수정되었습니다.", id), "article", article);
//	}
//
//	public ResultData actorCanModify(int actorId, Article article) {
//		if (article == null) {
//			return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
//		}
//
//		if (article.getMemberId() != actorId) {
//			return ResultData.from("F-2", "권한이 없습니다.");
//		}
//
//		return ResultData.from("S-1", "게시물 수정이 가능합니다.");
//	}
//
//	public ResultData actorCanDelete(int actorId, Article article) {
//		if (article == null) {
//			return ResultData.from("F-1", "게시물이 존재하지 않습니다.");
//		}
//
//		if (article.getMemberId() != actorId) {
//			return ResultData.from("F-2", "권한이 없습니다.");
//		}
//
//		return ResultData.from("S-1", "게시물 삭제가 가능합니다.");
//	}
//
	public int getBooksCount(String searchKeywordTypeCode, String searchKeyword) {
		return bookRepository.getBooksCount(searchKeywordTypeCode, searchKeyword);
	}

//	public ResultData<Integer> increaseHitCount(int id) {
//		int affectedRowsCount = articleRepository.increaseHitCount(id);
//
//		if (affectedRowsCount == 0) {
//			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
//		}
//
//		return ResultData.from("S-1", "조회수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
//	}
//
//	public int getArticleHitCount(int id) {
//		return articleRepository.getArticleHitCount(id);
//	}
//
//	public ResultData increaseGoodReactionPoint(int relId) {
//		int affectedRowsCount = articleRepository.increaseGoodReactionPoint(relId);
//
//		if (affectedRowsCount == 0) {
//			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
//		}
//
//		return ResultData.from("S-1", "좋아요 수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
//	}
//
//	public ResultData increaseBadReactionPoint(int relId) {
//		int affectedRowsCount = articleRepository.increaseBadReactionPoint(relId);
//
//		if (affectedRowsCount == 0) {
//			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
//		}
//
//		return ResultData.from("S-1", "싫어요 수가 증가되었습니다.", "affectedRowsCount", affectedRowsCount);
//	}
//
//	public ResultData decreaseGoodReactionPoint(int relId) {
//		int affectedRowsCount = articleRepository.decreaseGoodReactionPoint(relId);
//
//		if (affectedRowsCount == 0) {
//			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
//		}
//
//		return ResultData.from("S-1", "좋아요 수가 감소되었습니다.", "affectedRowsCount", affectedRowsCount);
//	}
//
//	public ResultData decreaseBadReactionPoint(int relId) {
//		int affectedRowsCount = articleRepository.decreaseBadReactionPoint(relId);
//
//		if (affectedRowsCount == 0) {
//			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.", "affectedRowsCount", affectedRowsCount);
//		}
//
//		return ResultData.from("S-1", "싫어요 수가 감소되었습니다.", "affectedRowsCount", affectedRowsCount);
//	}
//	
//	public Article getArticle(int id) {
//		return articleRepository.getArticle(id);
//	}
}