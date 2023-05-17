package com.gdu.semi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.semi.service.QnaService;

@RequestMapping("/qna")
@Controller
public class QnaController {
	@Autowired
	private QnaService qnaService;
	
	@GetMapping("/list.do")
	public String qnaList(Model model) {
		model.addAttribute("qnaList", qnaService.getQnaList());
		return "qna/list";
	}
}
