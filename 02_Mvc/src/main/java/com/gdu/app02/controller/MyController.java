package com.gdu.app02.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
		@Controller
		안녕. 난 컨트롤러야.
		@Component를 포함하고 있어서 자동으로 Spring Container에 Bean으로 등록되지. 
		나는 스프링에 의해서 사용되고 있어. 
 */
@Controller
public class MyController {
	
	// 메소드 : 요청과 응답을 처리하는 단위 
	/*
		메소드 작성 방법 
		1. 반환타입 : String (응답할 Jsp의 이름을 작성한다.) 
		2. 메소드명 : 아무 일도 안 한다. 
		3. 매개변수 : 요청과 응답에 따라 다르다. (요청이 필요한 경우 HttpServletRequest, 응답이 필요하면 HttpServletResponse 등) 
	*/
	
	/*
		@RequestMapping
		1. value  : URL Mapping을 작성한다. (동작할 주소를 작성한다.) 
		2. method : Request Method를 작성한다. (GET, POST, PUT, DELETE 등) 
	
	*/
	
	/*
		@RequestMappingvalue="/", method=RequestMethod.GET)
		URL Mapping이 "/"이면 context path 경로를 의미한다. (http://localhost:9090/app02
	*/	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index() {
		return "index"; // ViewResolverd에 의해서 해석된다. (servlet-context.xml을 참고하자.) 
						// /WEB-INf/views/index.jsp 
	}
	
	/*
		@RequestMapping 작성 예시
		@RequestMapping(value="/list.do", method=RequestMethod.GET)
		@RequestMapping(value="list.do", method=RequestMethod.GET)	 value는 슬래시(/)로 시작하지 않아도 된다. 
		@RequestMapping(value="list.do")							 GET 방식의 method는 생략할 수 있다. 
		@RequestMapping("/list.do")									 value 속성만 작성하는 경우에는 값만 작성할 수 있다. 
		
	*/
	
	@RequestMapping(value="/list.do", method=RequestMethod.GET)
	public String list() { 
		return "board/list"; // 실제 처리되는 경로 : /WEB-INF/views/list.jsp
		
		/*
		 	return "/board/list";
		 	실제 처리되는 경로 : /WEB-INF/views//board/list.jsp  (중간에 슬래시가 2개 포함된다.) 
		 	// 리턴에 /(슬래시)를 붙이게 되면 서블릿 컨텍스트가 앞에 붙여주는게 슬래시로 끝나는데 뷰리졸버가 붙여준 슬래시하고 리턴의 슬래시하고 합치면 2개의 슬래시가 만들어지게 됨. 하지만 하나로 이어지게 하겠다는 것. 
		 	하지만 실제로는 /WEB-INF/views/list.jsp 경로로 처리된다. 
		 	// 수업에서 매핑은 슬래시로 시작하기, 경로는 슬래시로 시작하지 않기. 
		 */
		
	}

}

