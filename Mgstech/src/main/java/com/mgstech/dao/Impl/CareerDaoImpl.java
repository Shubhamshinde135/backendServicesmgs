package com.mgstech.dao.Impl;

import java.sql.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mgstech.dao.CareerDao;
import com.mgstech.entities.NewJobPost;
import com.mgstech.util.SQLConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CareerDaoImpl implements CareerDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Integer addNewJobPost(String department, String jobType, String positionType, String location,
			String jobTitle, String experience, String jobDescription, String jobStatus, Date currentDate) {
		try {
			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_JOB_VACANCY_IN_DB,
					new Object[] { department, jobType, positionType, location, jobTitle, 
							experience, jobDescription, jobStatus, currentDate});
			
			log.debug("INSERT_NEW_JOB_VACANCY_IN_DB sql querry : " + SQLConstants.INSERT_NEW_JOB_VACANCY_IN_DB);
			log.debug("Is record isinserted to db or not count : " + count);
			
			return count;
		} catch (Exception e) {
			log.error(" addNewJobPost : Error occured while persisting code : " + e.getMessage());
			return 0;
		}
	}

	@Override
	public List<NewJobPost> getAvailableAllActiveJobsDao() {
		
		List<NewJobPost> resultList = new ArrayList<>();
		
		try {
			resultList = jdbcTemplate.query(SQLConstants.GET_ALL_AVAILABLE_ACTIVE_JOBS_LIST,
					new BeanPropertyRowMapper<NewJobPost>(NewJobPost.class));
			
			log.debug(" getAvailableAllActiveJobsDao : resultList size : "+ resultList.size());
			log.debug(" getAvailableAllActiveJobsDao : resultList : "+ resultList.toString());
			
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAvailableAllActiveJobsDao : catch block exception : "+e.getMessage());
			return resultList;
		}
		
	}

	@Override
	public int updateJobStatusByIdDao(int job_id) {
		try {
			java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
			log.debug(" : updateJobStatusByIdDao : date  --> " + date);
			
			int affectedRows = jdbcTemplate.update(SQLConstants.UPDAT_JOB_STATUS_BY_JOB_ID,  date, job_id) ;
			
			log.debug("UPDAT_JOB_STATUS_BY_JOB_ID sql querry : " + SQLConstants.UPDAT_JOB_STATUS_BY_JOB_ID);
			log.debug(" updateJobStatusByIdDao : affectedRows count : "+ affectedRows);
			return affectedRows;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("updateJobStatusByIdDao : catch block exception : "+e.getMessage());
			return 0;
		}
	}

	@Override
	public int isJobIdExistWithActiveStatus(int job_id) {
		
		Integer response = jdbcTemplate.queryForObject(
				SQLConstants.IS_JOB_ID_IS_EXIST_WITH_ACTIVE_STATUS, Integer.class, job_id, "ACTIVE");
		
		return response;
	}

	@Override
	public Integer addNewJobApplicant(int jobId, String applicantName, String emailId, String contactNumber, 
			String totalExperience, String about, Date currentDate, String noticePeriod, String expectedCtc, 
			String actualCtc, String readyForOnsite) {
		
		try {
			Integer count = jdbcTemplate.update(SQLConstants.INSERT_NEW_JOB_APPLICATIONS_IN_DB,
					new Object[] { jobId, applicantName, emailId, contactNumber, totalExperience, about, currentDate,
							noticePeriod, expectedCtc, actualCtc, readyForOnsite});
			
			log.debug("INSERT_NEW_JOB_VACANCY_IN_DB sql querry : " + SQLConstants.INSERT_NEW_JOB_APPLICATIONS_IN_DB);
			log.debug("Is record isinserted to db or not count : " + count);
			
			return count;
		} catch (Exception e) {
			log.error(" addNewJobPost : Error occured while persisting code : " + e.getMessage());
			return 0;
		}
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public NewJobPost getJobPostDetailsByJobId(int jobId) {
		NewJobPost resultList = null;

		try {
			
			resultList = jdbcTemplate.queryForObject(SQLConstants.GET_JOB_POST_DETAIL_BY_JOB_ID,
					new Object[] { jobId }, (rs, rowNum) -> new NewJobPost(
	                        rs.getLong("job_id"),
	                        rs.getString("department"),
	                        rs.getString("job_type"),
	                        rs.getString("position_type"),
	                        rs.getString("location"),
	                        rs.getString("job_title"),
	                        rs.getString("experience"),
	                        rs.getString("job_description"),
	                        rs.getString("job_status"),
	                        rs.getDate("created_date"),
	                        rs.getDate("created_date")
	                ));
			log.debug("getJobPostDetailsByJobId: Retrieved job post details: " + resultList);
		} catch (EmptyResultDataAccessException e) {
			log.warn("getJobPostDetailsByJobId: No job post found for jobId: " + jobId);
		} catch (DataAccessException e) {
			log.error("getJobPostDetailsByJobId: Database access error: " + e.getMessage(), e);
		} catch (Exception e) {
			log.error("getJobPostDetailsByJobId: Unexpected error: " + e.getMessage(), e);
		}

		return resultList;
	}

	@Override
	public int isJobApplicationAlreadyExistOrNot(int jobId, String emailId, String contactNumber) {	
		
		try {
			Integer count = jdbcTemplate.update(SQLConstants.IS_JOB_APPLICATION_IS_ALREADY_EXIST_FOR_GIVEN_JOB_ID,
					new Object[] { jobId,  emailId });
			
			log.debug("isJobApplicationAlreadyExistOrNot sql querry : " + SQLConstants.IS_JOB_APPLICATION_IS_ALREADY_EXIST_FOR_GIVEN_JOB_ID);
			log.debug("isJobApplicationAlreadyExistOrNot count : " + count);
			
			return count;
		} catch (Exception e) {
			log.error(" addNewJobPost : Error occured while persisting code : " + e.getMessage());
			return 0;
		}
		
	}

	@Override
	public NewJobPost getJobDetailsByJobIdsDao(int jobId) {
	    NewJobPost result = null;

	    try {
	        result = jdbcTemplate.queryForObject(SQLConstants.GET_JOB_DETAILS_BY_JOB_ID,
	                new BeanPropertyRowMapper<>(NewJobPost.class), jobId);
	        
	        log.debug("getJobDetailsByJobIdsDao: result: " + result);
	        
	    } catch (EmptyResultDataAccessException e) {
	    	e.printStackTrace();
	        log.warn("No job found for jobId: " + jobId);
	    } catch (DataAccessException e) {
	    	e.printStackTrace();
	        log.error("Database access error in getJobDetailsByJobIdsDao: " + e.getMessage());
	    } catch (Exception e) {
	    	e.printStackTrace();
	        log.error("Unexpected exception in getJobDetailsByJobIdsDao: " + e.getMessage(), e);
	    }

		return result;
	}

	@Override
	public List<NewJobPost> sortAllAvailableJobsByInputService(String mod, String value) {

		List<NewJobPost> resultList = new ArrayList<>();
		
		List<String> allowedColumns = Arrays.asList("department", "position_type", "location" );
		
	    if (!allowedColumns.contains(mod)) {
	        log.error("Invalid column for sorting/filtering: " + mod);
	        return resultList;
	    }

		try {
						
	        String sql = "SELECT * FROM new_job_post WHERE job_status = 'ACTIVE' AND " + mod + " = ? ORDER BY job_id DESC";

	        resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(NewJobPost.class), value);

	        log.debug("sortAllAvailableJobsByInputService: sql querry : " + sql);
	        log.debug("sortAllAvailableJobsByInputService: resultList : " + resultList.toString());

	        return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" getAvailableAllActiveJobsDao : catch block exception : " + e.getMessage());
			return resultList;
		}

	}

}
