package com.gdu.app07;


import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// JUnit4
@RunWith(SpringJUnit4ClassRunner.class) //제이유닛 사용하겠다. 


// ContextConfiguration -> 빈 관리를 어떻게 하고 있는지  - 루트컨텍스트 이용, 컨피그에 만들었던 컨피그레이션과 빈태그, 직접 클래스에 덧붙이는 애너테이션
// 테스트에서 사용할 Bean이 @Component로 생성되었기 때문에 component-scan이 작성된 servlet-context.xml의 경로를 작성한다.
@ContextConfiguration(locations = {"file:src/main/webapp/WEP-INF/spring/appServlet/servlet-context.xml"})

// 테스트 순서를 메소드명의 알파벳순으로 수행
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

//WebApplicationContext을 사용하기 위해서 필요한 애너테이션
@WebAppConfiguration 

public class BoardControllerTest {
	
	/*
	 	Mock 테스트 
	 	
	 	1. 가상 MVC 테스트이다. 
	 	2. Controller를 테스트할 수 있는 통합 테스트이다. // 매핑을 이용해야 컨트롤러 구동 가능
	 	3. method(겟인지 포스트인지) + mapping을 이용해서 테스트를 진행한다. 
	 	
	 */

	// Mock 테스트를  수행하는 객체
	// WebApplicationContext에 의해서 생성된다.
	private MockMvc mockMvc;
	
	//@WebApplication이 있어야 자동주입(@Autowired)이 가능하다.
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	// LOGGER 등록 
	private static final Logger LOGGER = LoggerFactory.getLogger(BoardControllerTest.class);
	
	// @Before
	// 1. 모든 @Test (해당 수업에서는 목록, 상세, 삽입, 수정, 삭제) 수행 이전에 실행된다.
	// 2. MockMvc mockMvc 객체를 @Before에서 build한다. 
	
	//제이유닛 단위테스트에서는 setup 체크 후 생성하면 간단한 형식으로 @Before가 만들어진다.
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders
				  .webAppContextSetup(webApplicationContext)
				  .build();
	}
	
	// 삽입결과는 플래시애트리뷰트를 사용한다는 것을 기억. 
	@Test
	public void a1삽입테스트() throws Exception {
		
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders // request builder를 실행주는 메소드 
				.post("/board/add.do") // 전달  		//@PostMapping("/board/add.do")
				.param("title", "테스트제목") 			// 파라미터 
				.param("content", "테스트내용")			// 파라미터
				.param("writer", "테스트작성자"))		// 파라미터 
					.andReturn() 						// 삽입결과 	
					.getFlashMap() 						//출력이플래시애트리뷰트일때 겟플래시맵을 사용한다. FlashAttribute로 저장된 결과 확인 
						.toString()); 					//최종 결과를 문자열로 바꿨다.

	}
	
	@Test
	public void a2수정테스트() throws Exception {
		
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders // request builder를 실행주는 메소드 
				.post("/board/modify.do") // 전달  		//@PostMapping("/board/modify.do")
				.param("title", "테스트제목2") 			// 파라미터 
				.param("content", "테스트내용2")		// 파라미터
				.param("boardNo", "1"))					// 파라미터 _ 파라미터는 늘 String을 요구하므로 큰따옴표 해야함. 
					.andReturn() 						// 수정결과 	
					.getFlashMap() 						//출력이플래시애트리뷰트일때 겟플래시맵을 사용한다. FlashAttribute로 저장된 결과 확인 
						.toString()); 					//최종 결과를 문자열로 바꿨다.

	}
	
	@Test
	public void a3상세조회테스트() throws Exception {
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/detail.do")			// @Getmapping("/board/detail.do")
					.param("boaredNo", "1"))		// 파라미터 
						.andReturn()				// 조회결과
						.getModelAndView()			// Model에 저장된 조화 결과를 가져오기 위해서 ModelAndView를 먼저 가져옴. 
						.getModelMap()				// ModelAndView에서 Model을 가져옴
							.toString());			// 최종 결과를 문자열로 바꿨다. 
	}
	
	@Test
	public void a4목록테스트() throws Exception {
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders
				.get("/board/list.do"))			// @Getmapping("/board/list.do")
						.andReturn()			// 목록조회결과
						.getModelAndView()		// Model에 저장된 조화 결과를 가져오기 위해서 ModelAndView를 먼저 가져옴. 
						.getModelMap()			// ModelAndView에서 Model을 가져옴
							.toString());		// 최종 결과를 문자열로 바꿨다. 
	}
	
	
	@Test
	public void a5삭제테스트() throws Exception {
		
		LOGGER.debug(mockMvc.perform(MockMvcRequestBuilders // request builder를 실행주는 메소드 
				.post("/board/remove.do") // 전달  		//@PostMapping("/board/remove.do")
				.param("boardNo", "1"))					// 파라미터 _ 파라미터는 늘 String을 요구하므로 큰따옴표 해야함. 
					.andReturn() 						// 수정결과 	
					.getFlashMap() 						//출력이플래시애트리뷰트일때 겟플래시맵을 사용한다. FlashAttribute로 저장된 결과 확인 
						.toString()); 					//최종 결과를 문자열로 바꿨다.

	}

}
