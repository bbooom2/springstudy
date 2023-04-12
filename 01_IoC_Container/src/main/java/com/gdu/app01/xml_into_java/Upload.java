package com.gdu.app01.xml_into_java;

public class Upload {
	
	// 포함관계 클래스 만드는 이유 : 디비관점이 들어가는 것. 업로드내용이 있고 첨부내용이 있고 업로드에 첨부가 하나가 아닌 여러개 들어가는 경우도 있기 때문에 따로 만들어서 참고할수있는 관계를 만들어 주는 것. 
	
	// field
	private String title;
	private Attach attach;
	
	// default constructor
	
	// method(getter, setter)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Attach getAttach() {
		return attach;
	}
	public void setAttach(Attach attach) {
		this.attach = attach;
	} 
	

}
