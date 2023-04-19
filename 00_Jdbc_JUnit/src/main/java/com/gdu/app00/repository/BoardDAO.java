package com.gdu.app00.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.app00.domain.BoardDTO;

@Repository
public class BoardDAO {

	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private String sql;
	
	// private 메소드 - 1 (BoardDAO 클래스 내부에서만 사용)
	public Connection getConnection() {
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "GDJ61", "1111");
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	// private 메소드 - 2 (BoardDAO 클래스 내부에서만 사용)
	private void close() {
		try { 
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	// 1. 목록 
	public List<BoardDTO> selectBoardList() {
		List<BoardDTO> list = new ArrayList<BoardDTO>();
	
		try {
			
			con = getConnection();
			sql = "SELECT BOARD_NO, TITLE, CONTENT, WRITER, CREATED_AT, MODIFIED_AT FROM BOARD ORDER BY BOARD_NO DESC";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery(); 
			while(rs.next()) {
				BoardDTO board = new BoardDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6));
				list.add(board);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			close();
		}
		return list;
	}
	
	
}
