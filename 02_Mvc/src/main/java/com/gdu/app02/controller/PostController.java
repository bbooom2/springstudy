package com.gdu.app02.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {
	
	@GetMapping("/post/detail.do") // index에 있는 게 들어감 
	public String detail(HttpServletRequest request) throws Exception { // name, age 파라미터가 있다. 
	//public String detail(@RequestParam("name") String name, @RequestParam("age") int age) {}
	//public String detail(Person p) {}
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		System.out.println("/post/detail.do");
		System.out.println("name: " + name + ", age : " + age );
		
		// return "redirect: 이동경로";
		return "redirect:/post/list.do?name=" + URLEncoder.encode(name, "UTF-8") + "&age=" + age; // /post/list.do 매핑으로 이동하기 
																	 // name, age 파라미터를 다시 붙인다. 
	}
	
	@GetMapping("/post/list.do")  //위에서 작성한 매핑(redirect:/post/list.do)과 이어짐
	public String list(HttpServletRequest request,  //name, age 파라미터가 있다. 
							Model model) { 	// name과 age가 전달안됐음. 전달하는 건 포워드밖에 없음. 이건 리다이렉트로 했기 때문에 전달 안 된 것. 위의 매핑(redirect:/post/list.do)에 파라미터가 없잖아~
											// name과 age 파라미터를 붙이고자 한다면 ? 붙이고 파라미터 붙여야함  return "redirect:/post/list.do?name" + name + "&age=" + age; <- 필요하면 갖다 붙여야 함. 
		
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		
		// /WEB-INF/views/post/list.jsp로 forward 하겠다
		//WEB-INF/views View resolver가 하는 프리픽스값 .jsp 는 서픽스값
		return "post/list";
		
	}
	
	@GetMapping("/post/detail.me")
	public String detailMe(HttpServletRequest request,
						   RedirectAttributes redirectAttributes) {  // 리다이렉트할때 속성 전달해주는 새로운 스프링 인터페이스
	
	
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		// Redirect 경로까지 전달되는 속성 : Flash Attribute
		redirectAttributes.addFlashAttribute("name", name); // addAttribute()가 아님을 주의하세요 
		redirectAttributes.addFlashAttribute("age", age);   
		
		return "redirect:/post/list.me";
	}
	
	@GetMapping("/post/list.me")
	public String listMe() { //  Flash Attribute는 Redirect 경로까지 자동으로 전달되므로 별도의 코드가 필요하지 않다.
		return "post/list";
	}
}
