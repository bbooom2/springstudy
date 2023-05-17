package com.gdu.semi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdDTO {
	
	private int prodCode;
	private String prodName;
	private String prodPath;
	private String prodImgName;
	private int prodOriginPrice;
	private int prodStock;
	

}
