package com.gdu.app06.aop;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Component // 스프링컨테이너에 빈을 만들어 두자 // Spring Container에 ParameterCheckAOP 객체를 Bean으로 만든다. 
@Aspect 	// ParameterCheckAOP 클래스는 AOP 동작을 위한 클래스이다. 
//  @EnableAspectJAutoProxy디비 컨피그가 이미 가지고 있음. 그래서 굳이 입력해줄 필요 없음. 원래는 Aspect와 세트개념 
public class ParameterCheckAOP {
	
	
	// 포인트컷(어떤 메소드에 어드바이스(AOP 동작)를 적용할 것인가?)
	@Pointcut("execution(* com.gdu.app06.controller.*Controller.*ParamCheck(..))") // *Controller는 생략도 가능하다. 
	public void setPointCut() {
		// 이 메소드는 일종의 바지사장이다. 이름만 제공하는 역할(아무 이름이나 써도 되고 본문도 필요가 없다.) 
	}
	// 어드바이스(포인트컷에 실제로 동작할 작업 : 파라미터들의 값을 LOGGER를 이용해서 콘솔로 확인) 
	// 파라미터를 콘솔에 출력하기 위한 LOGGER 
	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterCheckAOP.class);
	
	@Around("setPointCut()")
	public void paramLogging(JoinPoint joinPoint) {
		
		// 모든 파라미터가 저장된 HttpServletRequest 가져오기 
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		
		// HttpServletRequest -> Map으로 변환하기 
		// 파라미터 -> Map의 Key로 변환된다. Map의 Key는 반복문으로 모두 순회할 수 있다. 파라미터 이름을 몰라도 됨. 
		
		Map<String, String[]> map = request.getParameterMap(); 
		
		// 콘솔에 출력할 형태 만들기 
		// [파라미터명=값] 
		String str = "";
		if(map.isEmpty()) {
			str += "[No Parameter]";
		} else {
			for(java.util.Map.Entry<String, String[]> entry : map.entrySet()) {
				str += "[" + entry.getKey() + "=" + entry.getValue() + "]"; // 자바는 배열을 문자로 바꿔주는 게 있음. = toString
				
			}
		}
		/*
		// 어드바이스 실행 
		Object obj = null; 
		try {
			obj = joinPoint.proceed(); // 실제 실행
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e; // paramLogging() 메소드가 throws Throwable 처리하고 있다. 
			
		}finally {
			// 여기서 콘솔에 String str을 로그로 남긴다. 
			// 치환 문자 : {} 
		}
		return obj;
		*/
		// 어드바이스 실행
		// 치환 문자 : {}
		LOGGER.debug("{} {} {}", request.getMethod(), request.getRequestURI(), str);  // 겟인지 포스트인지 확인, 주소확인, 파라미터 확인  (문자열로 이어붙여도 상관없음) 
	}
	
}
