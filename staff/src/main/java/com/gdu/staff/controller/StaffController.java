package com.gdu.staff.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.staff.domain.StaffDTO;
import com.gdu.staff.service.StaffService;

@Controller
public class StaffController {
	
	@Autowired
	private StaffService staffService;
	
	@ResponseBody
	@GetMapping(value="/list.json", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<StaffDTO> list1() {
		
		return staffService.getStaffList1();
	}
	
	@ResponseBody 
	@PostMapping(value="/add.do", produces="text/plain; charset=UTF-8")
	public String add1(HttpServletRequest request) {
		return staffService.addStaff1(request);
	}
	
	
	@GetMapping("/staff/search.do")
	public List<StaffDTO> search(HttpServletRequest request, Model model) {
		staffService.search(request, model);
		return staffService.getStaffList1();
	}

}
