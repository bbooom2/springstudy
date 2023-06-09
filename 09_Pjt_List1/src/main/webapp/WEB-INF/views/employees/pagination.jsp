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
</head> <!-- 페이지네이션은 목록 하단에 번호 이전 이후 이런거 있는것.  -->
<body>
	<div>
	<a href="${contextPath}/employee/search.form">사원 조회화면으로 이동</a>
	<!-- 서치 화면으로 이동.  -->	
	</div>
	
	<div>
		<h1>사원 목록</h1>
		<div> <!-- 세션은 저장해두면 언제든 꺼내서 쓸 수 있음. 기본 사용시간은 30분으로 되어있지만 마음만 먹으면 바꿀 수 있음. -->
			<select id="recordPerPage"> <!-- 레코드 퍼 페이지 : 셀렉트값이 체인지되었다는 체인지이벤트가 필요함 -->
				<option value="10">10개</option> <!-- 디폴트 값이 될것. -->
				<option value="20">20개</option>
				<option value="30">30개</option>
			</select>
		</div>
		<hr> <!-- 프로젝트 할때는 hr 넣지 말고 css 진행하면 됨  -->
		<table border="1">
			<thead>
				<tr>
					<td>순번</td>
					<td>사원번호</td>
					<td>사원명</td>
					<td>이메일</td>
					<td>전화번호</td>
					<td>입사일자</td>
					<td>직업</td> <!-- jopId  -->
					<td>연봉</td>
					<td>커미션</td>
					<td>매니저</td>
					<td>부서번호</td>
					<td>부서명</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${employees}" var="emp" varStatus="vs"> <!-- 하나씩 뺐을때 emp // 인덱스 vs 변수는 내가 알아서 주면 됨 -->
					<tr>
						<td>${vs.index}</td>
						<td>${emp.employeId}</td> <!-- empDTO에 있는 필드명  -->
						<td>${emp.firstName} ${emp.lastName}</td> <!-- 사원이름 퍼스트 라스트 공백사이 두고 같이 씀  -->
						<td>${emp.email}</td>
						<td>${emp.phoneNumber}</td>
						<td>${emp.hireDate}</td>
						<td>${emp.jobId}</td>
						<td>${emp.salary}</td>
						<td>${emp.commissionPct}</td>
						<td>${emp.managerId}</td>
						<td>${emp.deptDTO.departmentId}</td>
						<td>${emp.deptDTO.departmentName}</td> <!-- 부서명은 어떻게 처리할 것인가? EmpDTO에서 처리 완료. -->
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="12">
						<div>${pagination}</div> <!--해당 div는 이따css 해야할지 몰라서 일단 넣어놓은 것. 참고하세요.  -->
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</body>
</html>