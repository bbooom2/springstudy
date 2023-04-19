package com.gdu.app06.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
	
	@GetMapping("/")
	public String welecome() {
		return "index"; // 이게 웰컴파일임 여기서 이거 만들고 - index.jsp에서 화면에 띄울 거 확인하면 됨. 
	}


}
