package com.gdu.app01.xml06;

import java.util.List;

public class GymMember {

	
	// field
	private String name;
	private List<String> cources;
	private BmiCalculator bmiCalc;
	
	// contsructor
	public GymMember(String name, List<String> cources, BmiCalculator bmiCalc) {
		super();
		this.name = name;
		this.cources = cources;
		this.bmiCalc = bmiCalc;
	}
	
	// method 
	public void memberInfo() {
		System.out.println("이름: " + name);
		
		// 리스트니까 포문 쓰겠음 
		for(int i = 0; i < cources.size(); i++) {
			System.out.println((i+1) + "번째 종목:" + cources.get(i));
		}
		bmiCalc.bmiInfo();
	}
}
