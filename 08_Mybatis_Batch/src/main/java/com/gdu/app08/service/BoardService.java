package com.gdu.app08.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.app08.domain.BoardDTO;

// 다오로부터 받은걸 컨트롤러로 그대로 넘겨주고 결과는 컨트롤러에서 처리 예정 

public interface BoardService { // 목록서비스 소개하겠음. 서비스는 다오에게 값을 주고 다오에게 값을 받아옴 받아오는 것에 대한 이야기. 목록의 경우 다오로부터 어레이리스트를 받아오게 됨. 
	
	public List<BoardDTO> getBoardList(); //다오로부터 받아온 걸 서비스는 서비스를 불러주는 건 컨트롤러로 전달할 예정 // 다오에게 줄것없음. 컨트롤러에서 받아오지 않을 것. jsp에서 컨트롤러로 넘기면 컨트롤러는 세가지 방법으로 받을 수 있음. http서블릿 리퀘슽, 리퀘스트파람 애너테이션, 객체 이렇게 세가지로 받을 수 있음. 
										  // 다음프로젝트보다는 다오와 이름 맞출 것. 이 프로젝트에서는 이름 새로 만들어서 진행. 
	public BoardDTO getBoardByNo(HttpServletRequest reuqest); 
	public void addBoard(HttpServletRequest reuqest, HttpServletResponse response); // 컨트롤러로부터는 http서블릿 리퀘스트 받을 것 벗 보낼때는 Board로 보낼것. 다오는 받았으면 그대로 나가세요지만 서비스는 아님. 서비스는 로직 짜는 곳. 필요한 로직이 있으면 얼마든지 자바코드를 써서 만들 수 있음.
	public void modifyBoard(HttpServletRequest reuqest, HttpServletResponse response); // addBoard와 동일한 방식 
	public void removeBoard(HttpServletRequest request, HttpServletResponse response); // 제이에스피로부터 파라미터가 넘어갔을텐에 컨트롤러는 파라미터를 http로 받겠다는 것 이거 그대로 서비스에게 주겠다. 가져가서 너가 꺼내라는 것. 그럼 꺼낸 정보를 인트값으로 바꿔서 주겠다.
	public void removeBoardList(HttpServletRequest request, HttpServletResponse response); // 제이에스피로부터 파라미터가 넘어갔을텐에 컨트롤러는 파라미터를 http로 받겠다는 것 이거 그대로 서비스에게 주겠다. 가져가서 너가 꺼내라는 것. 그럼 꺼낸 정보를 인트값으로 바꿔서 주겠다.
	public void getBoardCount();
}


