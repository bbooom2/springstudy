package com.gdu.semi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdu.semi.mapper.VeggieMapper;

@Service
public class SaleServiceImpl implements SaleService {
	
	@Autowired
	private VeggieMapper veggieMapper;

}
