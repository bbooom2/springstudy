package com.gdu.app11.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttachDTO {

	private int attachNO;
	private String path;
	private String originName;
	private String filesystemName;
	private int downloadCount;
	private int hasThumbnail;
	private int uploadNo;
	
}
