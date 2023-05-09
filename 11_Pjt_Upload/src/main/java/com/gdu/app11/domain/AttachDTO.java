package com.gdu.app11.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachDTO {

	private int attachNo;
	private String path;
	private String originName; //인코딩, 중복문제(인코딩보다 큰 문제) 발생  -> UUID 필요
	private String filesystemName; // 인코딩, 중복문제(인코딩보다 큰 문제) 발생 -> UUID 필요
	private int downloadCount;
	private int hasThumbnail;
	private int uploadNo;
	
}
