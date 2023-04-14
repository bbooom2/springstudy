package com.gdu.app03.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {

	@GetMapping("/")
	public String welecome() {
		return "index"; // 스프링 컨트롤러는 리턴을 jsp로 해석함 앞에 프리픽스 뒤에 서픽스 붙여서 jsp로 해석 
	}
	
	@GetMapping("/first.do")
	public String first() {
		return "first";
	}
	
	@GetMapping("/second.do") // 인덱스에서 이쪽으로 오는것
	public String second() {
		return "second";
	}
	
	@GetMapping("/third.do")
	public String third() {
		return "third";
	}
	
	@GetMapping("/fourth.do")
	public String fourth() {
		return "fourth";
	}
	
	@GetMapping("/fifth.do")
	public String fifth() {
		return "fifth";
	}
}
