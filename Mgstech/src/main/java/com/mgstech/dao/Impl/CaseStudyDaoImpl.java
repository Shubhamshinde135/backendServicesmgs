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

import com.mgstech.dao.CaseStudyDao;
import com.mgstech.entities.CaseStudy;
import com.mgstech.util.SQLConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CaseStudyDaoImpl implements CaseStudyDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Integer addNewCaseStudyToDraftDao(String caseStudyTitle, String department, String caseStudyDescription,
			String caseStudyStatus, String caseStudyImage, Date currentDate) {
		
		try {
			byte[] image = null;
			if (!caseStudyImage.isEmpty()) {
				image = Base64.getDecoder().decode(caseStudyImage);
			}

			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_CASE_STUDY_WITH_DRAFTSTATUS_IN_DB,
					new Object[] { caseStudyTitle, department, caseStudyDescription, caseStudyStatus, image, currentDate });

			log.debug(" addNewCaseStudyToDraftDao : INSERT_NEW_JOB_VACANCY_IN_DB sql querry : "
					+ SQLConstants.INSERT_NEW_CASE_STUDY_WITH_DRAFTSTATUS_IN_DB);
			log.debug(" Is record isinserted to db or not count : " + count);

			return count;
		} catch (Exception e) {
			log.error(" addNewCaseStudyToDraftDao : Error occured while persisting code : " + e.getMessage());
			return 0;
		}		
	}

	@Override
	public Integer addNewCaseStudyToPublishDao(String caseStudyTitle, String department, String caseStudyDescription,
			String caseStudyStatus, String caseStudyImage, Date currentDate, Date currentDate2) {
		
		try {
			byte[] image = null;
			if (!caseStudyImage.isEmpty()) {
				image = Base64.getDecoder().decode(caseStudyImage);
			}

			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_CASESTUDY_WITH_PUBLISHED_STATUS_IN_DB,
					new Object[] { caseStudyTitle, department, caseStudyDescription, caseStudyStatus, image, currentDate, currentDate2 });

			log.debug(" addNewCaseStudyToPublishDao : INSERT_NEW_BLOG_WITH_PUBLISHED_STATUS_IN_DB sql querry : "
					+ SQLConstants.INSERT_NEW_CASESTUDY_WITH_PUBLISHED_STATUS_IN_DB);
			log.debug("Is record isinserted to db or not count : " + count);

			return count;
		} catch (Exception e) {
			log.error(" addNewCaseStudyToPublishDao : Error occured while persisting code : " + e.getMessage());
			return 0;
		}
		
	}

	@Override
	public int isCaseStudyIdIsExistOrNot(int caseId) {
		
		Integer count = jdbcTemplate.queryForObject(SQLConstants.IS_CASE_STUDY_IS_EXIST_WITH_ID_OR_NOT, Integer.class,
				caseId);

		log.debug("isCaseStudyIdIsExistOrNot : SQL Query : " + SQLConstants.IS_CASE_STUDY_IS_EXIST_WITH_ID_OR_NOT);
		log.debug("isCaseStudyIdIsExistOrNot : Count : " + count);

		return count;
		
	}

	@Override
	public int deleteCaseStudyByIdDao(int caseId) {
		
		int rowsAffected = 0;
		try {
			rowsAffected = jdbcTemplate.update(SQLConstants.DELETE_CASE_STUDY_BY_ID, caseId);

			log.debug("deleteCaseStudyByIdDao : rowsAffected count --> " + rowsAffected);
			log.debug("deleteCaseStudyByIdDao : sql querry : " + SQLConstants.DELETE_CASE_STUDY_BY_ID);
			return rowsAffected;
		} catch (Exception e) {
			log.error("deleteCaseStudyByIdDao : SSHRException: " + e.getMessage(), e);
			return rowsAffected;
		}
		
	}

	@Override
	public List<CaseStudy> getAvailableAllPublishedCaseStudyDao() {
		
		List<CaseStudy> resultList = new ArrayList<>();

		try {
			resultList = jdbcTemplate.query(SQLConstants.GET_ALL_AVAILABLE_CASE_STUDY_LIST,
					new BeanPropertyRowMapper<CaseStudy>(CaseStudy.class));
			
			for ( int i=0; i< resultList.size(); i++ ) {
				byte[] attachment = resultList.get(i).getCase_image();
				
				String base64 = Base64.getEncoder().encodeToString(attachment);
				resultList.get(i).setBase64Image(base64);
				resultList.get(i).setCase_image(null); 
			}

			log.debug(" getAvailableAllPublishedCaseStudyDao : resultList size : " + resultList.size());
			log.debug(" getAvailableAllPublishedCaseStudyDao : resultList : " + resultList.toString());

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAvailableAllPublishedCaseStudyDao : catch block exception : " + e.getMessage());
			return resultList;
		}
		
	}

	@Override
	public List<CaseStudy> getAllCaseStudyForAdminDao() {
		
		List<CaseStudy> resultList = new ArrayList<>();

		try {
			resultList = jdbcTemplate.query(SQLConstants.GET_ALL_CASE_STUDY_LIST,
					new BeanPropertyRowMapper<CaseStudy>(CaseStudy.class));
			
			for ( int i=0; i< resultList.size(); i++ ) {
				byte[] attachment = resultList.get(i).getCase_image();
				
				String base64 = Base64.getEncoder().encodeToString(attachment);
				resultList.get(i).setBase64Image(base64);
				resultList.get(i).setCase_image(null); 
			}

			log.debug(" getAllCaseStudyForAdminDao : resultList size : " + resultList.size());
			log.debug(" getAllCaseStudyForAdminDao : resultList : " + resultList.toString());

			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAllCaseStudyForAdminDao : catch block exception : " + e.getMessage());
			return resultList;
		}
		
	}

	@Override
	public int isCaseIdIsExistWithDraftStatus(int caseId) {

		Integer count = jdbcTemplate.queryForObject( SQLConstants.IS_CASESTUDY_IS_EXIST_WITH_DRAFT_STATUS_OR_NOT, 
				Integer.class, caseId );

		log.debug("isCaseIdIsExistWithDraftStatus : SQL Query : " + SQLConstants.IS_CASESTUDY_IS_EXIST_WITH_DRAFT_STATUS_OR_NOT);
		log.debug("isCaseIdIsExistWithDraftStatus : Count : " + count);

		return count;
		
	}

	@Override
	public int isCaseStudyIsGettingPublished(int caseId) {
		
		int rowsAffected = 0;
		try {
			
			LocalDate localDate = LocalDate.now();
			java.sql.Date currentDate = Date.valueOf(localDate);

			log.debug(" published date : " + currentDate);
			
			rowsAffected = jdbcTemplate.update(SQLConstants.PUBLISHED_DRAFTED_CASE_STUDY_BY_CASE_ID, currentDate, caseId);

			log.debug(" isCaseStudyIsGettingPublished : rowsAffected count --> " + rowsAffected);
			log.debug(" isCaseStudyIsGettingPublished : sql querry : " + SQLConstants.PUBLISHED_DRAFTED_CASE_STUDY_BY_CASE_ID);
			return rowsAffected;
		} catch (Exception e) {
			log.error("isCaseStudyIsGettingPublished : SSHRException: " + e.getMessage(), e);
			return rowsAffected;
		}
		
	}

	@Override
	public Integer isCaseIdIsExistOrNotWithPublishedStatus(int caseId) {
		
		Integer response = jdbcTemplate.queryForObject(
				SQLConstants.IS_CASE_ID_IS_EXIST_WITH_PUBLISHED_STATUS, Integer.class, caseId, "PUBLISHED");
		
		return response;
		
	}

	@Override
	public CaseStudy getCaseStudyDetailsByCaseIdDao(int caseId) {
		
		CaseStudy result = null;

	    try {
	        result = jdbcTemplate.queryForObject(SQLConstants.GET_CASE_STUDY_DETAILS_BY_CASE_ID,
	                new BeanPropertyRowMapper<>(CaseStudy.class), caseId);
	        
	        byte[] attachment = result.getCase_image();

			String base64 = Base64.getEncoder().encodeToString(attachment);
			result.setBase64Image(base64);
			result.setCase_image(null);
	        
	        log.debug("getBlogDetailsByBlogIdDao: result: " + result);
	        
	    } catch (EmptyResultDataAccessException e) {
	    	e.printStackTrace();
	        log.warn("No Case Study found for caseId : " + caseId);
	    } catch (DataAccessException e) {
	    	e.printStackTrace();
	        log.error("Database access error in getCaseStudyDetailsByCaseIdDao : " + e.getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace();
	        log.error("Unexpected exception in getCaseStudyDetailsByCaseIdDao : " + e.getMessage(), e);
	    }

		return result;
		
	}
	
}
