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
	
	$(function(){
		fnList();
	})
	
	function fnList() {
		$.ajax({
			type: 'get',
			url: '${contextPath}/list.json',
			dataType: 'json',
			success: function(resData){	
				$('#staffList').empty();
				$.each(resData, function(i, staff){
					let str = '<tr>';
					str += '<td>' + staff.sno;
					str += '<td>' + staff.name;
					str += '<td>' + staff.dept;
					str += '<td>' + staff.salary;
					$('#staffList').append(str);
				})
			}
		})
	}
	
	function fnAdd() {
		$.ajax({
			type: 'post',
			url: '${contextPath}/add.do',
			data: $('#frm_add').serialize(), 
			dataType: 'text',
			success: function(resData){
				alert(resData);
				fnList();	
				$('#sno').val();	
				$('#name').val();
				$('#salary').val();
				
			},
			error: function(jqXHR){  
				alert(jqXHR.responseText);
			}
		})
	}
	
	function fnSearch() {
		$.ajax({
			type: 'get',
			url: '${contextPath}/query.json',
			data: $('#frm_search').serialize(),
			
			dataType='json',
			success : function(){
				alert(resData);
				$('#sno').val();	
				$('#name').val();
				$('#salary').val();
				},
				error: function(jqXHR){  
					alert(jqXHR.responseText);
				}
		})
	}
	

</script>
</head>
<body>

	<div>
		<h3>사원등록</h3>
		<form id="frm_add">
			<input type="text" name="sno" id="sno" placeholder="사원번호">
			<input type="text" name="name" id="name" placeholder="사원명">
			<input type="text" name="dept" id="dept" placeholder="부서명">
			<input type="button" value="등록" onclick="fnAdd()">
		</form>
	</div>
	
	<hr>
	
	<div>
		<h3>사원조회</h3>
		<form id="frm_search">
			<input type="text" name="query" id="query" placeholder="사원번호입력">
			<input type="button" value="조회" onclick="fnSearch()">
			<input type="button" value="전체" onclick="fnList()">
		</form>
	</div>
	
      <div>
        <h1>사원번호 검사하기</h1>
        <input type="text" id="no">
        <input type="button" value="검사" id="btn_no">
    </div>
    <script>
   
        var no = document.getElementById('no');
        var btnId = document.getElementById('btn_no'); 
  
        btnNo.addEventListener('click', ()=>{

            var regNo = /^[1-9]{0,4}$/;
            if(regNo.test(id.value)) {
                alert('');
            }else {
                alert('사원번호는 5자리 숫자입니다.');
            }
        })
    </script>

	
	<hr>
	
	<div>
		<h3>사원목록</h3>
		<table border="1">
			<thead>
				<tr>
					<td>사원번호</td>
					<td>사원명</td>
					<td>부서명</td>
					<td>연봉</td>
				</tr>
			</thead>
			<tbody id="staffList">
			
			
			
			</tbody>
		</table>
	</div>
	
</body>
</html>