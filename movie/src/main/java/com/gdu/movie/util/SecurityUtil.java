package com.gdu.movie.util;

import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
	
	//크로스 사이트 스크립팅(Cross Site Scripting) 방지하기 
	//전달된 스트링을 가공을 해서 반환시켜주는 것 
	//태그가 < 들어왔다가 나갈때는 &lt; 이렇게 나가는 것 
	public String preventXSS (String str) {
		
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&gt;");
		return str;
				
	}
	

}
