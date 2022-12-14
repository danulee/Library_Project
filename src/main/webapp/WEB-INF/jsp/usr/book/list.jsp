<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="pageTitle" value="책 리스트" />
<%@ include file="../common/head.jspf"%>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<div class="flex">
			<div>
				게시물 개수 : <span class="text-blue-700">${booksCount}</span>개
			</div>
			<input type="hidden" name="replaceUri" value="${rq.currentUri}" />
			<div class="flex-grow"></div>
			<form class="flex">
				<select data-value="${param.searchKeywordTypeCode}"
					name="searchKeywordTypeCode" class="select select-bordered">
					<option disabled="disabled">검색타입</option>
					<option value="title">제목</option>
					<option value="writer">작가</option>
					<option value="title,writer">제목,작가</option>
				</select> <input name="searchKeyword" type="text"
					class="ml-2 w-72 input input-bordered" placeholder="검색어"
					maxlength="20" value="${param.searchKeyword}" />

				<button type="submit" class="ml-2 btn btn-primary">검색</button>
			</form>
		</div>
		<div class="mt-3">
			<table class="table table-fixed w-full">
				<colgroup>
					<col width="50" />
					<col width="100" />
					<col width="100" />
					<col width="100" />
					<col width="100" />
					<col width="200" />
					<col />
				</colgroup>
				<thead>
					<tr>
						<th>번호</th>
						<th>등록날짜</th>
						<th>제목</th>
						<th>작가</th>
						<th>출판사</th>
						<th>대여 가능 여부</th>
						<th>대여/반납</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="book" items="${books}">
						<tr class="hover">
							<th>${book.id}</th>
							<td>${book.forPrintType1RegDate}</td>
							<td>${book.title}</td>
							<td>${book.writer}</td>
							<td>${book.publisher}</td>
							<c:if test="${book.rental==0}">
								<td>대여 가능</td>
								<td><a class="btn-text-link block w-full truncate"
								href="../book/doRental?id=${book.id}&replaceUri=${rq.currentUri}">대여 신청</a></td>
								
							</c:if>
							<c:if test="${book.rental!=0}">
								<td>대여 중</td>
								<td><a class="btn-text-link block w-full truncate"
								href="../book/doReturn?id=${book.id}&replaceUri=${rq.currentUri}">반납 처리</a></td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>



	</div>
</section>

<%@ include file="../common/foot.jspf"%>