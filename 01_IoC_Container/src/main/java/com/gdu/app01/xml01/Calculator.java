package com.gdu.app01.xml01;

public class Calculator { // new Calculator() -> <bean> / new Student() -> <bean> => getBean()(빈가져다쓰기 배울 예정) 
	
	// no field
	
	// default constructor
	
	// method
	public void add(int a, int b) {
		System.out.println(a + "+" + b + "=" + (a + b));
		
	}
	public void sub(int a, int b) {
		System.out.println(a + "-" + b + "=" + (a - b));
		
	}
	public void mul(int a, int b) {
		System.out.println(a + "*" + b + "=" + (a * b));
		
	}
	public void div(int a, int b) {
		System.out.println(a + "/" + b + "=" + (a / b));
		
	}
}
