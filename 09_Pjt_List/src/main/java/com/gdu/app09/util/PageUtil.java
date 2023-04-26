package com.gdu.app09.util;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter // 간혹 정보를 빼서 써야할 경우가 있는데 그럴때 게터가 필요함. 값들을 볼수만 있는 게터만 사용하겠음.
		// 세터는 의도적으로 뺐음. 세터 있게되면 잘못하면 수정할 수 있게 되니까. 
public class PageUtil {

	private int page;  			// 현재 페이지(파라미터로 받아온다)
	private int totalRecord; 	// 전체 레코드 수(DB에서 구해온다.)
	private int recordPerPage;  // 한페이지에 표시할 레코드 개수(파라미터로 받아온다) 
	private int begin; 			// 한페이지에 표시할 레코드의 시작 번호(계산한다)
	private int end;			// 한페이지에 표시할 레코드의 종료 번호(계산한다)
	
	// 1페이지부터 4페이지까지 block이라고 하겠다. 블럭당 페이지 4개 있음. 
	// 5페이지부터 6페이지까지 2번째 블락이라 하겠다. 
	private int pagePerBlock = 5; // 한 블록에 표시할 페이지의 개수(임의로 정한다) 	
	private int totalPage; 		  // 전체 페이지 개수(계산한다) 
	private int beginPage; 		  // 한 블록에 표시할 페이지의 시작 번호(계산한다)		
	private int endPage; 		  // 한 블록에 표시할 페이지의 종료 번호(계산한다)
	
	//begin과 end를 계산하기 위해서는 page, totalRecord, recordPerPage 필요
	
	public void setPageUtil(int page, int totalRecord, int recordPerPage) { 
		
		// page, totalRecord, recorPerPage 저장 
		this.page = page;
		this.totalRecord = totalRecord;
		this.recordPerPage = recordPerPage;
		
		// begin, end 계산 
		
		/*
		 	totalRecord=26, recordPerPage=5인 상황 
		 	//페이지에 따른 비긴과 엔드 계산 
		 	
		  	page	begin 	end 
		  	 1		  1	     5
		  	 2		  6 	 10
		  	 3		  11  	 16
		  	 4        16     20
		  	 5 		  21 	 26
		  	 6 		  26	 26  // 26개가 있으므로 6페이지에는 26페이지가 시작이자 끝 
		 
		 */
		
		begin = (page - 1) * recordPerPage + 1;
		end = begin + recordPerPage - 1;
		if(end > totalRecord) {  // totalRecord 26보다 큰 상황이 생기면 
			end = totalRecord;  //  totalRecord로 대체 
		}
		
		// totalPage 계산 
		totalPage = totalRecord / recordPerPage; 
		if(totalRecord % recordPerPage != 0) {
			totalPage++;
		}
		
		// beginPage, endPage 계산 
		/*
		 	totalPage=6, pagePerBlock=4인 상황 
		 	block 		beginPage 	endPage 
		 	1(1~4)			1			4
		 	2(5~6) 			5			6
		 */
		
		beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
		endPage = beginPage + pagePerBlock -1; 
		if(endPage > totalPage ) { 
			endPage = totalPage;
		}
		
	}
	
	
	
	public String getPagination(String path) { // 기본 패스 받은 다음에 
		
		// path에 ?가 포함되어 있으면 이미 파라미터가 포함된 경로이므로 &를 붙여서 page 파라미터를 추가한다. 
		// path = "/app09/employees/pagination.do?order=ASC" -> 원래 받은 패스 
		if(path.contains("?")) {
			path += "&";  // path = "/app09/employees/pagination.do&order=ASC" 
		} else {
			path += "?";  // path = "/app09/employees/pagination.do?"
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=\"pagination\">"); 
		
		/*
		 1 2 3 4 5 > 
		 
	   < 6 7 8 9 10>
	   
	   <11
	   */
		
	   // 이전블록 : 1블록은 이전블록이 없고, 나머지 블록은 이전블록이 있다. 
	   if(beginPage == 1) {
		   sb.append("<span class=\"hidden\">◀</span>");
	   }else {
		     sb.append("<a class=\"link\" href=\""+ path +"page=" + (beginPage - 1) + "\">◀</a>"); // 원하는 페이지로 돌려주는 부분은 있으나 order가 없음. 
	   }

	   
	   // 페이지 번호: 현재 페이지는 링크가 없다. 
	   for(int p = beginPage; p <= endPage; p++) {
		   if(p == page) {
			   sb.append("<span class=\"strong\">"+ p +"</span>");
		   }else {
			   sb.append("<a class=\"link\" href=\""+ path +"page="+ p +"\">" + p +"</a>");
		   }
		   
	   }
		   
		   // 다음 블록 : 마지막 블록은 다음 블록이 없고, 나머지 블록은 다음 블록이 있다. 
		   if(endPage == totalPage) {
			   
			   sb.append("<span class=\"hidden\">▶</span>");

		   }else {
			   sb.append("<a class=\"link\" href=\""+ path +"page="+ (endPage + 1) +"\">▶</a>");
			  
	   }

		sb.append("</div>");
	   return sb.toString();
	}
	
	
}
	
