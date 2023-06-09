<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="${contextPath}/resources/js/lib/jquery-3.6.4.min.js"></script>
<script>
	$(function(){
		$('#title').text('Hello World');
	})
</script>
<link rel="stylesheet" href="${contextPath}/resources/css/init.css">
</head>
<body>
	<!-- MvcController에서 확인합시다. -->
	<h1 id="title"></h1>
	<div>
	<img src="${contextPath}/resources/images/animal1.jpg" width="300px">	
	</div>
	
	<div>
		<a href="${contextPath}/list.do">목록보기</a>
	</div>
	
	<!-- ------------------------------------------------------------------------------------- -->
	<!-- MvcController에서 확인합시다. -->
	<h1>요청 파라미터1</h1>
	<div><a href="${contextPath}/detail.do">상세보기1</a></div>
	<div><a href="${contextPath}/detail.do?name=민경태">상세보기2</a></div>
	<div><a href="${contextPath}/detail.do?age=46">상세보기3</a></div>
	<div><a href="${contextPath}/detail.do?name=민경태&age=46">상세보기4</a></div>
	
	<h1>요청 파라미터2</h1>
	<div><a href="${contextPath}/detail.me">상세보기1</a></div>
	<div><a href="${contextPath}/detail.me?name=민경태">상세보기2</a></div>
	<div><a href="${contextPath}/detail.me?age=46">상세보기3</a></div>
	<div><a href="${contextPath}/detail.me?name=민경태&age=46">상세보기4</a></div>
	
	
	<h1>요청 파라미터3</h1>
	<div><a href="${contextPath}/detail.gdu">상세보기1</a></div>
	<div><a href="${contextPath}/detail.gdu?name=민경태">상세보기2</a></div>
	<div><a href="${contextPath}/detail.gdu?age=46">상세보기3</a></div>
	<div><a href="${contextPath}/detail.gdu?name=민경태&age=46">상세보기4</a></div>
	
	<!-- DiController에서 확인합시다. -->
	<h1>Dependency Injection</h1>
	<div><a href="${contextPath}/bbs/detail.do">상세보기</a></div> <!--/bbs/detail.do 이제 이게 매핑  -->
	
	<!-- PostController에서 확인합시다. -->
   <h1>Redirect</h1>
   <div><a href="${contextPath}/post/detail.do?name=지원&age=29">상세보기</a></div>
   

</body>
</html>


