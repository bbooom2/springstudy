package com.gdu.app08.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app08.domain.BoardDTO;

// 보드다오를 매퍼의 인터페이스로 사용할 것. - > 그래서 이름 바꿨음. BoardMapper 
// 매퍼의 쿼리문을 호출하기 위한 매소드를 1:1로 만들어서 인터페이스로 사용할 것. 
// 비교를 하자면 보드다오가 서비스가 될것이고 매퍼가 서비스임플이 되는것. 

/*
 	@Mapper
 	
 	1. mapper의 쿼리문을 직접 호출할 수 있는 인터페이스이다. 
 	2. 쿼리문의 id와 메소드명을 동일하게 처리한다. 
 	3. DBConfig.java(SqlsessionTemplate Bean이 정의된 파일)에 @MapperSca을 추가해야한다. 
 */
@Mapper
public interface BoardMapper { // 실제로는 보드xml을 부르는 것. 보드xml을 직접 부르는 방법이 없으니까 인터페이스를 하나 만들어서 부르는 것. 다오가 없어지고 매퍼를 곧바로 부르는 방식. 이름은 다오라고 쓰지않고 매퍼라고 수정한 것. 
	
		public List<BoardDTO> selectBoardList(); //xml의 아이디와 인터페이스의 이름을 맞춰주면 된다. 
		public BoardDTO selectBoardByNo(int boardNo);
		public int insertBoard(BoardDTO board);
		public int updateBoard(BoardDTO board) ;
		public int deleteBoard(int boardNo);
		public int deleteBoardList(List<String> boardNoList); // 번호를 정수로 바꿔도 되고 안 바꿔도 됨. 강사는 안 바꾸고 String이 여러개 들어있다고 보겠음. 
		public int selectBoardCount();
		
	}

