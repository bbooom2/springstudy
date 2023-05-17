package com.gdu.app12.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccessDTO {

	private String id;
	private String lastLoginAt;

}
