package com.gdu.app07.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app07.domain.BoardDTO;

@Repository // 나 좀 스프링컨테이너에 Bean으로 만들어 넣어 달라고 할 때 쓰는 것. 
public class BoardDAO { 
	
	// DBConfig에 있는 SqlSessionTemplate Bean을 객체로 만들어 DAO에서 사용하기
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; // 이게 모든 처리를 하니까 코드 적기만 하면 된다. 역할 : 나는 보드 엑셈엘에 잇는 셀렉트보드리스트를 만들거야 하고 불러주기만 하면됨. 할일이 매우 적어짐. 
	
	private final String NS = "mybatis.mapper.board.";
	
	//목록반환 : 매퍼로부터 어레이리스트(목록) 받아서 그대로 다시 서비스로 주는게 다오의 역할이다. 어레이리스트를 받아와서 그 목록 그대로 반환해 줘야 하니까 어레이리스트. 
	public List<BoardDTO> selectBoardList() { // 다오에서 매퍼로 줄 수 있는 게 없음. 서비스가 다오에게 주는 것도 없다는 것. 다오는 필요한 게 있으면 받아오고 그대로 전달하는 역할. 전달이 없다고하니 받아오는 것도 없음. 어렵게 생각할 필요 없음. 
		return sqlSessionTemplate.selectList(NS +"selectBoardList"); // mybatis.mapper.board. 이부분은 따로 처리해줄 것 (매퍼 이름 적기. 매퍼 네임스페이스에 있는 거 + 셀렉트보드리스트 쿼리 ( . 으로 연결) 이게 어레이리스트 이제 받아온 결과 서비스로 돌려줘야 함. 돌려줄때 쓰는 코드는 리턴이니까 앞에 리턴 기재하고 마무리.
	}
	
	public BoardDTO selectBoardByNo(int boardNo) { // 다오가 서비스한테 인트타입의 보드넘버를 받아와야 함 
		
		return sqlSessionTemplate.selectOne(NS + "selectBoardByNo", boardNo); // NS 항상 붙어야 함 호출할 타겟 , 서비스에서 보드넘버를 받아오는 것 그걸 그대로 쿼리로 넘겨주는 코드. 다오는 서비스에서 다 해놓은걸 DB로 주는 역할을 할 뿐임. 중간자 역할.
		
	}
	
	public int insertBoard(BoardDTO board) { //인서트는 다오에게 0아니면 1줌 .정해진 거였음. 정해진것이기 때문에 적지도 못하게 막았음. 무조건 0 아니면 1. 다오는 매퍼가 반환한거 그대로 주기로 했으니까 (가공x 받으면 무조건 걍 그대로 줌) 이름은 신경쓰지않기 (파라미터타입의 Board와 board) 
											// 서비스 구현할 때 타이틀, 컨텐트, 라이트 잘 확인하기. 
		return sqlSessionTemplate.insert(NS + "insertBoard", board);
	}
	
	public int updateBoard(BoardDTO board) {
		return sqlSessionTemplate.update(NS + "updateBoard", board);
	}
	
	public int deleteBoard(int boardNo) {
		return sqlSessionTemplate.delete(NS + "deleteBoard", boardNo);
	}
	
	
	// 이제 다오 구현 다 했으니까 서비스 가기! 서비스는 설계단계에서 우리에게 인터페이스만 준다고 생각하면 됨. 자 여기있어 인터페이스 가져가서 코드 구현해. 문서로 나와 있는 이야기. 반환타입이 무엇이고 다 써있음. 평가 봤던 리퀘스트 방식으로 하겠음. 
}
