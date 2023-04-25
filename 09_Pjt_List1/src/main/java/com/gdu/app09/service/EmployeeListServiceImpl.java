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
		
		// PageUtuil(Pagination에 필요한 모든 정보) 계산하기
		pageUtil.setPageUtil(page, totalRecord, recordPerPage);
		
		// DB로 보낼 Map 만들기 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("begin", pageUtil.getBegin());
		map.put("end", pageUtil.getEnd());
		
		// begin  ~ end 사이의 목록 가져오기
		List<EmpDTO> employees = employeeListMapper.getEmployeeListUsingPagination(map);
		
		// pagination.jsp로 전달할(forward)할 정보 저장하기 
		model.addAttribute("employees", employees);
		model.addAttribute("pagination", "1, 2, 3, 4, 5"); //이거 만드는 건 시간이 조금 걸릴 예정

		
		// 선생님 오늘 오전 어떠셨나유 잘하고 계시죠?><><><><><><><><><><
		// 화이팅 하자요..
		// 갹갹..
	}
}