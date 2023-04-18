package com.gdu.app05.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gdu.app05.repository.BoardDAO;
import com.gdu.app05.service.BoardService;
import com.gdu.app05.service.BoardServiceImpl;

@Configuration
public class AppConfig {
	
	//AppContext로 쓰던걸 AppConfig으로 이름만 변경했다. 
	@Bean // BoardServiceImpl에 @Service로 사용했었음 
	public BoardService boardService() {
		return new BoardServiceImpl();
	}
	@Bean // BoardDAO의 @Repository로 사용했었음 
	public BoardDAO boardDAO() {
		return new BoardDAO();
	}

}
