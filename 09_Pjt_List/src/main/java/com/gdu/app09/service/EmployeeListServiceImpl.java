package com.gdu.app09.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.gdu.app09.domain.EmpDTO;
import com.gdu.app09.mapper.EmployeeListMapper;
import com.gdu.app09.util.PageUtil;

import lombok.AllArgsConstructor;

// 이제는 페이지네이션 아니면 무한스크롤 무조건 사용할 예정.

@AllArgsConstructor // 이것으로 인해서 필드에 자동으로 @Autowired 처리가 된다. 생성자는 오토와이어드 생략할 수 있으니까. 생성자는 만들어두기만 하면 자동주입된다. 
@Service
public class EmployeeListServiceImpl implements EmployeeListService {

	// field
	private EmployeeListMapper employeeListMapper; // 스프링 컨테이너의 빈으로 저장되어있음 매퍼에 컴포넌트 처리가 안되어있어도 오토와이어드 됨
	private PageUtil pageUtil; // 컴포넌트에 의해서 빈으로 저장되어있음
	
	
	
	@Override
	public void getEmployeeListUsingPagination(HttpServletRequest request, Model model) {
		// 파라미터 page가 전달되지 않는 경우 page=1로 처리한다. 
		Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
		int page = Integer.parseInt(opt1.orElse("1"));
		
		// 전체 레코드 개수를 구한다. 
		int totalRecord = employeeListMapper.getEmployeeCount();
		
		// 세션에 있는 recordPerPage를 가져온다.세션에 없는 경우 recordPerPage=10으로 처리한다. 
		HttpSession session = request.getSession();
		Optional<Object> opt2 = Optional.ofNullable(session.getAttribute("recordPerPage")); // 세션에 올라갈 수 있는 타입은 정해져있음 - 오브젝트 
		int recordPerPage = (int)(opt2.orElse(10));		
		
		// 파라미터 order가 전달되지 않는 경우 order=ASC로 처리한다. 
		Optional<String> opt3 = Optional.ofNullable(request.getParameter("order"));
		String order = opt3.orElse("ASC"); // 첫 화면일때는 안옴. 페이징첫화면처럼 order값 없음. 항상 오는 게 아님. 아무것도 안온거에 대해서는 우리가 정하면 되는데 우리는 첫 기본정렬을 오름차순으로 진행 
		// 현재 ASC로 되어있다면 DESC 반대를 보내주겠다. 서비스에서 ASC를 전달했다? 페이지네이션엔 DESC가 저장되어있어야 그 다음에 처리됨 
		
		// 파라미터 column이 전달되지 않는 경우 column=EMPLOYEE_ID로 처리한다.
		Optional<String> opt4 = Optional.ofNullable(request.getParameter("column"));
		String column = opt4.orElse("EMPLOYEE_ID"); 
		
		// PageUtuil(Pagination에 필요한 모든 정보) 계산하기
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		map.put("order", order);
		map.put("column", column);
		
		// begin  ~ end 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingPagination(map);
		
		// pagination.jsp로 전달할(forward)할 정보 저장하기 
		model.addAttribute("employees", employees);
		model.addAttribute("pagination", pageUtil.getPagination(request.getContextPath() + "/employees/pagination.do?column=" + column +"&order=" + order));
		model.addAttribute("beginNo", totalRecord - (page - 1) * recordPerPage);
		switch(order) {
		case "ASC" : model.addAttribute("order", "DESC"); break; // 현재 ASC 정렬이므로 다음 정렬은 DESC라고 Jsp에 알려준다.
		case "DESC" : model.addAttribute("order", "ASC"); break; 
		}
		model.addAttribute("page", page);
		
		// 선생님 오늘 오전 어떠셨나유 잘하고 계시죠?><><><><><><><><><><
		// 화이팅 하자요..
		// 갹갹..
	}
	 @Override
	public Map<String, Object> getEmployeeListUsingScroll(HttpServletRequest request) {
			// Map에 Object를 지정해서 ArrayList도 담을 것. 실무처럼 작업할 예정. 
			// 파라미터 page가 전달되지 않는 경우 page=1로 처리한다. 
			Optional<String> opt1 = Optional.ofNullable(request.getParameter("page"));
			int page = Integer.parseInt(opt1.orElse("1"));
			
			// 전체 레코드 개수를 구한다. 
			int totalRecord = employeeListMapper.getEmployeeCount();
			
			// recordPerPage=9 한화면에 3X3으로 구현 스크롤한번에 9개씩 보여주기 
			int recordPerPage = 9;
			
			// PageUtuil(Pagination에 필요한 모든 정보) 계산하기
			pageUtil.setPageUtil(page, totalRecord, recordPerPage);
			
			// DB로 보낼 Map 만들기 
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("begin", pageUtil.getBegin());
			map.put("end", pageUtil.getEnd());
		
			
			// begin  ~ end 사이의 목록 가져오기
			List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingScroll(map);
			
			// scroll.jsp로 응답할 데이터 
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("employees", employees);
			resultMap.put("totalPage", pageUtil.getTotalPage());
			
			// 응답 
			return resultMap;
			
			/*
			  resultMap이 json으로 변환될 때의 모습 
			  {
			  	 "employees" : [
			  	 
			  	 "employeeId": 100,
			  	 "firstName": "Steven",
			  	 "lastName": "King",
			  	 ...
			  	 "deptDTO": {
			  	 "departmentId" : 90,
			  	 "departmentName": "Executive",
			  	 ...
			  	 }
			  	 }
			  	 ]
			  	 }
			  
			 */
		
	}
	
}