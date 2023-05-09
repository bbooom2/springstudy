package com.gdu.app11.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public interface UploadService {
	public void getUploadList(Model model);
	public int addUpload(MultipartHttpServletRequest multipartRequest);
	public void getUploadByNo(int uploadNo, Model model);
	public ResponseEntity<byte[]> display(int attachNo);
}
