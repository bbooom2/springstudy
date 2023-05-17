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
<style>

	

	@font-face {
    font-family: 'Tenada';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2210-2@1.0/Tenada.woff2') format('woff2');
    font-weight: normal;
    font-style: normal;
}
	
 	header {
 		 position: fixed;
		  top: 0;
		  left: 0;
		  right: 0;
		  
		  height: 50px;
		  padding: 1rem;
		  color: black;
		  background: white;
		  display: flex;
		   font-size : 32px;
		  justify-content: space-between;
		  align-items: center;
		  border-bottom: 20px solid #facc57;
	
	}
	
	header > div {
		float:left;
		vertical-align: top;
		font-family: 'Tenada';
	}
	
	button {
      margin-left: 10px;
      font-size: 16px;
      background-color: transparent;
      border: none;
   }
	
	.logo_name {
		display: flex;
		align-items: center;
	}
	
	body {
		padding-top: 100px;
	}
	

	div {
		
		box-sizing: border-box; 
		text-align: center;
		
	}
	
	.product-list {
	 	width: 1200px;
	 	margin-left: auto;
	 	margin-right: auto;
	 
	}
	
	.product {
	 	width: 380px;
	 	text-align: center;
	 	display: block;
	 	float: left;
	 	margin-left: 10px;
	 	margin-right: 10px;
	 	text-decoration: none;
	}
	
	.product-name {
	 	margin-top: 20px;
	 	margin-bottom: 4px;
	}
	
	.product-price {
	 	margin-top: 4px;
	 	margin-bottom: 20px;
	}

	
	

</style>

</head>

<body>
<header>

	<div class="logo">
	야채단속반<img src="${contextPath}/resources/images/logo.png" width="250px">
	</div>

 <div><form method="post">
   <button>메뉴</button>
   <button>메뉴2</button>
   <button>메뉴2</button>
   <button>로그인</button>
   <button>회원가입</button>
</form></div>

</header>


	<h2 align="center">WANTED</h2>
	<div class="product-list">
	
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_01.png" width="380">
			<div class = "product-name">싸움고수</div>
			<div class="product-price">15,000</div>
		
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_02.png" width="380">
			<div class = "product-name">깻잎</div>
			<div class="product-price">12,000</div>
		
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_03.png" width="380">
			<div class = "product-name">땅땅콩콩</div>
			<div class="product-price">10,000</div>

		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_05.png" width="380">
			<div class = "product-name">양배추</div>
			<div class="product-price">12,000</div>
		
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_06.png" width="380" height="285">
			<div class = "product-name">양배추쪼개면방울양배추</div>
			<div class="product-price">12,000</div>
		
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_09.png" width="380">
			<div class = "product-name">맛좋은오이</div>
			<div class="product-price">5,000</div>
			
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_21.png" width="380"> 
			<div class = "product-name">아슷파라거슷</div>
			<div class="product-price">5,000</div>
			
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_27.png" width="380">
			<div class = "product-name">사랑의콩깍지</div>
			<div class="product-price">5,000</div>
		
		<a href="#" class ="product">
			<img src="${contextPath}/resources/images/v_28.png" width="380">
			<div class = "product-name">양파쿵야</div>
			<div class="product-price">7,000</div>
		
	</div>
<footer>
         <p>footer area</p>
</footer>
</body>
</html>