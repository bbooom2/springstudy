package com.gdu.app11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.gdu.app11.service.UploadService;

@RequestMapping("/upload")
@Controller
public class UploadController {
	
	@Autowired
	// field
	private UploadService uploadService;
	
	@GetMapping("/list.do")
	public String list(Model model) {
		uploadService.getUploadList(model);
		return "upload/list";
	}

	@GetMapping("/write.do")
	public String write() {
		return "upload/write";
	}
	
	@PostMapping("/add.do") // add.jsp로 인식 
	public String add(MultipartHttpServletRequest multipartRequest, RedirectAttributes redirectAttributes) { // 파일첨부할때 사용 
		int uploadResult = uploadService.addUpload(multipartRequest);
		redirectAttributes.addFlashAttribute("uploadResult", uploadResult);
		return "redirect:/upload/list.do";
	}
	
	@GetMapping("/detail.do")
	public String detail(@RequestParam(value="uploadNo", required=false, defaultValue="0") int uplodNo
						, Model model) {
		uploadService.getUploadByNo(uplodNo, model);
		return "upload/detail";
	}
	
	@GetMapping("/display.do")
	public ResponseEntity<byte[]> display(@RequestParam("attachNo") int attachNo) {
		return uploadService.display(attachNo);
	}
}
