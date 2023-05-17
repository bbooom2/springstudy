package com.gdu.semi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeavePersonDTO {
	private String id;
	private String email;
	private Date joinedAt;
	private Date leaveAt;
}
