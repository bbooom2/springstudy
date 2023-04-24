package com.gdu.app07.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gdu.app07.domain.BoardDTO;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate; // board.xml 불러올수있게됨 
	
	private final String NS = "mybatis.mapper.board.";
	
	public List<BoardDTO> selectBoardList() {
		return sqlSessionTemplate.selectList(NS + "selectBoardList"); //셀렉문의 반환값이 없으면(게시글없음) 그 결과 자체를 리스트로 넣는 것. 
	}
	
	public BoardDTO selectBoardByNo(int boardNo) { 
		return sqlSessionTemplate.selectOne(NS + "selectBoardByNo", boardNo); // selectOne : 셀렉문의 반환값이 없으면 null 처리 해줌.  // 상세보기할 게시글이 없는것. 
	}
	
	public int insertBoard(BoardDTO board) {
		
		return sqlSessionTemplate.insert(NS + "insertBoard", board);
		
	}
	
	public int updateBoard (BoardDTO board) {
		
		return sqlSessionTemplate.update(NS + "updateBoard", board);
	}
	
	public int deleteBoard (int boardNo) {
		return sqlSessionTemplate.delete(NS + "deleteBoard", boardNo);
	}
}
