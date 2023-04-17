package com.gdu.app04.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gdu.app04.service.BoardService;

@RequestMapping("/board") // 모든 mapping에 /board가 prefix(앞에) 추가됩니다. 인덱스를 똑같이 갖다두면 인덱스 웰컴작업이 안 되기 때문에 인덱스는 별도의 컨트롤러로 뺀 것.(mvc) 여기는 board로 시작하는 애들만 모아놓는 것. 구현하는 테이블 이름으로 하면 됨. board컨트롤러에서 board내가 쓰면 다른사람이 작업하면 안 되는 것.
@Controller
public class BoardController { // 서비스를 가져다 쓰는 건 컨트롤러 
	
@Autowired 
private BoardService boardService; // 3장부터 연습했던 에이작에서 썼던 것. 루트컨텍스트에서 작성한 걸 컨트롤러에서 씀. 이번에는 @Service 애너테이션을 작성함으로써 해당 오토와이얼드 사용가능. 

	@GetMapping("/list.do")  // @GetMapping 할 때마다 board를 공통으로 적지 않아도 됨. @RequestMapping("/board")애너테이션으로 기입 가능. 
	public String list(Model model) { // Model : jsp로 전달(forward)할 데이터(속성, attribute)를 저장한다. 모델을 써서 맵기반으로 저장을 하면 리퀘스트에 실제로 저장됨. 
		//컨트롤러가 해당 서비스의 값을 받아서 콘솔에 찍어 놓을 것 
		//이거 확인할때도 톰캣활용하여 서버로 돌리면 됨. 게시판 클릭시 [BoardDTO(board_no=1, title=제목, content=내용, wirter=작성자, created_at=작성일, modified_at=수정일)] 콘솔창에 나타남
		//System.out.println(boardService.getBoardList());
		return "board/list"; // list는 jsp 이름 그러니 이제 만들러 가면 됨. 
	}
	
}