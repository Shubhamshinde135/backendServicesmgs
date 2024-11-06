package com.mgstech.service;

import java.util.HashMap;

public interface CareerService {

	HashMap<String, Object> addNewCareerPost(String department, String jobType, String positionType, String location,
			String jobTitle, String experience, String jobDescription);

	HashMap<String, Object> getAvailableAllActiveJobsService();

	HashMap<String, Object> changeJobStatusByJobIdService(int job_id);

	HashMap<String, Object> submitJobApplicationService(String applicantName,  int jobId, String emailId,
			String contactNumber, String totalExperience, String about, String attachment, String attachmentType, 
			String noticePeriod, String expectedCtc, String actualCtc, String readyForOnsite);

	HashMap<String, Object> getJobDetailsByJobIdService(int jobId);

	HashMap<String, Object> sortAllAvailableJobsByInputService(String mod, String value);

}
