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
<script> /* 타입 자동완성 되어있어도 전혀 문제 없음 안 지워도 됨. */
/* 리무브 사용하려면 먼저 읽어오고 그다음에 나를 해주세요이기 때문에 로드이벤트 필요 */

	$(function(){
		
		// 원글 달기 결과 메시지 
		if('${addResult}' != ''){
			if('${addResult}' == '1') { 
				alert('원글이 달렸습니다.');
			} else {
				alert('원글 달기가 실패했습니다.');
			}
			
		}
		// 게시글 삭제 결과 메시지 
		if('${addResult}' != ''){
			if('${addResult}' == '1') { 
				alert('게시글이 삭제됐습니다.');
			} else {
				alert('게시글이 삭제되지 않았습니다.');
			}
			
		}
		// 답글 달기 결과 메시지 
		if('${addReplyResult}' != ''){
			if('${addReplyResult}' == '1') { 
				alert('답글이 달렸습니다.');
			} else {
				alert('답글 달기가 실패했습니다.');
			}
		}
		
		// 삭제 버튼 이벤트 
		$('.frm_remove').on('submit', function(event){
			if(confirm('BBS를 삭제할까요?') == false) {
				event.preventDefault();
				return;
			}
		})
		
		// 답글 작성 화면 표시/숨기기 
		$('.btn_reply').on('click', function(){
			
			//작성화면 
			let write= $(this).closest('.list').next(); //.closest은 가장 가까운 것 찾는 것. let write는 제이쿼리 객체 = (jQuery wrapper가 필요 없다) //클릭한 라이트 화면에서만 블라인드를 적용한다. 
			// 작성화면이 blind를 가지고 있다 = 다른 작성화면이 열려 있다 
			if(write.hasClass('blind')) {
			$('.write').addClass('blind'); 
			write.removeClass('blind');
			// 작성화면이 blind를 가지고 있지 않다 = 현재 작성화면이 열려 있다 	
			}else {
				write.addClass('blind'); // 현재 작성화면을 닫자 
			}
			
		})
	})

</script>
<style>
	.blind {
		display :none; /* 완전히 화면에서 안 보이게 하는 것 비저빌리티로 히든처리하는 거하고는 다름  */
	}
</style>
</head>
<body>
	<div>
		<a href="${contextPath}/bbs/write.do">BBS작성하러가기</a>
	</div>
	
	<hr>
	
	<div>
		<div>${pagination}</div> <!-- 번호가 위에 있음. -->
		<table border="1">
			<thead>
				<tr>
					<td>순번</td>
					<td>작성자</td>
					<td>제목</td>
					<td>IP</td>
					<td>작성일자</td>
					<td></td> <!-- 버튼 들어갈 자리 -->
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${bbsList}" var="bbs" varStatus="vs">
					<c:if test="${bbs.state == 1}">
					<!-- 게시글 내용 -->
					<tr class="list">
						<td>${beginNo - vs.index}</td>
						<td>${bbs.writer}</td>
						<td>
							<!-- DEPTH에 의한 들여쓰기 -->
							<c:forEach begin="1" end="${bbs.depth}" step="1">&nbsp;&nbsp;&nbsp;</c:forEach><!-- 원글은 0이니까 (1부터0)이니까 반복문 없음. 일단 바로 단 답글은 1이니까 1에서 1까지 한번 반복 -->
							<!-- 답글은 [Re] 표시하기 -->
							<c:if test="${bbs.depth > 0}">[Re]</c:if>
						 	<!-- 제목 -->
						 	${bbs.title}
						 	<!-- 답글작성하기 버튼 -->
						 	<input type="button" value="답글" class="btn_reply">
						
						 </td>
						<td>${bbs.ip}</td>
						<td>${bbs.createdAt}</td>
						<td><!-- c:forEach 안에 있는 거라 id로 하면 너무 여러개 생겨버림 -->
							<form class="frm_remove" method="post" action="${contextPath}/bbs/remove.do">
								<input type="hidden" name="bbsNo" value="${bbs.bbsNo}">
								<button>삭제</button> 
							</form>
						</td>
					</tr>
					<!-- 답글 작성 화면  -->
					<tr class="write blind"> <!-- 클래스 여러개는 띄어쓰기로 구분한다. 해당 blind는 초기화를 위한 것이다.-->
						<td colspan="6">
							<form method="post" action="${contextPath}/bbs/reply/add.do">
								<div>
									<label for="writer">작성자</label>
									<input id="writer" name="writer" required="required">
								</div>
								<div>
									<label for="title">제목</label>
									<input id="title" name="title" required="required">
								</div>
								<div>
									<button>답글</button>
									<!-- 원글의 depth, groupNo, groupOrder를 함께 보낸다. -->
									<input type="hidden" name="depth" value="${bbs.depth + 1}">
									<input type="hidden" name="groupNo" value="${bbs.groupNo}">
									<input type="hidden" name="groupOrder" value="${bbs.groupOrder}">
								</div>
							</form>
						</td>
					</tr>
					</c:if>
					<c:if test="${bbs.state == 0}">
						<tr>
							<td>${beginNo - vs.index}</td>
							<td colspan="5">삭제된 게시글입니다.</td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>