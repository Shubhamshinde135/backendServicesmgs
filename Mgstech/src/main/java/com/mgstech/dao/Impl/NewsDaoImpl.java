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

import com.mgstech.dao.NewsDao;
import com.mgstech.entities.News;
import com.mgstech.util.SQLConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class NewsDaoImpl implements NewsDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Integer addNewNewsToDraftDao(String newsTitle, String newsTpye, String newsStatus, String newsMedia, Date currentDate) {

		try {
			byte[] image = null;
			if (!newsMedia.isEmpty()) {
				image = Base64.getDecoder().decode(newsMedia);
			}

			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_NEWS_WITH_DRAFTSTATUS_IN_DB,
					new Object[] { newsTitle, newsTpye, newsStatus, image, currentDate });

			log.debug(" addNewNewsToDraftDao : INSERT_NEW_JOB_VACANCY_IN_DB sql querry : "
					+ SQLConstants.INSERT_NEW_NEWS_WITH_DRAFTSTATUS_IN_DB);
			log.debug(" Is record isinserted to db or not count : " + count);

			return count;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" addNewNewsToDraftDao : Error occured while persisting code : " + e.getMessage());
			return 0;
		}	
	}

	@Override
	public Integer addNewNewsToPublishDao(String newsTitle, String newsTpye, String newsStatus, String newsMedia,
			Date currentDate, Date currentDate2) {
		
		try {
			byte[] image = null;
			if (!newsMedia.isEmpty()) {
				image = Base64.getDecoder().decode(newsMedia);
			}

			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_NEWS_WITH_PUBLISHED_STATUS_IN_DB,
					new Object[] { newsTitle, newsTpye, newsStatus, image, currentDate, currentDate2 });

			log.debug(" addNewNewsToPublishDao : INSERT_NEW_BLOG_WITH_PUBLISHED_STATUS_IN_DB sql querry : "
					+ SQLConstants.INSERT_NEW_NEWS_WITH_PUBLISHED_STATUS_IN_DB);
			log.debug("Is record isinserted to db or not count : " + count);

			return count;
		} catch (Exception e) {
			log.error(" addNewNewsToPublishDao : Error occured while persisting code : " + e.getMessage());
			return 0;
		}	
	}

	@Override
	public int isNewsIdIsExistOrNotDao(int newsId) {
		
		Integer count = jdbcTemplate.queryForObject(SQLConstants.IS_NEWS_IS_EXIST_WITH_ID_OR_NOT, 
				Integer.class,newsId);

		log.debug("isNewsIdIsExistOrNotDao : SQL Query : " + SQLConstants.IS_NEWS_IS_EXIST_WITH_ID_OR_NOT);
		log.debug("isNewsIdIsExistOrNotDao : Count : " + count);

		return count;
	}

	@Override
	public int deleteNewsByIdDao(int newsId) {
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(SQLConstants.DELETE_NEWS_BY_ID, newsId);

			log.debug("deleteNewsByIdDao : rowsAffected count --> " + rowsAffected);
			log.debug("deleteNewsByIdDao : sql querry : " + SQLConstants.DELETE_NEWS_BY_ID);
			return rowsAffected;
		} catch (Exception e) {
			log.error("deleteNewsByIdDao : SSHRException: " + e.getMessage(), e);
			return rowsAffected;
		}
	}

	@Override
	public List<News> getAvailableAllPublishedNewsDao() {

		List<News> resultList = new ArrayList<>();

		try {
			resultList = jdbcTemplate.query(SQLConstants.GET_ALL_AVAILABLE_NEWS_LIST,
					new BeanPropertyRowMapper<News>(News.class));

			for (int i = 0; i < resultList.size(); i++) {
				byte[] attachment = resultList.get(i).getNews_media();

				String base64 = Base64.getEncoder().encodeToString(attachment);
				resultList.get(i).setBase64Media(base64);
				resultList.get(i).setNews_media(null);
			}

			log.debug(" getAvailableAllPublishedNewsDao : resultList size : " + resultList.size());
			log.debug(" getAvailableAllPublishedNewsDao : resultList : " + resultList.toString());

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAvailableAllPublishedNewsDao : catch block exception : " + e.getMessage());
			return resultList;
		}

	}

	@Override
	public List<News> getAllNewsForAdminServiceDao() {
		
		List<News> resultList = new ArrayList<>();

		try {
			resultList = jdbcTemplate.query(SQLConstants.GET_ALL_NEWS_LIST,
					new BeanPropertyRowMapper<News>(News.class));
			
			for ( int i=0; i< resultList.size(); i++ ) {
				byte[] attachment = resultList.get(i).getNews_media();
				
				String base64 = Base64.getEncoder().encodeToString(attachment);
				resultList.get(i).setBase64Media(base64);
				resultList.get(i).setNews_media(null); 
			}

			log.debug(" getAllNewsForAdminServiceDao : resultList size : " + resultList.size());
			log.debug(" getAllNewsForAdminServiceDao : resultList : " + resultList.toString());

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAllNewsForAdminServiceDao : catch block exception : " + e.getMessage());
			return resultList;
		}
		
	}

	@Override
	public int isNewsIdIsExistOrNotWithDraftDao(int newsId) {
		
		Integer count = jdbcTemplate.queryForObject(
				SQLConstants.IS_NEWS_IS_EXIST_WITH_ID_AND_DRAFT_STATUS_OR_NOT, Integer.class,newsId);

		log.debug("isNewsIdIsExistOrNotWithDraftDao : SQL Query : " + 
					SQLConstants.IS_NEWS_IS_EXIST_WITH_ID_AND_DRAFT_STATUS_OR_NOT);
		
		log.debug("isNewsIdIsExistOrNotWithDraftDao : Count : " + count);

		return count;
	}

	@Override
	public int isNewsIsGettingPublishedDao(int newsId) {
		
		int rowsAffected = 0;
		try {
			
			LocalDate localDate = LocalDate.now();
			java.sql.Date currentDate = Date.valueOf(localDate);

			log.debug(" published date : " + currentDate);
			
			rowsAffected = jdbcTemplate.update(SQLConstants.PUBLISHED_DRAFTED_NEWS_BY_ID, currentDate, newsId);

			log.debug(" isNewsIsGettingPublishedDao : rowsAffected count --> " + rowsAffected);
			log.debug(" isNewsIsGettingPublishedDao : sql querry : " + SQLConstants.PUBLISHED_DRAFTED_NEWS_BY_ID);
			return rowsAffected;
		} catch (Exception e) {
			log.error("isNewsIsGettingPublishedDao : SSHRException: " + e.getMessage(), e);
			return rowsAffected;
		}
		
	}

	@Override
	public Integer isNewsIdIsExistOrNotWithPublishedStatus(int newsId) {
		
		Integer response = jdbcTemplate.queryForObject(
				SQLConstants.IS_NEWS_ID_IS_EXIST_WITH_PUBLISHED_STATUS, Integer.class, newsId, "PUBLISHED");
		
		return response;
		
	}

	@Override
	public News getNewsDetailsByNewsIdDao(int newsId) {
		
		News result = null;

	    try {
	        result = jdbcTemplate.queryForObject(SQLConstants.GET_NEWS_DETAILS_BY_NEWS_ID,
	                new BeanPropertyRowMapper<>(News.class), newsId);
	        
	        byte[] attachment = result.getNews_media();

			String base64 = Base64.getEncoder().encodeToString(attachment);
			result.setBase64Media(base64);
			result.setNews_media(null);
	        
	        log.debug("getNewsDetailsByNewsIdDao: result: " + result);
	        
	    } catch (EmptyResultDataAccessException e) {
	    	e.printStackTrace();
	        log.warn("No News found for newsId : " + newsId);
	    } catch (DataAccessException e) {
	    	e.printStackTrace();
	        log.error("Database access error in getNewsDetailsByNewsIdDao : " + e.getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace();
	        log.error("Unexpected exception in getNewsDetailsByNewsIdDao : " + e.getMessage(), e);
	    }

		return result;
		
		
	}
	
	
}
