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
		  font-size : 32px;
		  background: white;
		  display: flex;
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
	
	.logo {
		display: flex;
		align-items: center;
	}
	
	body {
		padding-top: 100px;
	}
	
	.consumer > input {
		width: 300px;
		border: none;
		border-bottom: 1px solid black;
		outline: none;
		padding-top: 5px;
		margin-bottom: 5px;
	}
	
	.receiver > input {
	width: 300px;
	border: none;
	border-bottom: 1px solid black;
	outline: none;
	padding-top: 5px;
	margin-bottom: 5px;
	}
	

      .main_section {
           display: flex;
       }
       .left_section {
           border: 1px solid gray;
           margin-right: 20px;
           flex: 4;  /* width: 40%; */
       }
       .right_section {
           border: 1px solid gray;
           flex: 6;  /* width: 60%; */
       }
       article {
           margin: 10px 5px 0;
           padding: 10px;
           background-color: #FFF;
       }
       .img {
           padding: 20px;
           background-color: #888;
       }
       footer {
           height: 150px;
           margin-top: 10px;
           background-color: #facc57;
       }
       footer > p {
           text-align: center;
           line-height: 150px;
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
</form>
</div>

</header>

	<section class="main_section">
		<section class="left_section">
			<article>	
				<h2>검거페이지</h2>
				No.19293921 검거 
			</article>	
			<article>
				<div class="consumer">
					<h3>구매자 정보</h3>
					<div>구매자 이름</div>
					<input type="text" name="con_name" id="con_name" >
					<div>구매자 전화번호</div>
					<input type="text" name="con_phoneNo" id="con_phoneNo">
				</div>
			</article>	
			<article>
				<h3>받으시는 분 정보</h3>
				<div class="receiver">
					<label id="check">
						<input type="checkbox" id="check">구매자와 동일합니다	
					</label>
					<div>이름</div>
					<input type="text" name="rec_name" id="rec_name" >
					<div>전화번호</div>
					<input type="text" name="rec_phoneNo" id="rec_phoneNo">
					<div>우편번호</div>
					<input type="text" name="rec_postcode" id="rec_postcode">
					<div>주소</div>
					<input type="text" name="rec_address" id="rec_address">
					<div>상세주소</div>
					<input type="text" name="rec_address2" id="rec_address2">
					<form>
					<h3>배송 요청 사항</h3>
						<select name="delivery_memo">
							<option value="delivery_memo1" selected>벨 누르고 앞에 두고 가주세요</option>
							<option value="delivery_memo2">소화전에 넣어 주세요</option>
							<option value="delivery_memo3">부재중일 시 문 앞에 두고 문자 주세요</option>
						</select>
					</form>
				</div>
			</article>
		</section>
			 <section class="right_section">
            <article>
                <h2>상품목록</h2>
               <a href="#" class ="product">
				<img src="${contextPath}/resources/images/v_01.png" width="150" align="middle">싸움고수
				<div class = "product-name">싸움고수</div>
				<div class="product-price">15,000</div>
			
				<a href="#" class ="product">
				<img src="${contextPath}/resources/images/v_02.png" width="150">
				<div class = "product-name">깻잎</div>
				<div class="product-price">12,000</div>
			
				<a href="#" class ="product">
				<img src="${contextPath}/resources/images/v_03.png" width="150">
				<div class = "product-name">땅땅콩콩</div>
				<div class="product-price">10,000</div>
	
				<a href="#" class ="product">
				<img src="${contextPath}/resources/images/v_05.png" width="150">
				<div class = "product-name">양배추</div>
				<div class="product-price">12,000</div>
            </article>
            <article>
                <h2>Title3</h2>
                <div class="img"></div>
                <div class="img"></div>
                <div class="img"></div>
            </article>
            <article>
                <h2>Title3</h2>
                <p>Description3</p>
                <p>Content3</p>
            </article>
        </section>
    </section>
	
	
	
<footer>
       <p>footer area</p>
</footer>
</body>
</html>