package com.gdu.semi.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	private int perNo;
	private String id;
	private String pw;
	private String name;
	private int phoneNo;
	private String postAddress;
	private String roadAddress;
	private String jibunAddress;
	private String detailAddress;
	private String email;
	private String gender;
	private int aggreecode;
	private Date joinedAt;
}
