package com.gdu.semi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnaDTO {
	private int qnaNo;
	private String title;
	private String content;
	private String id;
	private Date writeAt;
	private String qnaReply;
}
