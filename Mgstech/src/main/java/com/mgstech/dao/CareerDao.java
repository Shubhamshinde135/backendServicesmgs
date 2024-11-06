package com.mgstech.dao;

import java.sql.Date;
import java.util.List;

import com.mgstech.entities.NewJobPost;

public interface CareerDao {

	Integer addNewJobPost(String department, String jobType, String positionType, String location, String jobTitle,
			String experience, String jobDescription, String jobStatus, Date currentDate);

	List<NewJobPost> getAvailableAllActiveJobsDao();

	int updateJobStatusByIdDao(int job_id);

	int isJobIdExistWithActiveStatus(int job_id);

	Integer addNewJobApplicant(int jobId, String applicantName, String emailId, String contactNumber, String totalExperience, String about,
			Date currentDate, String noticePeriod, String expectedCtc, String actualCtc, String readyForOnsite);

	NewJobPost getJobPostDetailsByJobId(int jobId);

	int isJobApplicationAlreadyExistOrNot(int jobId, String emailId, String contactNumber);

	NewJobPost getJobDetailsByJobIdsDao(int jobId);

	List<NewJobPost> sortAllAvailableJobsByInputService(String mod, String value);


}
