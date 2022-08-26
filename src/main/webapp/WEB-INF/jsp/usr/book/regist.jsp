<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.khj.exam.demo.utill.Ut"%>

<c:set var="pageTitle" value="책 등록" />
<%@ include file="../common/head.jspf"%>



<script>
	let submitRegistFormDone = false;
	let validLogind = "";
	function submitRegistForm(form) {
		if (submitRegistFormDone) {
			alert('처리중입니다.');
			return;
		}

		form.title.value = form.title.value.trim();

		if (form.title.value.length == 0) {
			alert('책 제목을 입력해주세요.');
			form.title.focus();
			return;
		}

		form.writer.value = form.writer.value.trim();

		if (form.writer.value.length == 0) {
			alert('작가를 입력해주세요.');
			form.writer.focus();
			return;
		}

		form.publisher.value = form.publisher.value.trim();

		if (form.publisher.value.length == 0) {
			alert('출판사를 입력해주세요.');
			form.publisher.focus();
			return;
		}

		submitRegistFormDone = true;
		form.submit();
	}

	var checkLoginIdDup = _.debounce(function(form) {
		<!--
		$massage.empty().append('<div class="mt-2"> 아이디를 입력해주세요. </div>');
		-->

		$.get('../member/getLoginIdDup', {
			isAjax : 'Y',
			loginId : form.loginId.value,
		}, function(data) {
			var $massage = $(form.loginId).next();

			if (data.resultCode.substr(0, 2) == 'S-') {
				$massage.empty().append(
						'<div class="mt-2 text-green-500">' + data.msg
								+ '</div>');
				validLogind = data.body.loginId;
			} else {
				$massage.empty()
						.append(
								'<div class="mt-2 text-red-500">' + data.msg
										+ '</div>');
				validLogind = '';
			}

			if (data.success) {
				validLogind = data.data1;
			} else {
				validLogind = '';
			}
		}, 'json');

	}, 1000);

	function JoinForm_checkLoginIdDup(input) {
		var form = input.form;
		form.loginId.value = form.loginId.value.trim();

		var $massage = $(form.loginId).next();

		if (form.loginId.value.length == 0) {
			$massage.empty();
			return;
		}

		checkLoginIdDup(form);
	}
</script>

<section class="mt-5">
	<div class="container mx-auto px-3">
		<div class="table-box-type-1">
			<form class="table-box-type-1" method="POST"
				action="../book/doRegist"
				onsubmit="submitRegistForm(this); return false;">
				<input type="hidden" name="afterJoinUri"
					value="${param.afterJoinUri}" />
				<table>
					<colgroup>
						<col width="200" />
					</colgroup>
					<tbody>
						<tr>
							<th>책 제목</th>
							<td><input class="input input-bordered" name="title"
								placeholder="책제목" type="text"
								onkeyup="JoinForm_checkLoginIdDup(this);" autocomplete="off" />
								<div class="massage-msg"></div></td>
						</tr>
						<tr>
							<th>작가</th>
							<td><input class="input input-bordered" name="writer"
								placeholder="작가" type="text" /></td>
						</tr>
						<tr>
							<th>출판사</th>
							<td><input class="input input-bordered"
								name="publisher" placeholder="출판사" type="text" />
							</td>
						</tr>
						<tr>
							<th>등록하기</th>
							<td>
								<button type="submit" class="btn btn-primary">등록하기</button>
								<button type="button" class="btn btn-outline btn-success"
									onclick="history.back();">뒤로가기</button>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
</section>

<%@ include file="../common/foot.jspf"%>