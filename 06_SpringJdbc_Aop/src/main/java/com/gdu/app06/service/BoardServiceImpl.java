package com.gdu.app06.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.app06.domain.BoardDTO;
import com.gdu.app06.repository.BoardDAO;

// BoardServiceImpl 클래스 타입의 객체를 만들어서 Spring Container에 저장하시오. 

/*
@Component
1. BoardServiceImpl 클래스 타입의 객체를 만ㄷ르어서 Spring Container에 저장한다. 
2.<bean> 태그나 @Configuration + @Bean 애너테이션을 대체하는 방식이다.
3. Layer별 전용 @Component가 만들어져 있다.
	1) 컨트롤러		   : @Controller
	2) 서비스		   : @Service
	3) 레파지토리(DAO) : @Repository
	
*/

/*
	단, @Component가 @Autowired를 통해서 인식되려면 Component-Scan이 등록되어 있어야 한다.
 	Component-Scan 등록 방법 
 	방법1. <context:component-scan> - servlet-context.xml에 이미 등록되어 있다.
 	방법2. @ComponentScan - 방법 1이 되어있으므로 방법2는 굳이 할 필요 없다. 
 	
*/

@Service
// @Component 이렇게 되어있으면 스프링 컨테이너에 만들어진 bean이다. 근데 이걸로는 다충족이 안되므로 @Service 활용 
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private com.gdu.app06.repository.BoardDAO boardDAO;
	
	@Override
	public List<BoardDTO> getBoardList() {
		
		return boardDAO.selectBoardList();
	}

	   @Override
	   public BoardDTO getBoardByNo(int board_no) {
	      return boardDAO.selectBoardByNo(board_no);
	   }

	@Override
	public int addBoard(BoardDTO board) { // 서비스는 받아서 다오로 전달 
		return boardDAO.insertBoard(board);
	}

	@Override
	public int modifyBoard(BoardDTO board) {
		
		return boardDAO.updateBoard(board);
	}

	@Override
	public int removeBoard(int board_no) { //번호 전달됨 매개변수에 인트넘버가 있음. 하나만 할때는 트랜잭션 필요없음 
		return boardDAO.deleteBoard(board_no); 
	}

	//aop 활용한 트랜잭션 처리 테스트 
	
	/*
	 aop 
	-핵심로직과 공통 로직을 분리하자. service에서 핵심로직 , aop에서 공통로직 
	-탈퇴 서비스 하면 delete 서비스, 인서트(이사람이 지워졌다는 탈퇴작업) 인서트와 딜리트가 하나의 서비스에 동시에 구현되면 공통로직에서는
	해줘야하는게 트랜잭션 (지금까지 수행한 쿼리문을 모두하거나 아니면 모두안하거나) 그래서 공통로직은 따로 빼는 것이 낫다. 
	트랜잭션을 aop라는 패러다임을 통해 해결하는 것
	임의의 서비스 테스트용ㅇ으로 만들고 일부는 성공시키고 일부는 실패 시킬 것이다. 
	그래서 전체가 다 성공하지 않는 이상 전체가 다 실패되는지를 확인할 것 
	일부만 실패하고 일부만 성공하면 실패로 간주함 
	전부 성공할때만 성공으로 간주하는 것이다. 
	은행이체를 떠올리자. 내통장에서 돈빼고 남의통장에 돈 넣어줘야하는 두작업 하나가 실패하고 하나가 성공하더라도 이 두 작업은 실패로 본다. 트랜잭션 실행을 안할경우 하나는실패 하나는 성공으로 각각 본다는 것. 그래서 둘중하나 실패는 무조건 실패. 즉 아무일도 하지 않았던 것. 
	*/
	@Override
	public void testTx() { // 코드 외우려고 하지 않기.
		// 2개이상의 삽입 수정 삭제가 하나의 서비스에서 진행되는 경우에 트랜잭션 처리가 필요하다. 
		
		// 성공하는 작업 
		boardDAO.insertBoard(new BoardDTO(0, "트랜잭션제목", "트랜잭션내용", "트랜잭션작성자", null, null)); // 이것도 안들어갔을 것이라 예상 title 칼럼은 not null이기 때문에 Exception이 발생한다.
		
		// 실패하는 작업 
		boardDAO.insertBoard(new BoardDTO()); // TITLE 칼럼은 NOT NULL이기 때문에 Exception이 발생한다. 
		
		// 트랜잭션 처리가 된다면 모든 insert가 실패해야 한다. 
	}
	
	
}
