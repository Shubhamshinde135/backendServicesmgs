package com.mgstech.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgstech.service.NewsService;
import com.mgstech.util.ServiceResponseLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class NewsController {

	@Autowired
	ServiceResponseLogger myServiceLogger;
	
	@Autowired
	NewsService newsService;
	
	@RequestMapping(value = "/addNewNewsToDraft", method = RequestMethod.POST)
	public ResponseEntity<?> addNewNewsToDraft(
	    @RequestParam String newsTitle, 
	    @RequestParam String newsType, 
	    @RequestParam String newsMedia) {

	    HashMap<String, Object> response;
	    try {
	        log.debug("newsType :  " + newsType);
	        log.debug("newsTitle : " + newsTitle);
	        
	        response = newsService.addNewNewsToDraftService(newsTitle, newsType, newsMedia);

	        myServiceLogger.logResponse(response);
	        return ResponseEntity.ok(response);

	    } catch (Exception e) {
	        log.error(e.getLocalizedMessage(), e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
	    }
	}


	@RequestMapping(value = "/addNewNewsToPublish", method = RequestMethod.POST)
	public ResponseEntity<?> addNewNewsToPublish(
		    @RequestParam String newsTitle, 
		    @RequestParam String newsType, 
		    @RequestParam String newsMedia) {

		HashMap<String, Object> response = null;
		try {

			response = newsService.addNewNewsToPublishService( newsTitle, newsType, newsMedia );

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteNewsById", method = RequestMethod.POST)
	public ResponseEntity<?> deleteNewsById(int newsId) {

		HashMap<String, Object> response = null;
		try {

			response = newsService.deleteNewsByIdService( newsId );

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getAvailableAllPublishedNews", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableAllPublishedNews() {
		
		HashMap<String, Object> response = null;
		try {

			response = newsService.getAvailableAllPublishedNewsService();
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getAllNewsForAdmin", method = RequestMethod.GET)
	public ResponseEntity<?> getAllNewsForAdmin() {
		
		HashMap<String, Object> response = null;
		try {

			response = newsService.getAllNewsForAdminService();
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	@RequestMapping(value = "/publishedPendingNews", method = RequestMethod.POST)
	public ResponseEntity<?> publishedPendingNews(int newsId) {

		HashMap<String, Object> response = null;
		try {

			response = newsService.publishedPendingNewsService(newsId);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getNewsyDetailsByNewsId", method = RequestMethod.POST)
	public ResponseEntity<?> getNewsyDetailsByNewsId(int newsId) {
		
		HashMap<String, Object> response = null;
		try {

			response = newsService.getNewsyDetailsByNewsIdService(newsId);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
