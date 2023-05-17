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
	$(function() {
		// 비밀번호 테스트
		// 숫자, 영문, 특수기호 포함 8글자 이상
		var pwTest = /^[0-9a-z!@#$%^&?]{8,}$/
		var pwValidate = false;
		$('#perPw2').keyup(function(event) {
			
			if(pwTest.test($('#perPw1').val()) == true && pwTest.test($('#perPw2').val()) == true){
				$('#pw_msg').text('')
				$('#pw_msg').removeClass('pw_fail');
				$('#pw_msg').removeClass('pw_init');
				$('#pw_msg').addClass('pw_success');
				pwValidate = true;
				
				if ($('#perPw1').val() != $('#perPw2').val()) {
					$('#perPw2').css('margin-bottom', '0px');
					$('#pw_msg').css('margin', '10px 0px 10px 0px');
					$('#pw_msg').text('비밀번호가 일치하지 않습니다.')
					$('#pw_msg').removeClass('pw_fail');
					$('#pw_msg').removeClass('pw_success');
					$('#pw_msg').addClass('pw_init');
					pwValidate = false;
				}
			} else if (pwTest.test($('#perPw1').val()) != true) {
					$('#perPw1').css('margin-bottom', '0px');
					$('#pw_msg').css('margin', '10px 0px 10px 0px');
					$('#pw_msg').text('비밀번호는 영문, 숫자, 특수기호를 혼합하여 8자 이상 가능합니다.')
					$('#pw_msg').removeClass('pw_success');
					$('#pw_msg').removeClass('pw_init');
					$('#pw_msg').addClass('pw_fail');
					pwValidate = false;
			}
			
		})
		// 핸드폰 번호 검사
		var phoneNoTest = /^010-[0-9]{4}-[0-9]{4}$/
		var phoneValidate = false;
		$('#perPhoneNo').keyup(function(event) {
			if(phoneNoTest.test($('#perPhoneNo').val()) != true){
				$('#perPhoneNo').css('margin-bottom', '0px');
				$('#phone_msg').text('핸드폰 번호를 확인해주세요.');
				$('#phone_msg').css('margin', '10px 0px 10px 0px');
				$('#phone_msg').removeClass('id_success');
				$('#phone_msg').removeClass('id_init');
				$('#phone_msg').addClass('id_fail');
				var phoneValidate = false;
			} else {
				$('#phone_msg').text('');
				$('#phone_msg').removeClass('id_success');
				$('#phone_msg').removeClass('id_init');
				$('#phone_msg').addClass('id_success');
				var phoneValidate = true;

			}
		})
		
	})
	// 돌아가기 버튼 눌렀을 때 뒤로 감
	function fnBack() {
		hitory.back();
	}

</script>
<!-- 클래스는 . 아이디는 # -->
<style type="text/css">
	.box {
		position: absolute;
		width: 600px;
		left: calc(50% - 600px/2);
		top: 210px;
		background: #F4F4F4;
		border-radius: 59px;
	}
	
	h1 {
		text-align: center;
		margin-bottom: 50px;
	}
	#register_frm {
		display: flex;
		justify-content: center;
	}
	input {
		width : 410px;
		border: none;
		border-bottom: 1px solid black;
		padding-top: 20px;
		margin-bottom: 20px;
		outline: none;
		background-color: transparent;
	}
	input[type="radio"] {
		width: 40px;
	}
	.gender {
		display: flex;
	}

	.modifyBtn {
		border: none;
		background-color : #FACC57;
		padding : 6px;
		width : 300px;
	}
	.fnBack {
		text-align: center;
	}
	/* 로그인 관련  */
	#pw_msg, #id_msg, #phone_msg {
		color : red;
	}
</style>
<body>
	<div class="box">
	<div>
		<h1>회원정보수정</h1>	
	</div>
	<!--el 넣어야 -->
	<form action="/addPerson.do" method="post" id="register_frm">
		<div>
			<div>이름</div>
			<input type="text" name="perName" id="perName" readonly="readonly" placeholder="이름은 변경이 불가능합니다.">
			<div>아이디</div>
			<input type="text" name="perId" id="perId" readonly="readonly" placeholder="아이디는 변경이 불가능합니다.">
			<div>이메일</div>
			<!-- DB수정 필요 -->
			<input type="text" name="perEmail1" placeholder="이메일은 변경이 불가능합니다.">
			<div>핸드폰번호</div>
			<input type="text" id="perPhoneNo" name="perPhoneNo">
			<div id="phone_msg"></div>
			<div>비밀번호</div>
			<input type="password" name="perPw" id="perPw1" placeholder="●●●●">
			<div>비밀번호 확인</div>
			<input type="password" id="perPw2" placeholder="●●●●">
			<div id="pw_msg" class="pw_init"></div>
			
			
			<!-- 패스워드 일치시, 불일치시 메세지 -->
			<div id="pwMsg"></div>
			<div class="gender" hidden="">
				<span>성별</span>
				<label for="male">
					<input type="radio" name="gender" value="male" id="male">남성
				</label>
				<label for="female">
					<input type="radio" name="gender" value="female" id="female">여성
				</label>
			</div>
			<div>주소</div>
				<!-- 주소 api 자리 -->
			<input type="text" placeholder="주소api넣어야해요">
			<div>상세주소</div>
			<input type="text" id="detailAddress" name="detailAddress" placeholder="아파트/건물이름/동호수">
			<div>
				<button class="modifyBtn">수정완료</button>
				<div onclick="fnBack()" class="fnBack">돌아가기</div>
			</div>
		</div>
	</form>
	</div>
</body>
</html>