package com.mgstech.dao.Impl;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mgstech.dao.AdminDashBoardDao;
import com.mgstech.entities.JobPostPositionRecords;
import com.mgstech.util.SQLConstants;
import com.mgstech.util.SSHRConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class AdminDashBoardDaoImpl implements AdminDashBoardDao {
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public int getActiveJobPosition(String technology) {
		
		try {			
			String jobStatus = SSHRConstants.ACTIVE;
			String departmentLike = "%" + technology + "%";

			log.debug(" getActiveJobPosition : department : "+ departmentLike);
			log.debug(" getActiveJobPosition : SQL Querry : "+ SQLConstants.GET_ACTIVE_POSTION_COUNT_ACCORDING_TO_DEPARTMENT);
			
			Integer response = jdbcTemplate.queryForObject(
					SQLConstants.GET_ACTIVE_POSTION_COUNT_ACCORDING_TO_DEPARTMENT, Integer.class, departmentLike, jobStatus);

			log.debug("responsefor result count : "+response);
			
			return response != null ? response : 0;
			
		} catch (Exception e) {
			log.error(" : getActiveJobPosition : error : "+ e.getMessage());
			return 0;
		}		
	}	
	
	@Override
	public List<JobPostPositionRecords> getOpenJobPositionListAccordingToPagination(String orderBy, Integer pageSize,
	        Integer offset, String ascdsc) {
	    
	    log.debug(" getOpenJobPositionListAccordingToPagination Inputs : orderBy  : " + orderBy +
	            " : pageSize : " + pageSize + " : offset : " + offset + " : ascdsc : " + ascdsc);
	    
	    List<JobPostPositionRecords> resultList = new ArrayList<>();

	    int totalRecordsCount = jdbcTemplate.queryForObject(SQLConstants.GET_ALL_ACTIVE_JOB_POST_COUNT, Integer.class);
	    int totalRecords = (int) Math.ceil((double) totalRecordsCount / pageSize);

	    String sql = "SELECT new_job_post.*, COUNT(job_applicants.applicant_id) AS applicant_count, "
	            + totalRecords + " AS max_rownum FROM new_job_post "
	            + "LEFT JOIN job_applicants ON new_job_post.job_id = job_applicants.job_id "
	            + "WHERE job_status = 'ACTIVE' GROUP BY new_job_post.job_id "
	            + "ORDER BY " + orderBy + " " + ascdsc + " LIMIT " + pageSize + " OFFSET " + offset;

	    resultList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(JobPostPositionRecords.class));
	    
	    log.debug(" : getOpenJobPositionListAccordingToPagination : sql query for above : " + sql);		
	    log.debug(" : getOpenJobPositionListAccordingToPagination : resultList : " + resultList.toString());
	    
	    return resultList;
	}

	@Override
	public Integer isUserNameExist(String user_name) {

		Integer count = jdbcTemplate.queryForObject(SQLConstants.IS_USERNAME_IS_EXIST_OR_NOT, Integer.class, user_name);

	    log.debug("isUserNameExist: SQL Query : " + SQLConstants.IS_USERNAME_IS_EXIST_OR_NOT);
	    log.debug("isUserNameExist: Count : "+ count);

	    return count;  	
	}

	@Override
	public Integer isPasswordValid(String user_name, String password) {
		
		Integer count = jdbcTemplate.queryForObject(SQLConstants.IS_USERNAME_PASSWORD_IS_VALID_OR_NOT, 
				Integer.class, user_name, password);
		
		log.debug(" : isPasswordValid : SQL_QUERRY : " + SQLConstants.IS_USERNAME_PASSWORD_IS_VALID_OR_NOT);
		log.debug(" : isPasswordValid : count : " + count);

		return count;
	}

	@Override
	public int persistContactUsRecords(String firstName, String lastName, String emailId, String department,
			String message) {

		try {

			Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

			Integer count = jdbcTemplate.update(SQLConstants.INSERT_CONTACT_US_IN_DB,
					new Object[] { firstName, lastName, emailId, department, message, currentTimestamp });

			log.debug("INSERT_CONTACT_US_IN_DB sql querry : " + SQLConstants.INSERT_CONTACT_US_IN_DB);
			log.debug("Is record is inserted to db or not count : " + count);

			return count;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(" persistContactUsRecords : Error occured while persisting code : " + e.getMessage());
			return 0;
		}

	}

	@Override
	public int getTotalRecorsCount() {
		
	    int totalRecordsCount = jdbcTemplate.queryForObject(
	    		SQLConstants.GET_ALL_ACTIVE_JOB_POST_COUNT, Integer.class);
	    
	    return totalRecordsCount;
	}

}
