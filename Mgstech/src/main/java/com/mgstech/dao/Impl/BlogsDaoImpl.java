package com.mgstech.dao.Impl;

import java.sql.Date;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mgstech.dao.BlogsDao;
import com.mgstech.entities.Blogs;
import com.mgstech.util.SQLConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BlogsDaoImpl implements BlogsDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Integer addNewBlogToDraftDao(String blogTitle, String blogBy, String blogDescription, String blogStatus,
			String blogImage, Date currentDate) {

		try {
			byte[] image = null;
			if (!blogImage.isEmpty()) {
				image = Base64.getDecoder().decode(blogImage);
			}

			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_BLOG_WITH_DRAFTSTATUS_IN_DB,
					new Object[] { blogTitle, blogBy, blogDescription, blogStatus, image, currentDate });

			log.debug(" addNewBlogToDraftDao : INSERT_NEW_JOB_VACANCY_IN_DB sql querry : "
					+ SQLConstants.INSERT_NEW_BLOG_WITH_DRAFTSTATUS_IN_DB);
			log.debug(" Is record isinserted to db or not count : " + count);

			return count;
		} catch (Exception e) {
			log.error(" addNewBlogToDraftDao : Error occured while persisting code : " + e.getMessage());
			return 0;
		}

	}

	@Override
	public Integer addNewBlogToPublishedDao(String blogTitle, String blogBy, String blogDescription, String blogStatus,
			String blogImage, Date currentDate, Date currentDate2) {

		try {
			byte[] image = null;
			if (!blogImage.isEmpty()) {
				image = Base64.getDecoder().decode(blogImage);
			}

			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_BLOG_WITH_PUBLISHED_STATUS_IN_DB,
					new Object[] { blogTitle, blogBy, blogDescription, blogStatus, image, currentDate, currentDate2 });

			log.debug(" addNewBlogToPublishedDao : INSERT_NEW_BLOG_WITH_PUBLISHED_STATUS_IN_DB sql querry : "
					+ SQLConstants.INSERT_NEW_BLOG_WITH_PUBLISHED_STATUS_IN_DB);
			log.debug("Is record isinserted to db or not count : " + count);

			return count;
		} catch (Exception e) {
			log.error(" addNewBlogToPublishedDao : Error occured while persisting code : " + e.getMessage());
			return 0;
		}

	}

	@Override
	public int isBlogIdIsExistOrNot(int blogId) {

		Integer count = jdbcTemplate.queryForObject(SQLConstants.IS_BLOG_IS_EXIST_WITH_ID_OR_NOT, Integer.class,
				blogId);

		log.debug("isBlogIdIsExistOrNot : SQL Query : " + SQLConstants.IS_BLOG_IS_EXIST_WITH_ID_OR_NOT);
		log.debug("isBlogIdIsExistOrNot : Count : " + count);

		return count;
	}

	@Override
	public int deleteBlogByIdDao(int blogId) {
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(SQLConstants.DELETE_BLOG_BY_ID, blogId);

			log.debug("deleteBlogByIdDao : rowsAffected count --> " + rowsAffected);
			log.debug(" deleteBlogByIdDao : sql querry : " + SQLConstants.DELETE_BLOG_BY_ID);
			return rowsAffected;
		} catch (Exception e) {
			log.error("deleteBlogByIdDao : SSHRException: " + e.getMessage(), e);
			return rowsAffected;
		}

	}

	@Override
	public List<Blogs> getAvailableAllActiveBlogssDao() {

		List<Blogs> resultList = new ArrayList<>();

		try {
			resultList = jdbcTemplate.query(SQLConstants.GET_ALL_AVAILABLE_BLOGS_LIST,
					new BeanPropertyRowMapper<Blogs>(Blogs.class));
			
			for ( int i=0; i< resultList.size(); i++ ) {
				byte[] attachment = resultList.get(i).getBlog_image();
				
				String base64 = Base64.getEncoder().encodeToString(attachment);
				resultList.get(i).setBase64Image(base64);
				resultList.get(i).setBlog_image(null);
			}

			log.debug(" getAvailableAllActiveBlogssDao : resultList size : " + resultList.size());
			log.debug(" getAvailableAllActiveBlogssDao : resultList : " + resultList.toString());

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAvailableAllActiveBlogssDao : catch block exception : " + e.getMessage());
			return resultList;
		}

	}

	@Override
	public List<Blogs> getAllBlogsForAdminDao() {

		List<Blogs> resultList = new ArrayList<>();

		try {
			resultList = jdbcTemplate.query(SQLConstants.GET_ALL_BLOGS_LIST,
					new BeanPropertyRowMapper<Blogs>(Blogs.class));
			
			for ( int i=0; i< resultList.size(); i++ ) {
				byte[] attachment = resultList.get(i).getBlog_image();
				
				String base64 = Base64.getEncoder().encodeToString(attachment);
				resultList.get(i).setBase64Image(base64);
				resultList.get(i).setBlog_image(null);
			}

			log.debug(" getAllBlogsForAdminDao : resultList size : " + resultList.size());
			log.debug(" getAllBlogsForAdminDao : resultList : " + resultList.toString());

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAllBlogsForAdminDao : catch block exception : " + e.getMessage());
			return resultList;
		}

	}

	@Override
	public int isBlogIdIsExistWithDraftStatus(int blogId) {
				
		Integer count = jdbcTemplate.queryForObject( SQLConstants.IS_BLOG_IS_EXIST_WITH_DRAFT_STATUS_OR_NOT, 
				Integer.class, blogId );

		log.debug("isBlogIdIsExistWithDraftStatus : SQL Query : " + SQLConstants.IS_BLOG_IS_EXIST_WITH_DRAFT_STATUS_OR_NOT);
		log.debug("isBlogIdIsExistWithDraftStatus : Count : " + count);

		return count;		
	}

	@Override
	public int isBlogIsGettingPublished(int blogId) {
		
		int rowsAffected = 0;
		try {
			
			LocalDate localDate = LocalDate.now();
			java.sql.Date currentDate = Date.valueOf(localDate);

			log.debug(" published date : " + currentDate);
			
			rowsAffected = jdbcTemplate.update(SQLConstants.PUBLISHED_DRAFTED_BLOG_BY_BLOG_ID, currentDate, blogId);

			log.debug(" isBlogIsGettingPublished : rowsAffected count --> " + rowsAffected);
			log.debug(" isBlogIsGettingPublished : sql querry : " + SQLConstants.PUBLISHED_DRAFTED_BLOG_BY_BLOG_ID);
			return rowsAffected;
		} catch (Exception e) {
			log.error("isBlogIsGettingPublished : SSHRException: " + e.getMessage(), e);
			return rowsAffected;
		}		
	}

	@Override
	public Integer isBlogIdIsExistOrNotWithActiveStatus(int blogId) {
		
		Integer response = jdbcTemplate.queryForObject(
				SQLConstants.IS_BLOG_ID_IS_EXIST_WITH_PUBLISHED_STATUS, Integer.class, blogId, "PUBLISHED");
		
		return response;
	}

	@Override
	public Blogs getBlogDetailsByBlogIdDao(int blogId) {
		Blogs result = null;

	    try {
	        result = jdbcTemplate.queryForObject(SQLConstants.GET_BLOG_DETAILS_BY_BLOG_ID,
	                new BeanPropertyRowMapper<>(Blogs.class), blogId);
	        
	        byte[] attachment = result.getBlog_image();

			String base64 = Base64.getEncoder().encodeToString(attachment);
			result.setBase64Image(base64);
			result.setBlog_image(null);
	        
	        log.debug("getBlogDetailsByBlogIdDao: result: " + result);
	        
	    } catch (EmptyResultDataAccessException e) {
	    	e.printStackTrace();
	        log.warn("No Blog found for blogId : " + blogId);
	    } catch (DataAccessException e) {
	    	e.printStackTrace();
	        log.error("Database access error in getBlogDetailsByBlogIdDao : " + e.getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace();
	        log.error("Unexpected exception in getBlogDetailsByBlogIdDao : " + e.getMessage(), e);
	    }

		return result;
	}

}
