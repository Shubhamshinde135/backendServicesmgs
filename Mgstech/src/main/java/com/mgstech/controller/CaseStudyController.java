package com.mgstech.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mgstech.service.CaseStudyService;
import com.mgstech.util.ServiceResponseLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CaseStudyController {

	@Autowired
	ServiceResponseLogger myServiceLogger;
	
	@Autowired
	CaseStudyService caseStudyService;
		
	@RequestMapping(value = "/addNewCaseStudyToDraft", method = RequestMethod.POST)
	public ResponseEntity<?> addNewCaseStudyToDraft(String caseStudyTitle, String department, String caseStudyImage,
			String caseStudyDescription) {

		HashMap<String, Object> response = null;
		try {

			response = caseStudyService.addNewCaseStudyToDraft(caseStudyTitle, department, 
					caseStudyImage, caseStudyDescription);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/addNewCaseStudyToPublish", method = RequestMethod.POST)
	public ResponseEntity<?> addNewCaseStudyToPublish(String caseStudyTitle, String department, String caseStudyImage,
			String caseStudyDescription) {

		HashMap<String, Object> response = null;
		try {

			response = caseStudyService.addNewCaseStudyToPublish(caseStudyTitle, 
					department, caseStudyImage, caseStudyDescription);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteCaseStudyById", method = RequestMethod.POST)
	public ResponseEntity<?> deleteCaseStudyById(int caseId) {

		HashMap<String, Object> response = null;
		try {

			response = caseStudyService.deleteCaseStudyByIdService( caseId );

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getAvailableAllPublishedCaseStudy", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableAllPublishedCaseStudy() {
		
		HashMap<String, Object> response = null;
		try {

			response = caseStudyService.getAvailableAllPublishedCaseStudy();
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getAllCaseStudyForAdmin", method = RequestMethod.GET)
	public ResponseEntity<?> getAllCaseStudyForAdmin() {
		
		HashMap<String, Object> response = null;
		try {

			response = caseStudyService.getAllCaseStudyForAdminService();
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/publishedPendingCaseStudy", method = RequestMethod.POST)
	public ResponseEntity<?> publishedPendingCaseStudy(int caseId) {

		HashMap<String, Object> response = null;
		try {

			response = caseStudyService.publishedPendingCaseStudyService(caseId);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getCaseStudyDetailsByCaseId", method = RequestMethod.POST)
	public ResponseEntity<?> getCaseStudyDetailsByCaseId(int caseId) {
		
		HashMap<String, Object> response = null;
		try {

			response = caseStudyService.getCaseStudyDetailsByCaseIdService(caseId);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
