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
	
	$(function(){
		fnChkAll();
		fnChkOne();
		fnRemoveList();
	})
	// 전체선택 체크박스 
	function fnChkAll() {
		$('#chk_all').on('click', function(){
			$('.chk_one').prop('checked', $(this).prop('checked')); /* this가 전체선택. 체크되어있어서 트루이면 개별선택도 트루를 가져와라 전체선택이 펄스이면 개별선택도 다 펄스를 가져가라 */
			
		})
	}
	// 개별선택 체크박스 
	function fnChkOne() {
		let chkOne = $('.chk_one'); // 모든 개별선택
		chkOne.on('click', function(){ /* 개별선택중 누구든 선택하면 전체를 다 뒤짐. 그래서 포문 짤것 */
			let chkCnt = 0;
			for(let i = 0; i < chkOne.length; i++) { // 모든 개별선택 체크여부 확인 
				chkCnt += $(chkOne[i]).prop('checked'); // 그 값을 누적시키고 
			}
			$('#chk_all').prop('checked', chkCnt == chkOne.length); // 길이와 같으면 전부 트루. 전부 선택. 
		})
	}
	
	// 선택 항목 삭제 
	function fnRemoveList(){
		$('#frm_remove_list').on('submit', function(event){
			if($('.chk_one:checked').length == 0) {
				alert('선택된 게시글이 없습니다.');
				event.preventDefault();
				return;
			}
			if(confirm('선택한 게시글을 모두 삭제할까요?') == false) {
				event.preventDefault(); 
				return; 
			}
		})
	}
	
</script>
<style>
	.screen_out {
		display: none;
		
	}
	#lbl_chk_all:hover {
		cursor: pointer;
		color: gray;
	}
	#lbl_chk_all:active {
	 	color : silver;
	}
	.lbl_chk_one {
		cursor: pointer;
		padding-left: 20px;
		background-image: url('../resources/images/check1.png');
		background-size: 16px 16px;
		background-repeat: no-repeat;
		background-position: 0 3px;
		
	}
	.chk_one:checked + label {
		background-image: url('../resources/images/check2.png');
	}
</style>
</head>
<body>
	<div>
		<a href="${contextPath}/board/write.do" >새글작성하기</a> <!-- 게시판 앞으로 이런식으로 작성할 것. (분리하는 방식) -->
	</div>
	<div>
		<form id="frm_remove_list" action="${contextPath}/board/removeList.do" method="post">
			<div>
				<button>선택삭제</button> <!-- 여러개 삭제하겠다는 뜻 -->
			</div>
			<table border="1">
				<thead>
					<tr> 
						<td>
							<label for="chk_all" id="lbl_chk_all">전체선택</label> <!-- 체크 박스 없이 라벨로 남겨두면 전체선택 글씨만 눌러도 전체선태 가능 -->
							<input type="checkbox" id="chk_all" class="screen_out">
						</td>
						<td>제목</td>
						<td>작성자</td>
						<td>작성일자</td>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty boardList}">
						<tr>
							<td colspan="4">첫 게시글의 주인공이 되어 보세요!</td>
						</tr>
					</c:if>
					<c:if test="${not empty boardList}">
					<c:forEach items="${boardList}" var="b"> <!-- 아이템즈라는 배열에서 var를 하나씩 꺼내는 것  -->
						<tr>
							<td>
							 	<input type="checkbox" id="chk_one${b.boardNo}" class="chk_one screen_out"  name="boardNoList" value="${b.boardNo}">
							 	<label for="chk_one${b.boardNo}" class="lbl_chk_one">${b.boardNo}</label>
							 </td> <!--파라미터 넘길거라 name 입력했음. forEach이므로 배열로 들어올것이므로 리스트로 작성.  -->
							<td><a href="${contextPath}/board/detail.do?boardNo=${b.boardNo}">${b.title}</a></td>  <!-- boardDTO 객체를 b라고 함-->
							<td>${b.writer}</td>
							<td>${b.createdAt}</td>
						</tr>
					</c:forEach>
					</c:if>
				</tbody>
			</table>
		</form> 
	</div>
</body>
</html>