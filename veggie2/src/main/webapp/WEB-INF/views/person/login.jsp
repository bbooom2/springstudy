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
		// 아이디 테스트
		// 알파벳으로 시작, 알파벳, 숫자로만 이루어지되 4자 이상
		var idTest = /^[a-z][0-9a-z]{4,}$/
		var idValidate = false;
		$('#perId').keyup(function(event) {
			if(idTest.test($('#perId').val()) != true){
				$('#perId').css('margin-bottom', '0px');
				$('#id_msg').css('margin', '10px 0px 10px 0px');
				$('#id_msg').text('아이디는 4자 이상이여야합니다.');
				$('#id_msg').removeClass('id_success');
				$('#id_msg').removeClass('id_init');
				$('#id_msg').addClass('id_fail');
				var idValidate = false;
			} else {
				$('#id_msg').text('');
				$('#id_msg').removeClass('id_success');
				$('#id_msg').removeClass('id_init');
				$('#id_msg').addClass('id_success');
				var idValidate = true;
			}
		})
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
					$('#perPw2').css('margin-bottom', '0px');
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
		position: static;
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
	.perEmail1, .perEmail2 {
		width : 190px;
	}
	
	input[type="radio"] {
		width: 40px;
	}
	.gender {
		display: flex;
	}

	.regiBtn {
		position : absolute;
		border: none;
		background-color : #FACC57;
		padding : 6px;
		width : 410px;
		height: 40px;
	}
	.fnBack {
		text-align: center;
	}	
	
	/* 로그인 관련  */
	#pw_msg, #id_msg, #phone_msg {
		color : red;
		font-size: 14px;
	}
</style>
</head>
<body>

	<!-- 회원가입란 -->
	<div class="box">
	<h1>회원가입</h1>
		<form action="/addPerson.go" method="post" id="register_frm">
			<div>
				<div>이름</div>
				<input type="text" name="perName" id="perName" placeholder="이름">
				<div>아이디</div>
				<input type="text" name="perId" id="perId" placeholder="아이디">
				<div id="id_msg" class="id_init"></div>
				<div>이메일</div>
				<!-- DB수정 필요 -->
				<input type="text" name="perEmail1" class="perEmail1"> @ 
					<select name="perEmail2" class="perEmail2">
						<option value="" selected="selected">--선택--</option>
						<option value="gmail.com">gmail.com</option>
						<option value="naver.com">naver.com</option>
					</select>
				<div>핸드폰번호</div>
				<input type="text" id="perPhoneNo" name="perPhoneNo" placeholder="하이픈기호(-)를 포함하여 입력해주세요">
				<div id="phone_msg"></div>
				<div>비밀번호</div>
				<input type="password" name="perPw" id="perPw1" placeholder="비밀번호는 8자이상입니다">
				<div>비밀번호 확인</div>
				<input type="password" id="perPw2" placeholder="비밀번호는 8자이상어쩌고">
				<!-- 패스워드 일치시, 불일치시 메세지 -->
				<div id="pw_msg" class="pw_init"></div>
					
				<div class="gender">
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
					<div><button class="regiBtn">회원가입</button></div>
					<div onclick="fnBack()" class="fnBack">돌아가기</div>
				</div>
			</div>
		</form>
	</div>
	
</body>
</html>