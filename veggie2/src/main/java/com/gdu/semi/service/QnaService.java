package com.gdu.semi.service;

import java.util.List;

import org.springframework.ui.Model;

import com.gdu.semi.domain.QnaDTO;

public interface QnaService {

	public List<QnaDTO> getQnaList();
	
}
