package com.gdu.app08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MvcController {
	
	@GetMapping("/") 
	public String welecome() {
		return "index"; 			
	}
	

}
