package com.gdu.app03.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdu.app03.domain.Person;
import com.gdu.app03.service.FirstServiceImpl;
import com.gdu.app03.service.IFirstService;

@Controller
public class FirstController { 
	
   // FirstServiceImpl을 Spring Container에 Bean으로 등록시켜보자. (root-context.xml에 <bean> 태그 등록) 
   // Spring Container에 등록된 firstService Bean을 아래 필드 firstService에 주입해보자. (필드에 @Autowired 추가) 
	
   @Autowired
   private IFirstService firstService;
	
   @ResponseBody //응답 바디를 붙여줌 - > 메소드의 반환 값을 Jsp이름이 아니다. 그냥 응답하는 값이다. (요청한 곳으로 보내는 데이터이다.) 
   @GetMapping(value="/first/ajax1", produces = "application/json") // produces : 응답할 데이터의 MIME TYPE 
   public Person ajax1(HttpServletRequest request,
		   			   HttpServletResponse response) { 				// "Jackson 라이브러리"가 반환값 Person 객체를 자동으로 JSON 데이터로 변환한다.
      return firstService.execute1(request, response);
   }
   
   @ResponseBody 
   @GetMapping(value="/first/ajax2", produces="application/json")
   public Map<String, Object> ajax2(@RequestParam("name") String name,@RequestParam("age") int age) {  // 이름은 빈문자열로 하더라도 빈문자열 자체로 인식하지만 (String) 빈 문자열을 인트로 저장하려고 보면 중간에 스트링을 인트로 바꾸는데 안된다?? 이래버림 (스프링에 내부에서) 그래서 넘버포멧익셉션 -> "" 자동변환되고 있어서 안돼요 이런 메시지 뜸 - > 정상임 
	   return firstService.execute2(name, age); // "Jackson 라이브러리"가 반환값 Map을 자동으로 JSON 데이터로 변환한다.
   }
   
   @ResponseBody
   @GetMapping(value="/first/ajax3", produces="application/json")
   public Map<String, Object> ajax3(Person person) {
	   return firstService.execute3(person);
   }
   
   
   
}