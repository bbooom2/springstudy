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
		location.href = '${contextPath}/board/detail.do?boardNo=' + n; /* boardNo라는 파라미터, 상세보기를 하려면 어떤 번호를 보고 싶은지 기재해줘야 한다. */ 
	}
	$(function() {
		/*
		let addResult = '1'; : 삽입 성공
		let addResult = '0'; : 삽입 실패
		let addResult = '';  : 삽입과 상관 없음 
		// 따옴표로 묶음으로써 전달되지 않는 경우에는 '' 빈문자열로 되게끔 만든것이다. 
							 ↑		
		*/ 
		
		let addResult = '${addResult}'; //에드리절트라는 속성을 삽입했을 땐 에드리절트라고 하는 속성을 보내준다. 에드후에는 리스트로 보내지만 이상태에서는 전달된 값이 없을 수도 있다. 변수처리되는 부분 (이엘처리된부분)이라 실제로 실행될때는 변수값으로 바뀐다. 디테일도 그런데 컨텍스트패스가 변수로 되어있는데 실제로 실ㅇ행될때 는 변수값을 받아서 진행되는 것과 같음 이것도 값이 0과 1이 있으면 성공 실패가 되어 실행되겠지만 문제는 에드리절트가 전달이 안됐을때는 let addResult = ;이렇게돼서 실행불가. 빨간줄 회피하기 위해 문자 안에 넣을 것. 
		if(addResult != '') {
			if(addResult == '1') {
				alert('게시글이 등록되었습니다.');
			} else {
				alert('게시글이 등록이 실패했습니다.');
			}
		}
	
	let removeResult = '${removeResult}';
    if(removeResult != ''){
       if(removeResult == '1'){
          alert('게시글이 삭제되었습니다.');
       } else{
          alert('게시글 삭제가 실패했습니다.')
       	}
       }
    })
	
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
					<tr onclick="fnDetail(${b.boardNo})">
						<td>${b.title}</td>  <!-- boardDTO 객체를 b라고 함-->
						<td>${b.writer}</td>
						<td>${b.createdAt}</td>
					</tr>
				</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>