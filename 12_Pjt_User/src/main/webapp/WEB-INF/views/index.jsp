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
</head>
<body>

	<div>
		<!-- 어떤 파일로 넘어갈 때는 .do 대신 .jsp를 그대로 기재한다. -->
		<a href="${contextPath}/user/agree.jsp">회원가입</a>
		<a href="${contextPath}/user/login.jsp">로그인</a>
		
	</div>
	
	
</body>
</html>