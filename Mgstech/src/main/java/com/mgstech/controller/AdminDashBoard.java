package com.mgstech.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mgstech.service.AdminDashBoardService;
import com.mgstech.util.ServiceResponseLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AdminDashBoard {

	@Autowired
	ServiceResponseLogger myServiceLogger;

	@Autowired
	AdminDashBoardService adminDashBoardService;

	@RequestMapping(value = "/adminDashboard", method = RequestMethod.POST)
	public ResponseEntity<?> openJobPositionList(String orderBy, Integer pageSize, Integer offset, String ascdesc) {

		HashMap<String, Object> response = null;
		try {

			response = adminDashBoardService.openJobPositionList(orderBy, pageSize, offset, ascdesc);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> userAuthenticate(String user_name, String password) {

		HashMap<String, Object> response = null;

		try {

			response = adminDashBoardService.userAuthenticateService(user_name, password);

			myServiceLogger.logResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/contactUs", method = RequestMethod.POST)
	public ResponseEntity<?> contactUs(String firstName, String lastName, String emailId, String department,
			String message) {

		HashMap<String, Object> response = null;

		try {
			response = adminDashBoardService.contactUsThroghWebsite(firstName, lastName, emailId, department, message);

			myServiceLogger.logResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.ok(response);
	}
	
	@RequestMapping(value = "/getAppVersion", method = RequestMethod.POST)
	public ResponseEntity<?> getAppVersion(String sessionId) {

		HashMap<String, Object> response = null;

		try {
			response = adminDashBoardService.getAppVersionBySession(sessionId);

			myServiceLogger.logResponse(response);

			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
