package com.mgstech.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mgstech.service.CareerService;
import com.mgstech.util.ServiceResponseLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CareerController {
	
	@Autowired
	ServiceResponseLogger myServiceLogger;
	
	@Autowired
	CareerService careerService;
	
	@RequestMapping(value = "/addNewJobPost", method = RequestMethod.POST)
	public ResponseEntity<?> dashboardDetails(String department, String jobType, String positionType, 
			String location, String jobTitle, String experience, String jobDescription) {
		
		HashMap<String, Object> response = null;
		try {

			response = careerService.addNewCareerPost(department, jobType, positionType, location, 
					jobTitle, experience, jobDescription);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/getAvailableAllActiveJobs", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableAllActiveJobs() {
		
		HashMap<String, Object> response = null;
		try {

			response = careerService.getAvailableAllActiveJobsService();
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/changeJobStatus", method = RequestMethod.POST)
	public ResponseEntity<?> changeJobStatusByJobId(int job_id) {
		
		HashMap<String, Object> response = null;
		try {

			response = careerService.changeJobStatusByJobIdService(job_id);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/submitJobApplication", method = RequestMethod.POST)
	public ResponseEntity<?> submitJobApplication(String applicantName, int jobId, String emailId, String contactNumber, 
			String totalExperience, String about, String attachment, String attachmentType, String noticePeriod,
			String expectedCtc, String actualCtc, String readyForOnsite) {
		
		HashMap<String, Object> response = null;
		try {

			response = careerService.submitJobApplicationService( applicantName, jobId, emailId, 
					contactNumber, totalExperience, about, attachment, attachmentType, noticePeriod,
					expectedCtc, actualCtc, readyForOnsite);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/getJobDetailsByJobId", method = RequestMethod.POST)
	public ResponseEntity<?> getJobDetailsByJobId(int jobId) {
		
		HashMap<String, Object> response = null;
		try {

			response = careerService.getJobDetailsByJobIdService(jobId);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/sortAllAvailableJobsByInput", method = RequestMethod.POST)
	public ResponseEntity<?> sortAllAvailableJobsByInput(String mod, String value) {
		
		HashMap<String, Object> response = null;
		try {

			response = careerService.sortAllAvailableJobsByInputService(mod, value);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
