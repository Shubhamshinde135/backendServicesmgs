package com.mgstech.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ResponseBean {

	private String status = "";
	private String errorMessage = "" ;
	private int errorCode = 200 ;

}
