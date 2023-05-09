package com.gdu.app11.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class FileConfig {
	// Bean 타입은 MultipartResolver로 설정해야 한다. (인터페이스 타입으로 설정한다) 
	@Bean // 서버나 클라우드 용량 맞춰서 사용할 것. 파일 업로드하려면 Bean이 필요해서 작업해놓은 것. 
	public MultipartResolver multipartResolver() { //빈을 맞출 때 인터페이스 타입으로 설정한다. 
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setDefaultEncoding("UTF-8");
		multipartResolver.setMaxUploadSize(1024 * 1024 * 100); 	     // 전체 첨부 파일의 크기 100MB까지는 한번에 업로드 할 수 있다. (다중첨부) 
		multipartResolver.setMaxUploadSizePerFile(1024 * 1024 * 10); // 첨부 파일 하나의 크기 10MB
		return multipartResolver;
	}
}
