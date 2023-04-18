<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>  <!-- 이걸 제일 위에 두고 나머지 아래 배치 -->
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.js"></script>  <!-- 썸먼노트는 write.jsp 작성하면서 확인하도록 함  -->
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script> 
<link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote-lite.min.css"> <!-- css라 link태그 사용 -->
<style>
	tbody tr:hover {
		background-color: beige;
		cursor: pointer; 
		
	}
</style>
<script>
	function fnDetail(n) {
		location.href = '${contextPath}/board/detail.do?board_no=' + n; /* board_no라는 파라미터*/
	}

</script>
</head>
<body>
	<div>
		<a href="${contextPath}/board/write.do" >새글작성하기</a> <!-- 게시판 앞으로 이런식으로 작성할 것. (분리하는 방식) -->
	</div>
	<div>
		<table border="1">
			<thead>
				<tr>
					<td>제목</td>
					<td>작성자</td>
					<td>작성일자</td>
				</tr>
			</thead>
			<tbody>
				<c:if test="${empty boardList}">
					<tr>
						<td colspan="3">첫 게시글의 주인공이 되어 보세요!</td>
					</tr>
				</c:if>
				<c:if test="${not empty boardList}">
				<c:forEach items="${boardList}" var="b"> <!-- 아이템즈라는 배열에서 var를 하나씩 꺼내는 것  -->
					<tr onclick="fnDetail(${b.board_no})">
						<td>${b.title}</td>  <!-- boardDTO 객체를 b라고 함-->
						<td>${b.writer}</td>
						<td>${b.created_at}</td>
					</tr>
				</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>