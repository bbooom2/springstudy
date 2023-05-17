<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script>
	
</script>
<!-- 클래스는 . 아이디는 # -->
<style type="text/css">
	.qnaTbl {
		border-bottom: 1px solid black;
		margin : 10px;
	}
</style>
<body>
	<div>질문 게시판</div>
	<div>
		<table class="qnaTbl">
			<thead>
				<tr>
					<td>글번호</td>
					<td>제목</td>
					<td>글쓴이<td>
					<td>날짜<td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${qnaList}" var="q">
					<tr>
						<td>${q.qnaNo}</td>
						<td>${q.qnaTitle}</td>
						<td>${q.qnaWriter}</td>
						<td>${q.qnaDate}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>