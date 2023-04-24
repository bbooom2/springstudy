package com.gdu.app05.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.app05.domain.BoardDTO;
import com.gdu.app05.service.BoardService;
@RequestMapping("/board") // 모든 mapping에 /board가 prefix(앞에) 추가됩니다. 인덱스를 똑같이 갖다두면 인덱스 웰컴작업이 안 되기 때문에 인덱스는 별도의 컨트롤러로 뺀 것.(mvc) 여기는 board로 시작하는 애들만 모아놓는 것. 구현하는 테이블 이름으로 하면 됨. board컨트롤러에서 board내가 쓰면 다른사람이 작업하면 안 되는 것.
@Controller
public class BoardController { // 서비스를 가져다 쓰는 건 컨트롤러 
	
private static final Logger LOGGER = LoggerFactory.getLogger(BoardController.class);
	
@Autowired 
private BoardService boardService; // 3장부터 연습했던 에이작에서 썼던 것. 루트컨텍스트에서 작성한 걸 컨트롤러에서 씀. 이번에는 @Service 애너테이션을 작성함으로써 해당 오토와이얼드 사용가능. 

	@GetMapping("/list.do") 			// @GetMapping 할 때마다 board를 공통으로 적지 않아도 됨. @RequestMapping("/board")애너테이션으로 기입 가능. 
	public String list(Model model) { 	// Model : jsp로 전달(forward)할 데이터(속성, attribute)를 저장한다. 모델을 써서 맵기반으로 저장을 하면 리퀘스트에 실제로 저장됨. 
										//컨트롤러가 해당 서비스의 값을 받아서 콘솔에 찍어 놓을 것 
										//이거 확인할때도 톰캣활용하여 서버로 돌리면 됨. 게시판 클릭시 [BoardDTO(board_no=1, title=제목, content=내용, wirter=작성자, created_at=작성일, modified_at=수정일)] 콘솔창에 나타남
										//System.out.println(boardService.getBoardList());
		List<BoardDTO> list = boardService.getBoardList();
		LOGGER.debug(list.toString());
		model.addAttribute("boardList", boardService.getBoardList()); // db에 있는 목록이 나옴. 보드 리스트라는 이름으로 전달 
		return "board/list"; // list는 jsp 이름 그러니 이제 만들러 가면 됨. 
	}
	
	@GetMapping("/write.do")
	public String write() {
		return "board/write"; 
	}
	
	@PostMapping("/add.do")
	public String add(BoardDTO board) {// 파라미터 받는 방법 3가지 title, writer, content
		LOGGER.debug(board.toString()); // 파라미터 확인 
		LOGGER.debug(boardService.addBoard(board) + ""); // 결과 확인 
		boardService.addBoard(board); // addBoard() 메소드의 호출 결과인 int 값(0또는1)은 사용하지 않았다. 
		return "redirect:/board/list.do"; // 목록 보기로 redirect 다음에는 매핑을 기재해야한다. (리다이렉트 경로는 항상 매핑으로 작성한다.)
		}
	
	@GetMapping("/detail.do") 
	public String detail(@RequestParam(value="board_no", required = false, defaultValue="0") int board_no // 목록보기 상세보기는 model 필요 모델이 있어야 상세보기 jsp로 상세보기 내용을 전달함 
						,Model model) { 
		LOGGER.debug(board_no + ""); //파라미터 확인 
		BoardDTO b = boardService.getBoardByNo(board_no);
		model.addAttribute("b", boardService.getBoardByNo(board_no));
		LOGGER.debug(b.toString()); // 상세 결과 확인
		model.addAttribute("b", b);
		return "board/detail";
	}
	
	@GetMapping("/remove.do")
	public String remove(@RequestParam(value="board_no", required = false, defaultValue="0") int board_no) {
		boardService.removeBoard(board_no);
		return "redirect:/board/list.do"; //삭제하고 돌아가는건 목록보기
	}
	@PostMapping("/modify.do")
	public String modify(BoardDTO board) {
		LOGGER.debug(board.toString());  // 파라미터 확인
		LOGGER.debug(boardService.modifyBoard(board) + "");  // 결과 확인
		return "redirect:/board/detail.do?board_no=" +board.getBoard_no();
	}
	
	}
