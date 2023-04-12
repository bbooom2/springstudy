package com.gdu.app01.java01_into_xml;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		// java_into_xml 폴더 app-context.xml
		AbstractApplicationContext ctx = new GenericXmlApplicationContext("java_into_xml/app-context.xml");
		
		// 이름이 book인 Bean을 주세요!
		Book book = ctx.getBean("book", Book.class); 
		
		System.out.println("제목:" + book.getTitle());
		System.out.println("출판사: " + book.getPublisher().getName() );
		System.out.println("출판사 전화:" + book.getPublisher().getTel());
	}

}
