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
<script>
	function fnEdit() {
		$('#edit_screen').show();
		$('#detail_screen').hide();
	}
	function fnRemove(){
		if(confirm('삭제할까요?')){
			location.href = '${contextPath}/board/remove.do?board_no=${b.board_no}'; // 이버전이 싫어서 다른 버전으로 했었음. 왜냐면? 권한이 없는 사용자도 주소창에서 다 지울 수 있었음.
		}
	}
	function fnList() {
	 	location.href = '${contextPath}/board/list.do';
	}
	 $(function (){
	      $('#content').summernote({
	         width: 640,
	         height: 480,
	         lang: 'ko-KR',
	         toolbar: [
	        	 ['style', ['bold', 'italic', 'underline', 'clear']],
					['font', ['strikethrough', 'superscript', 'subscript']],
					['fontname', ['fontname']],
					['color', ['color']],
					['para', ['ul', 'ol', 'paragraph']],
					['table', ['table']],
					['insert', ['link', 'picture', 'video']],
					['view', ['fullscreen', 'codeview', 'help']]
	         ]
	      })
	      $('#edit_screen').hide(); // 최초 편집화면은 숨김 
	   })
</script>
</head>
<body>

	
	
	<div id="detail_screen">
		<h1>${b.board_no}번 게시글 상세보기</h1>
		<div>제목 : ${title}</div>
		<div>${content}</div>
		<div>
			<input type="button" value="편집" onclick="fnEdit()">
			<input type="button" value="삭제" onclick="fnRemove()">
			<input type="button" value="목록" onclick="fnList()">
		</div>
	</div>
	
	<div id="edit_screen">
		<h1>편집화면</h1> <!-- 위치는 상관없음 위 아래 상관 x 리다이렉트 방식은 포스트-->
		<form method="post" action="${contextPath}/board/modify.do">
			<div>
				<label for="title">제목</label>
				<input type="text" title="title" name="title" value="${b.title}"> 
			</div>
			<div>
				<div><label for="content">내용</label></div>	
				<textarea id="content" name="content" >${b.content}</textarea> <!-- summernote 편집기로 바뀌는 textarea / 텍스트아리아는 태그 사이에 기재해줘야 함-->
			</div>
			<div>
				<input type="hidden" name="board_no" value="${b.board_no}">
				<button>수정완료</button>
				<input type="button" value="목록" onclick="fnList()">
			</div>
		</form>
	</div>
	

	
</body>
</html>