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
public class JobPostPositionRecords {

	private Long job_id;	
	private String department;
	private String job_type;
	private String position_type;
	private String location;
	private String job_title;
	private String experience;
	private String job_description;
	private String job_status;
	private Date created_date;
	private Date updated_date;
	private Integer applicant_count;
	private int max_rownum;

}
