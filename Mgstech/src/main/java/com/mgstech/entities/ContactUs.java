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
public class ContactUs {

	private Long contact_id;
	private String firstname;
	private String lastname;
	private String emailid;
	private String department;
	private String message;
	private Timestamp submissiondate;	
	
}
