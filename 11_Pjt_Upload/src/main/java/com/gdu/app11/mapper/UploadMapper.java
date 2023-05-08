package com.gdu.app11.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.app11.domain.AttachDTO;

@Mapper
public interface UploadMapper {
	
	//진짜 매퍼와 연결해줄 가상의 매퍼 
	public int addAttach(AttachDTO attachDTO); 

}
