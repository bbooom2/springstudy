package com.gdu.app07.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.app07.domain.BoardDTO;
import com.gdu.app07.repository.BoardDAO;

@Service  // 스프링컨테이너에 보관 필요. 서비스라고 붙여줘야 스프링 컨테이너에 빈으로 들어가서 사용 가능. 안붙이면 다른데다 할 수는 있겠지만... 그냥 하라는대로 하기.
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public List<BoardDTO> getBoardList() { // 목록을 가지고 오는 서비스. 목록을 가지고 오는 서비스는 컨트롤러한테서 받아온 게 없음. 파라미터가 없다는 얘기. 그러면 얘는 받아은 게 없으니까 주기만 하면 됨 (돌려주는 것 리턴 ) 리턴만 하면 됨. 
	
		return boardDAO.selectBoardList(); 
	}

	@Override
	public BoardDTO getBoardByNo(HttpServletRequest request) { // jsp에서 상세보기 하고 싶음. /detail.do?boardNo=5 이런식으로. 그 요청이 오면 컨트롤러는 전부다 http서블릿 리퀘스트로 받기로 함. 이 7장에서는. 리퀘스트에 들어있는게 보드넘버. 보드넘버가 들어있다고 생각하고 코드 연결. 서비스 본문에서는 보드넘버를 뺄 것. 뺀 걸 다오측에 주는 것. 다오가 받을 준비 int boardNo로 하고 있음. jsp가 디테일 요청하면서 번호 붙여서 보냈을 것. 그럼 그 번호를 리퀘스트에 보관하고 있다가 리퀘스트에 꺼내는 타이밍에서 서비스에서 그 번호를 꺼내서 다오에게 전달해주면 다오는 작업다되어있으니까 다시 돌아오는 것. 컨트롤러로 다시 돌려보내는.  
		// 파라미터 boardNo가 없으면(null, "") 0을 사용한다. 없는 경우 두가지를 위해 고전타입 사용 
		// 옵셔널은 택배가 안왔을 때 내가 하나 줄게의 느낌으로 사용 (null 처리만 가능 빈문자열 처리 불가) 
		// 오기만 했는데 텅 비어있으면 "" 
		String strBoardNo = request.getParameter("boardNo"); //고전타입, 왜 스트링으로 받냐면 파라미터는 백프로 스트링임. 약속이지. 파라미터는 그냥 스트링이어야 함. 그것이 우리의 첫 요청수업에서 했던 약속임. 바꿀 수 없음. 
		int boardNo = 0; //초기화 
		if(strBoardNo != null && strBoardNo.isEmpty() == false) {  
			boardNo = Integer.parseInt(strBoardNo);  // 숫자가 아닌것까지 잡고 싶다면 jsp에서 날아가지도 못하게 잡으세요. jsp함수 중 isNaN() ->숫자가 아닌거 잡아내는 코드. 자바스크립트 코드. 알럿창 띄우고 아예 요청을 하지 않으면 됨. 여기서 하려면  트라이캐치 써서 하면 되긴함.. 그거까지 하진 말자 
		} 											// 변환된 보드넘버 아니면 0을 누구에게 주라? 다오에게 줘라 그럼 보드디티오가 올 것이다. 
		return boardDAO.selectBoardByNo(boardNo);  // 다오의 호출은 리턴에 있을 것. 다오의 결과는 그냥 돌려보내는 것. 
	}

	@Override
	public int addBoard(HttpServletRequest request) {
		try { 	
		// 에드보드에 파라미터 세개가 오길 바람. title, content, writer 있길 희망함. 세가지를 받아와야 정상동작 가능. 
		// 숫자로 바꿔줘야 하는 것 없음. (세가지 파라미터 중) 
		// 그래서 그냥 스트링으로 받으면 됨. 
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String writer = request.getParameter("writer");
		// BoardDAO로 전달할 BoardDTO를 만든다. 
		BoardDTO board = new BoardDTO();	 // 디폴트 형식으로 만들었음 
		board.setTitle(title);				 // title 전달 
		board.setContent(content); 			 // content 전달 
		board.setWriter(writer); 			 // writer 전달 
		return boardDAO.insertBoard(board);  // insert 보드에서 받은 0과 1은 그대로 돌려보내기로 했으니까 리턴 기재하면 끝. 
		 } catch (Exception e) {
			 return 0;						 //실패 시 0 반환 
		 }
	}

	@Override
	public int modifyBoard(HttpServletRequest request) { // 업데이트는 보드를 받아옴. 보드안에 타이틀, 컨텐트, 보드넘버가 들어있음. 세가지가 다들어있어야함. 애드와 다르게 필요함. writer 게시글의 작성자는 당연히 못바꿈, 작성일도 마찬가지. 수정불가. 전달받지 않아도 수정되는건 수정데이트 이거는 자동처리되니까 받아올 필요 없음. 
	 try { 	
		// 파라미터 title, content, boardNo를 받아온다. 보드넘버는 없어도 0처리 하지 않음. 
		// 처음에 제이에스피에서 요청할때를 생각. 수정할때 포스트 메소드 사용할 것. 포스트 요청을 한다는 건 주소창으로 조작이 불가능 하다는 것. 보드넘버를 의도적으로 안보낸다는 뜻. 임의의 게시글을 수정하기 위해서 임의의 보드넘버를 사용자가 붙여서 보내는 이런 작업을 아예 못하게 되어있음. 실제로 보드넘버가 무조건 온다고 봐야 함. 안 오는 상황이 있기는 하겠지만 (=개발자가 개발하다 말았을 경우) ㅋ .. ? 어쨌든 무조건 들어있어야 함. 
		String title = request.getParameter("title"); // 쿼리문 타이틀에 낫널로 되어있어서 널값넣으면 널포인트익셉션 나옴 만약 테스트할때 널포인트익셉션 안나오게 하려면 캐치트라이 지금처럼 해놓으면 됨
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt(request.getParameter("boardNo")); // 우리는 여기서 오류가 나면 우리가 처리해야함. 상세보기는 겟방식으로 하니까 얼마든지 주소창에서 조작 가능. 디테일 두 뒤에 물음표 없이 요청 가능. 임의의 번호 입력해서 없는 번호 입력해서 다 전달가능. 
		// BoardDAO로 전달할 BoardDTO를 만든다. 
		BoardDTO board = new BoardDTO(); //디폴트 형식으로 만들었음 
		board.setTitle(title); // 타이틀 전달 
		board.setContent(content); // 컨텐트 전달 
		board.setBoardNo(boardNo); //보드 넘버 전달 
		return boardDAO.updateBoard(board);
	 } catch (Exception e) {
		 return 0; //실패시 0 반환 
	 	}
	}
	
	@Override
	public int removeBoard(HttpServletRequest request) { // 겟요청 포스트요청하는 방법 둘다 한적있음. 겟으로 할때는 구현이 쉬워 그대신 누군가 삭제 요청하는 이름을 알아버리면 번호가 이렇게 생겼다는 것도 알았어 겟은 주소창을 이용해서 하는거니까 권한도 없는 사용자가 주소에 입력을 하면 지워져버림. 
		try {										    // 애초에 그냥 포스트(주소창으로 조작 불가) 방식으로 요청하는 것으로 바꿀 것. jsp 그대로 쓰는데 삭제 요청하는 부분만 수정할 것. 
		// 파라미터 boardNo를 받아온다. 
		int boardNo = Integer.parseInt(request.getParameter("boardNo"));
		return boardDAO.deleteBoard(boardNo);
		}catch (Exception e) {
			return 0;
		}
	}
	
	// 트랜잭션 확인 
	@Transactional //인서트, 업데이트, 딜리트를 두개 이상 사용할 때 사용. 
	@Override
	public void testTx() {
		boardDAO.insertBoard(new BoardDTO(0, "타이틀", "콘텐트", "작성자", null, null)); // 성공 
		boardDAO.insertBoard(new BoardDTO()); // 실패
	}
}
