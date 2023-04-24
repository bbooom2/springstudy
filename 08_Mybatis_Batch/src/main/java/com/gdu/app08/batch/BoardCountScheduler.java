package com.gdu.app08.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.gdu.app08.service.BoardService;

 // 원래 컨피그레이션에 빈 애너테이션 필요한대 컴퍼넌트가 ㄷ이 둘을 대체함 
public class BoardCountScheduler { // 동작할때 서블릿컨텍스트 읽어내고 루트컨텍스트 읽고 - 스케쥴을 읽어야함. 
	
	@Autowired
	private BoardService boardService;
	@Scheduled(cron="0 0/1 * * * ?") // 크론식 : 1분마다 
	public void execute() { // 타임을 적는 방식이 cron "0 0/1 * * * *"이라 한다면 1분마다. 휴면회원같은거 자동화시킬수도 있고 스케쥴드를 통해 처리할 수 있음. 
		boardService.getBoardCount();
	}

}
