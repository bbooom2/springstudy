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
	
  // 취소하면 이전 페이지로 돌아간다. 
  function fnCancel(){
	 $('#btnCancel').on('click', function(){		 
    	history.back();
	 })
  }
  
  // 모두 동의 
  function fnCheckAll {
	  $('#checkAll').on('click', function(){
		  
	  });
  }
  
  // 개별 선택 
  function fnCheckOne(){
 	$('#checkOne').on('click', function(){
		  
	  });
  } 
  
  // 가입페이지로 이동하기(frmAgree의 submit)
  function fnFrmAgreeSubmit() {
	  $('#frmAgree').on('submit', function(event) {
		  if($('#service').is(':checked') == false || $('#privacy').is(':checked') == false) //서비스가 체크되어있는지를 확인하는 문구 
	  		alert('필수 약관에 동의해야만 가입할 수 있습니다.');
		  	event.preventDefault();
		  	return;
	    }
	 })
  }
	  
  // 함수 호출 
  $(function(){	  
	  
	fnCancel();
	fnCheckAll();
	fnCheckOne();
  	fnFrmAgreeSubmit();
})
   
</script>
</head>
<body>

  <div>
  
    <h1>약관 동의하기</h1>
    
    <form id="frmAgree" action="${contextPath}/user/join.jsp">
    
      <div>
        <input type="checkbox" id="checkAll"> <!-- 반드시 체크 되어있어야함 -->
        <label for="checkAll">모두 동의</label>
      </div>
      
      <hr>
      
      <div>
      <!-- name 없는 이유는 체크하면 무조건 넘어가니까   -->
        <input type="checkbox" id="service" class="checkOne"> <!-- 반드시 체크 되어있어야함 -->
        <label for="service" >이용약관 동의(필수)</label>
        <div>
          <textarea>본 약관은 ...</textarea>
        </div>
      </div>
      
      <div>
        <input type="checkbox" id="privacy" class="checkOne">
        <label for="privacy">개인정보수집 동의(필수)</label>
        <div>
          <textarea>개인정보보호법에 따라 ...</textarea>
        </div>
      </div>
      
      <!-- 체크여부를 확인하기 위해서 name을 정해놓음, 전달되는 파라미터가 location, event / 이파라미터가 join.jsp로 넘어감  -->
      <!-- 이전에 할 때는 체크박스에 value를 넣었는데 이번에는 뺐음. 안 넣었을 때는 check를 하면 on이라는 디폴트값으로 넘어간다. 위치정보 수집을 클릭하면 파라미터 로케이션이 값으로 on이라는 값을 가지고 전달이 될 것. 그러면 check 안했을 때는 애초에 로케이션이 절달되지 않음. 체크가 없으면 없는 것으로 간주됨. 네임 로케이션이 아예 가질 않는다. location이 off로 넘어가는 게 x 그래서 파라미터 로케이션은 필수가 아니다. -->
      <div>
      	<!-- 
      		1. 체크한 경우     : 파라미터 location이 on값을 가지고 전달된다. 
      		2. 체크 안 한 경우 : 파라미터 location이 전달되지 않는다. (서버에서 null 값으로 인식)
      	 -->
        <input type="checkbox" id="location" name="location" class="checkOne">
        <label for="location">위치정보수집 동의(선택)</label>
        <div>
          <textarea>위치정보 ...</textarea>
        </div>
      </div>
      
      <div>
       	<!-- 
      		1. 체크한 경우     : 파라미터 event가 on값을 가지고 전달된다. 
      		2. 체크 안 한 경우 : 파라미터 event가 전달되지 않는다. (서버에서 null 값으로 인식)
      	 -->
        <input type="checkbox" id="event" name="event" class="checkOne">
        <label for="event">이벤트 동의(선택)</label>
        <div>
          <textarea>이벤트 ...</textarea>
        </div>
      </div>
      
      <hr>
      
      <div>
        <input type="button" value="취소" id="btnCancel">
        <button>다음</button>
      </div>
    
    </form>
    
  </div>
  
</body>
</html>