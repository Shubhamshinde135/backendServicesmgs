package com.mgstech.entities;

import java.sql.Date;

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
public class CaseStudy {
	
	private Long case_id;
	private String case_title;
	private String department;
	private String case_description;
	private String status;
	private byte[] case_image;
	private Date submission_date;
	private Date published_date;
	private String base64Image;

}
