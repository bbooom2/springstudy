package com.gdu.app01.java01_into_xml;

import org.springframework.context.annotation.Bean;

public class AppContext {
	
	@Bean
	public Publisher publisher() {
		
		Publisher publisher = new Publisher();
		publisher.setName("한국출판사");
		publisher.setTel("02-123-4567");
		return publisher;
				
	}
	
	@Bean
	public Book book() {
		
		Book book = new Book();
		book.setTitle("소나기");
		book.setPublisher(publisher());
		return book;
	}

}
