package com.mgstech.entities;

import java.sql.Timestamp;

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
public class JwtToken {

	private Long id;
	private String session_id;
	private String token;
	private Timestamp created_time;
	private Timestamp updated_time;
	
}
