package com.gdu.app05.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import com.gdu.app05.domain.BoardDTO;

//@Repository 대신 AppConfig에 @Bean이 등록되어 있다. 
 //DAO가 사용하는 @Component , @Repository도 @Component를 가지고 있음
		    // Spring Container에 Bean이 등록될 때 Singleton으로 등록되기 때문에 별도의 Singleton Pattern 코드를 작성할 필요가 없다.
public class BoardDAO {
	
	// dbcp 방식 (jdbc + DataSource)
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	private DataSource dataSource;

	// BoardDAO의 생성자 (webapp/META-INF/context.xml에 작성한 <Resource> 태그 읽기 
	public BoardDAO() {
		// JNDI 방식 : <Resource> 태그의 name 속성으로 Resource를 읽어 들이는 방식 
		try {
			Context context = new InitialContext();
			dataSource = (DataSource)context.lookup("java:comp/env/jdbc/GDJ61"); //리소스를 읽어들이면 데이타 소스가 나옴 java:comp/env 이건 정한거라 그냥 쓰면 됨 룩업의 반환타입이 오브젝트 어떤 타입의 리소스든 처리할 수 있게 메소드는 오브젝트를 반환화게 되어있어서 캐스팅 해줘야 함. 
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// private 메소드 (BoardDAO 클래스 내부에서만 사용)
	private void close() {
		try { 
			if(rs!= null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close(); // 사용한 Connection을 dataSource에게 반납한다. 
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// DAO 메소드 (BoardServiceImpl 클래스에서 사용하는 메소드) 서비스가 가져다 쓰는 건 DAO
	// DAO 메소드명 
	// 방법1. ServiceImpl의 메소드와 이름을 맞춤 
	// 방법2. DB 친화적으로 새 이름을 부여 (이론상 권장)
	
	// 1. 목록 
	public List<BoardDTO> selectBoardList() {  //보드 디티오가 여러개 들어있으므로 리스트 반환 
		List<BoardDTO> list = new ArrayList<BoardDTO>();
		try { 
			con = dataSource.getConnection(); // dataSource가 관리하는 Connection 8개 중 하나를 대여한다. 
			sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD ORDER BY BOARD_NO DESC";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery(); // 셀렉트 전용 셀렉트는 항상 이 문장으로 실행됨 
			while(rs.next()) { 
				BoardDTO board = new BoardDTO(rs.getInt(1), rs.getString(2), rs.getNString(3), rs.getString(4), rs.getString(5),rs.getString(6));
				list.add(board);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		// 확인용 
		//List<BoardDTO> list = new ArrayList<BoardDTO>();
		//list.add(new BoardDTO(1, "제목", "내용", "작성자", "작성일", "수정일"));
		return list;
	} 
	// 2. 상세 
	public BoardDTO selectBoardByNo(int board_no) {
		BoardDTO board = null;
		
		try { 
			con = dataSource.getConnection();
			sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD WHERE BOARD_NO = ?"; // 변수값은 꼭 물음표로 넣기  // 다오는 받아서 물음표 값에 
			ps = con.prepareStatement(sql);
			ps.setInt(1, board_no);
			rs = ps.executeQuery(); 
			if(rs.next()) {// 실행결과가 하나 또는 없다 
			 board = new BoardDTO(rs.getInt(1), rs.getString(2), rs.getNString(3), rs.getString(4), rs.getString(5),rs.getString(6));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return board;
	}
	
	// 3. 삽입
	public int insertBoard(BoardDTO board) {
		int result = 0;
		try {
			con = dataSource.getConnection();
			sql = "INSERT INTO BOARD VALUES(BOARD_SEQ.NEXTVAL, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'), TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS'))";
			ps = con.prepareStatement(sql);
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setString(3, board.getWriter());
			result = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// 4. 수정
	public int updateBoard(BoardDTO board) {
		int result = 0;
		try {
			con = dataSource.getConnection();
			sql = "UPDATE BOARD SET TITLE = ?, CONTENT = ?, MODIFIED_AT = TO_CHAR(SYSDATE, 'YYYY-MM-DD HH24:MI:SS') WHERE BOARD_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setString(1, board.getTitle());
			ps.setString(2, board.getContent());
			ps.setInt(3, board.getBoard_no());
			result = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
	// 5. 삭제
	public int deleteBoard(int board_no) {
		int result = 0;
		try {
			con = dataSource.getConnection();
			sql = "DELETE FROM BOARD WHERE BOARD_NO = ?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, board_no);
			result = ps.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return result;
	}
	
}
