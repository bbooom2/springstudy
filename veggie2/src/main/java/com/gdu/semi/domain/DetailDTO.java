package com.gdu.semi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailDTO {
	private int fdNo;
	private String fdWriter;
	private String fdTitle;
	private String fdTool;
	private String content1;
	private String content2;
	private String fdReply;
	private Date fdDate;
	private Date fdEdit;
}
