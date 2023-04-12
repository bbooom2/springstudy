package com.gdu.app01.xml04;

import java.sql.Connection;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MyDAO {
	// Connection을 가져다 사용할 DAO
	
	
	// field
	private Connection con;
	
	
	// singleton pattern - app-context.xml에서 <bean> 태그를 만들 때 사용된다. 
	
	/* 
	 scope = "singleton" 으로 인해 필요 없어진 코드임 
	 
	 singleton pattern
	private static MyDAO dao = new MyDAO();
	private MyDAO() { }
	public static MyDAO getInstance() {
		return dao;
	}
	*/
	
	// method
	public Connection getConnection() {
		

		// Spring Container에 만들어 둔 myConn Bean 가져오기 
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("xml04/app-context.xml");
		con = ctx.getBean("myConn", MyConnection.class).getConnection();
		ctx.close();
		return con;
		
	}
	
	public void close() {
	      
	      try { 
	         if(con != null) {
	            con.close(); 
	         } 
	      } catch(Exception e) {
	         e.printStackTrace();
	         
	      }
	   }
	   
	   public void list() {
	      
	      con = getConnection();
	      
	      close();
	   }

	}
	
