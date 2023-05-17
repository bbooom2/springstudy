<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<!-- 클래스는 . 아이디는 # -->
<script>
	$(function() {
		// 아이디 테스트
		var idTest = /^[a-z][0-9a-z]{4,}$/
		var idValidate = false;
		$('#perId').keyup(function(event) {
			if(idTest.test($('#perId').val()) != true){
				$('#id_msg').text('아이디는 4자 이상이여야합니다.');
				$('#pw_msg').removeClass('id_success');
				$('#pw_msg').removeClass('id_init');
				$('#pw_msg').addClass('id_fail');
				var idValidate = false;
			} else {
				$('#id_msg').text('');
				$('#pw_msg').removeClass('id_success');
				$('#pw_msg').removeClass('id_init');
				$('#pw_msg').addClass('id_success');
				var idValidate = true;

			}
		})
		// 비밀번호 테스트
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
					$('#pw_msg').text('비밀번호가 일치하지 않습니다.')
					$('#pw_msg').removeClass('pw_fail');
					$('#pw_msg').removeClass('pw_success');
					$('#pw_msg').addClass('pw_init');
					pwValidate = false;
				}
			} else if (pwTest.test($('#perPw1').val()) != true) {
					$('#pw_msg').text('비밀번호는 영문, 숫자, 특수기호를 혼합하여 8자 이상 가능합니다.')
					$('#pw_msg').removeClass('pw_success');
					$('#pw_msg').removeClass('pw_init');
					$('#pw_msg').addClass('pw_fail');
					pwValidate = false;
			}
			
		})
	})
	
	function fnBack() {
		hitory.back();
	}
	let inputPhone = document.getElementById("perPhoneNo").value
	let phoneFormat = /^010-[0-9]{4}-[0-9]{4}$/
	if(phoneFormat.test(intputPhone) == false){
		$('#phoneNoMsg').show();
	}


</script>
<!-- 클래스는 . 아이디는 # -->
<style type="text/css">
	
	/* 비밀번호 일치시, 불일치시 보이게 */
	.pwright .pwwrong {
		visibility: hidden;
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
		width : 300px;
		border: none;
		border-bottom: 1px solid black;
		padding-top: 20px;
		margin-bottom: 20px;
		outline: none;
	}
	input[type="radio"] {
		width: 40px;
	}
	.gender {
		display: flex;
	}

	.regiBtn {
		border: none;
		background-color : #FACC57;
		padding : 6px;
		width : 300px;
	}
	.fnBack {
		text-align: center;
	}
</style>
<body>

	<!-- 회원가입란 -->
	<div>
		<h1>회원가입</h1>	
	</div>
	<form action="/addPerson.do" method="post" id="register_frm">
		<div>
			<div>이름</div>
			<input type="text" name="perName" id="perName" placeholder="이름">
			<div>아이디</div>
			<input type="text" name="perId" id="perId" placeholder="아이디는 4자이상입니다.">
			<div id="id_msg" class="id_init"></div>
			<div>이메일</div>
			<!-- DB수정 필요 -->
			<input type="text" name="perEmail1"> @ 
				<select name="perEmail2">
					<option value="" selected="selected">--선택--</option>
					<option value="gmail.com">gmail.com</option>
					<option value="naver.com">naver.com</option>
				</select>
			<div>핸드폰번호</div>
			<input type="text" id="perPhoneNo" name="perPhoneNo">
			<div id="phoneNoMsg">정확한 핸드폰 번호를 입력해주세요</div>
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
				<button class="regiBtn">회원가입</button>
				<div onclick="fnBack()" class="fnBack">돌아가기</div>
			</div>
		</div>
	</form>
	
</body>
</html>