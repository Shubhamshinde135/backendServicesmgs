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
public class NewJobPost {
	
	private Long jobId;
	private String department;
	private String jobType;
	private String positionType;
	private String location;
	private String jobTitle;
	private String experience;
	private String jobDescription;
	private String jobStatus;
	private Date createdDate;
	private Date updatedDate;
	

}
