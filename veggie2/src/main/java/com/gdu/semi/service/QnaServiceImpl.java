package com.gdu.semi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.semi.domain.QnaDTO;
import com.gdu.semi.mapper.VeggieMapper;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	private VeggieMapper veggieMapper;
		
	@Override
	public List<QnaDTO> getQnaList() {
		return veggieMapper.getQnaList();
	}
}
