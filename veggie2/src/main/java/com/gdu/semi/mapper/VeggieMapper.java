package com.gdu.semi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.semi.domain.QnaDTO;

@Mapper
public interface VeggieMapper {
	
	// Person
	
	// Qna
	public List<QnaDTO> getQnaList();
	
	// Sale
	

}
