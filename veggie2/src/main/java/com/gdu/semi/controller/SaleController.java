package com.gdu.semi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class SaleController {
	
	@GetMapping("menu1/sale.do")
	public String wanted() {
		return "menu1/sale";
	}
	
	@GetMapping("order/arrest.do")
	public String arrest() {
		return "order/arrest";
	}

}
