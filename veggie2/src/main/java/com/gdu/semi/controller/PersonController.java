package com.gdu.semi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/person")
@Controller
public class PersonController {
	
	@GetMapping("/login.do")
	public String personLogin() {
		return "person/login";
	}
	@GetMapping("/edit.do")
	public String personEdit() {
		return "person/edit";
	}
}
