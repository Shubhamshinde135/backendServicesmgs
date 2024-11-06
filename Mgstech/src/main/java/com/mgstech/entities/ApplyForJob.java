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
public class ApplyForJob {
	
	private Long applicantId;
	private Long jobId;
	private String applicantsName;
	private String emailId;
	private String contactNumber;
	private String totalExperience;
	private String aboutInformation;
	private Date submissionDate;
	private String notice_period;
	private String expected_ctc;
	private String actual_ctc;
	private String ready_foronsite;

	
}
