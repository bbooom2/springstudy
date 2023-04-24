package com.gdu.app06.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.gdu.app06.domain.BoardDTO;

@Repository
public class BoardDAO { // 코드가지고 있는 걸로 만족. 6장은 aop를 배우는 게 중요한 것이고, Spring jdbc는 크게 중요한 건 x 
   
   @Autowired
   private JdbcTemplate jdbcTemplate; // Connection, PreparedStatement, ResultSet을 사용하는 스프링 클래스 
   private String sql;
   
   // 1. 목록 
   public List<BoardDTO> selectBoardList(){ //어레이리스트라고는 하지만 개별요소는 DTO
	  sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD ORDER BY BOARD_NO DESC";         
      List<BoardDTO> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(BoardDTO.class)); // RowMapper 행단위로 커넥션 처리하던게 rs, // 행단위로 가지온 rs값을 보드디티오 빈단위로 만드는 것을 빈프로퍼티로우매퍼라 한다. 
      return list;
   }
   
   // 2. 상세
   public BoardDTO selectBoardByNo(final int BOARD_NO) { // 컨트롤 + 쉬프트 + x 누르면 대문자 변환 // 매개변수의 변조를 막기 위해서 final 처리한다. (예전에 매개변수 해킹 시도가 있었다.)
	   sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD WHERE BOARD_NO = ?";
	   BoardDTO board = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(BoardDTO.class), BOARD_NO);
      return board;
   }
   
   
   // 3. 삽입
   public int insertBoard(BoardDTO board) {
	   sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";                                        
	   int result = jdbcTemplate.update(sql, new PreparedStatementSetter() { // 내부구현을 직접하는 익명타입 선택 PreparedStatementSetter()
		
		@Override
		public void setValues(PreparedStatement ps) throws SQLException {
			ps.setString(1, board.getTitle()); //첫번째 물음표에 타이틀, 보드 안에 들어있으니까 보드 겟타이틀
			ps.setString(2, board.getContent()); // 두번째 물음표 컨텐트 
			ps.setString(3, board.getWriter()); // 세번째 물음표 작성자 
			
		}
	});
      return result;
   }
   
   
   // 4. 수정
   public int updateBoard(final BoardDTO BOARD) {
      sql = "UPDATE BOARD SET TITLE = ?, CONTENT = ?, MODIFIED_AT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE BOARD_NO = ?";
      int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {
         
         @Override
         public void setValues(PreparedStatement ps) throws SQLException {
            ps.setString(1, BOARD.getTitle());
            ps.setString(2, BOARD.getContent());
            ps.setInt(3, BOARD.getBoard_no());
         }
      });
      return result;
   }
   
   // 5. 삭제
   public int deleteBoard(final int BOARD_NO) {
      sql = "DELETE FROM BOARD WHERE BOARD_NO = ?";
      int result = jdbcTemplate.update(sql, new PreparedStatementSetter() {
         
         @Override
         public void setValues(PreparedStatement ps) throws SQLException {
            ps.setInt(1, BOARD_NO);
         }
      });
      return result;
   }
}