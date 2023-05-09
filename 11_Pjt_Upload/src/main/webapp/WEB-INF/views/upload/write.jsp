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
<script>
	function fnList() {
		location.href = "${contextPath}/upload/list.do";
	}
	function fnFileCheck(vThis) {
		// 최대 크기 10MB
		let maxSize = 1024 * 1034 * 10;
		
		// 첨부된 파일 목록 
		let files = vThis.files;
		
		// 첨부된 파일 순회(크기 체크 + 첨부된 파일명 표시)
		$('#fileList').empty(); //인풋타입 밑에 만들어놓은 fileList. 실제로는 표시 안 됨.
		$.each(files, function(i, file){
			
			// 크기 체크 
			if(file.size > maxSize){
				alert('각 첨부파일의 최대 크기는 10MB입니다.');
				$(this).val(''); // 첨부된 파일을 모두 지운다. 
				return;
			}
			
			// 첨부된 파일명 표시 
			$('#fileList').append('<div>' + file.name + '</div>');
		})
	}
</script>
</head>
<body>

	<div>
		<h1>UPLOAD 게시글 작성하기</h1>
		<!-- 파일첨부 태그 -->
		<form method="post" enctype="multipart/form-data" action="${contextPath}/upload/add.do">
			<div>
				<label for="uploadTitle">제목</label>
				<input type="text" id="uploadTitle" name="uploadTitle">
			</div>
			<div>
				<label for="uploadContent">내용</label><br>
				<textarea id="uploadContent" name="uploadContent" rows="5" cols="30"></textarea>
			</div>
			<div>
				<label for="files">첨부</label>
				<!-- 멀티플을 넣으면 다중첨부가 된다. 빼면 단일첨부가 된다.  -->
				<!-- 일반 리퀘스트로는 처리 못함. 멀티파트리퀘스트를 사용해야한다. -->
				<input type="file" id="files" name="files" multiple="multiple" onchange="fnFileCheck(this)">
				<div id="fileList">첨부파일의 목록이 이곳에 표시됩니다</div>
			</div>
			<div>
				<button>작성완료</button>
				<input type="button" value="목록" onclick="fnList()">
			</div>
		</form>
	</div>
	
	
</body>
</html>