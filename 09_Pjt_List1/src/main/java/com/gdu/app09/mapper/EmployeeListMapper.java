package com.gdu.app09.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app09.domain.EmpDTO;

@Mapper
public interface EmployeeListMapper {
	public int getEmployeeCount(); 
	public List<EmpDTO> getEmployeeListUsingPagination(Map<String, Object> map);
	//map에는 비긴과 엔드가 들어가야 함. 
	
}
