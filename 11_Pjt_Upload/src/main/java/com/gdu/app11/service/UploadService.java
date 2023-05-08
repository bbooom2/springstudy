package com.gdu.app11.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Service
public interface UploadService {
	
	public int addUpload(MultipartHttpServletRequest multipartRequest);

}
