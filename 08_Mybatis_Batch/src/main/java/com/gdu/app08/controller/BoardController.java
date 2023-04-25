package com.gdu.app08.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app08.service.BoardService;

@Controller // 특징 : 모든 보드작업이 중간 매핑으로 슬래시 보드가 포함돼서 넘어옴 
@RequestMapping("/board") // 슬래시 보드로 시작하는 모든 매핑을 받겠다. 
public class BoardController {
	
	// 컨트롤러가 대상을 주고받는건 서비스.
	//서비스애너테이션을 통해 등록시켜놨기 때문에 오토와이어드로 가져올 수 있다. 
	@Autowired
	private BoardService boardService; //보드 서비스 임플해도 되고 보드서비스 해도 되고. 인터페이스가 만들어져있을때는 인터페이스를 타입으로 사용할 수 있음. 오토와이어드는 타입이 안맞더라도 이름으로 찾을 수 있음. 그래서 선언부는 둘중 뭘 해도 상관없음. 
	
	/*
		데이터(속성) 저장 방법
		 1. forward : Model에 attribute 형식으로 저장한다. 
		 2. redirect : 원래  redirect는 속성 저장이 안 됨. 그런데 스프링은 있음. RedirectAttributes에 flashAttribute로 저장한다.   
	*/
	
	@GetMapping("/list.do") // 매핑이 합쳐져서 /board/list.do로 되는것. 
	// 목록보기라고하는 것은 getBoardList() 서비스가 반환한 어레이리스트 List<BoardDTO>를 /WEB-INF/views/board/list.jsp로 전달하는 작업을 수행하는 게 list.do가 할 일이다. 
	public String list(Model model) {// 메소드 이름은 개인의 자유 정해진바가 전혀 없음 규칙도 없음. 리스트에 파라미터 없으니까 리퀘스트 선언할 필요 없음 
									  // 반환할 데이터를 전달해야하니까 포워드 해야함. 포워드할 데이터를 모델에 저장함. 모델선언은 매개변수로 진행. 
									 // 서비스를 불러서 겟보드리스트를 불러오고 어레이리스트를 저장해서 리스트.jsp로 보내는 코드 적으면 됨. 리스트jsp에서는 boardList <- 이 이름으로 쓰이고 있음 
		model.addAttribute("boardList", boardService.getBoardList()); // 보드서비스의 겟보드리스트 부르면 호출 결과  = 어레이리스트 
		return "board/list"; // 뷰리졸버가 /WEB-INF/views/, .jsp 자동으로 붙여줌 
	}
	
	// 호랑이 시절 ModelAndView 클래스 
	// 옛날코드인데 구글링 할 때 보이면 뒤로가기 누르기. 
	/*
	@GetMapping("/list.do")
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("boardList",boardService.getBoardList());
		mav.setViewName("board/list");
		return mav;
	}
	*/
	
	// getBoardByNo() 서비스가 반환한 BoardDTO를 /WEB-INF/views/board/list.jsp로 전달한다. 
	@GetMapping("/detail.do")
	public String detail(HttpServletRequest request, Model model) {
		model.addAttribute("b", boardService.getBoardByNo(request)); // 디테일에 b라고 해놨기 때문에 여기서도 b라고 기재 
		return "board/detail";
	}
	
	@GetMapping("/write.do")
	public String write() {
		return "board/write";
	}
	
	// addBoard() 서비스 내부에 location.href를 이용한 /board/list.do 이동이 있기 때문에 응답할 View를 반환하지 않는다. 
	@PostMapping("/add.do")
	public void add(HttpServletRequest request, HttpServletResponse response) { //attribute로 하면 /list.do까지만 접근 가능. 
		boardService.addBoard(request, response); // 삽입의 경우 에드리절트로 0또는 1d이 들어올텐데 이걸 리스트jsp에서 확인할 수 있을것. el형태로 확인 가능. 

	}
	
	//modifyBoard() 서비스 내부에 location.href를 이용한 /board/detail.do 이동이 있기 때문에 응답할 View를 반환하지 않는다. 
	@PostMapping("/modify.do")
	public void modify(HttpServletRequest request, HttpServletResponse response) { //attribute로 하면 /list.do까지만 접근 가능. 
		boardService.modifyBoard(request, response); // 삽입의 경우 에드리절트로 0또는 1d이 들어올텐데 이걸 리스트jsp에서 확인할 수 있을것. el형태로 확인 가능. 

	}
	
	// removeBoard() 서비스 내부에 location.href를 이용한 /board/list.do 이동이 있기 때문에 응답할 View를 반환하지 않는다. 
	@PostMapping("/remove.do")
	public void remove(HttpServletRequest request, HttpServletResponse response) { //attribute로 하면 /list.do까지만 접근 가능. 
		boardService.removeBoard(request, response); // 삽입의 경우 에드리절트로 0또는 1d이 들어올텐데 이걸 리스트jsp에서 확인할 수 있을것. el형태로 확인 가능. 

	}
	
	// removeBoard() 서비스 내부에 location.href를 이용한 /board/list.do 이동이 있기 때문에 응답할 View를 반환하지 않는다.
	@PostMapping("/removeList.do")
	public void removeList(HttpServletRequest request, HttpServletResponse response) {
		boardService.removeBoardList(request, response);
	}
	
}
