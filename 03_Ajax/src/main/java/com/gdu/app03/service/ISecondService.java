package com.gdu.app03.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gdu.app03.domain.BmiVO;

public interface ISecondService {
 
	public BmiVO execute1(HttpServletRequest request, HttpServletResponse response); // 반환타입 객체  - 잭슨이 제이슨으로 바꿔줌 
	public Map<String, Object> execute2(BmiVO bmiVO); // 반환타입 맵 - 잭슨이 제이슨으로 바꿔줌 
}
