package com.mgstech.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mgstech.service.BlogsService;
import com.mgstech.util.ServiceResponseLogger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BlogsController {
	
	@Autowired
	ServiceResponseLogger myServiceLogger;

	@Autowired
	BlogsService blogsService;

	@RequestMapping(value = "/addNewBlogToDraft", method = RequestMethod.POST)
	public ResponseEntity<?> addNewBlogToDraft(String blogTitle, String blogBy, String blogImage,
			String blogDescription) {

		HashMap<String, Object> response = null;
		try {

			response = blogsService.addNewBlogToDraft(blogTitle, blogBy, blogImage, blogDescription);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/addNewBlogToPublish", method = RequestMethod.POST)
	public ResponseEntity<?> addNewBlogToPublish(String blogTitle, String blogBy, String blogImage,
			String blogDescription) {

		HashMap<String, Object> response = null;
		try {

			response = blogsService.addNewBlogToPublish(blogTitle, blogBy, blogImage, blogDescription);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value = "/deleteBlogByBlogId", method = RequestMethod.POST)
	public ResponseEntity<?> deleteBlogByBlogId(int blogId) {

		HashMap<String, Object> response = null;
		try {

			response = blogsService.deleteBlogByBlogId(blogId);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getAvailableAllPublishedBlogs", method = RequestMethod.GET)
	public ResponseEntity<?> getAvailableAllActiveBlogs() {
		
		HashMap<String, Object> response = null;
		try {

			response = blogsService.getAvailableAllActiveBlogsService();
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/getAllBlogsForAdmin", method = RequestMethod.GET)
	public ResponseEntity<?> getAllBlogsForAdmin() {
		
		HashMap<String, Object> response = null;
		try {

			response = blogsService.getAllBlogsForAdminService();
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/publishedPendingBlog", method = RequestMethod.POST)
	public ResponseEntity<?> publishedPendingBlog(int blogId) {

		HashMap<String, Object> response = null;
		try {

			response = blogsService.publishedPendingBlogService(blogId);

			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}	
	
	@RequestMapping(value = "/getBlogDetailsByBlogId", method = RequestMethod.POST)
	public ResponseEntity<?> getBlogDetailsByBlogId(int blogId) {
		
		HashMap<String, Object> response = null;
		try {

			response = blogsService.getBlogDetailsByBlogIdService(blogId);
			
			myServiceLogger.logResponse(response);
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getLocalizedMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

}
