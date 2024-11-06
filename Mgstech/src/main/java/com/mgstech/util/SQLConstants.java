package com.mgstech.util;

public interface SQLConstants {

	public static final String INSERT_NEW_JOB_VACANCY_IN_DB = "INSERT INTO new_job_post ( department, job_type, position_type, location, job_title, experience, job_description, job_status, created_date ) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? );";
	public static final String GET_ALL_AVAILABLE_ACTIVE_JOBS_LIST = " SELECT * FROM new_job_post WHERE job_status = 'ACTIVE' ORDER BY job_id DESC ";
	public static final String GET_ACTIVE_POSTION_COUNT_ACCORDING_TO_DEPARTMENT = "SELECT COUNT(*) FROM new_job_post WHERE department LIKE ? AND job_status = ? ";
	public static final String IS_USERNAME_IS_EXIST_OR_NOT = " SELECT count(*) FROM admin_authentication WHERE BINARY user_name = ? ";
	public static final String IS_USERNAME_PASSWORD_IS_VALID_OR_NOT = " SELECT count(*) FROM admin_authentication WHERE BINARY user_name = ? AND BINARY password = ? ";
	public static final String UPDAT_JOB_STATUS_BY_JOB_ID = " UPDATE new_job_post set job_status = 'INACTIVE' , updated_date = ? WHERE job_id = ? ";
	public static final String IS_JOB_ID_IS_EXIST_WITH_ACTIVE_STATUS = " SELECT COUNT(*) FROM mgstech.new_job_post WHERE job_id = ? AND job_status = ? ";
	public static final String INSERT_NEW_JOB_APPLICATIONS_IN_DB = "INSERT INTO job_applicants (job_id, applicants_name, email_id, contact_number, total_experience, about_information, submission_date, notice_period, expected_ctc, actual_ctc, ready_foronsite ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	public static final String GET_JOB_POST_DETAILS_BY_JOB_ID = " SELECT * FROM new_job_post WHERE job_id = ? ";
	public static final String IS_JOB_APPLICATION_IS_ALREADY_EXIST_FOR_GIVEN_JOB_ID = " SELECT count(*) FROM job_applicants WHERE job_id = ? AND email_id = ? ";
	public static final String INSERT_CONTACT_US_IN_DB = " INSERT INTO contact_us ( firstname, lastname, emailid, department, message, submissiondate ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
	public static final String GET_JOB_DETAILS_BY_JOB_ID = "SELECT * FROM new_job_post WHERE job_id = ? ";
	public static final String INSERT_NEW_BLOG_WITH_DRAFTSTATUS_IN_DB = "INSERT INTO blogs ( blog_title, blog_by, blog_description, status,  blog_image, submission_date ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
	public static final String INSERT_NEW_BLOG_WITH_PUBLISHED_STATUS_IN_DB = "INSERT INTO blogs ( blog_title, blog_by, blog_description, status,  blog_image, submission_date, published_date ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
	public static final String IS_BLOG_IS_EXIST_WITH_ID_OR_NOT = " SELECT COUNT(*) FROM blogs WHERE blog_id = ? ";
	public static final String DELETE_BLOG_BY_ID = " DELETE FROM blogs WHERE blog_id = ? ";
	public static final String GET_ALL_AVAILABLE_BLOGS_LIST = " SELECT * FROM blogs WHERE status = 'PUBLISHED' ORDER BY blog_id DESC   ";
	public static final String GET_ALL_BLOGS_LIST = " SELECT * FROM blogs ORDER BY blog_id DESC ";
	public static final String IS_BLOG_IS_EXIST_WITH_DRAFT_STATUS_OR_NOT = " SELECT COUNT(*) FROM blogs WHERE blog_id = ? AND status = 'DRAFT' ";
	public static final String PUBLISHED_DRAFTED_BLOG_BY_BLOG_ID = " UPDATE blogs SET status = 'PUBLISHED', published_date = ? WHERE blog_id = ? ";
	public static final String INSERT_NEW_CASE_STUDY_WITH_DRAFTSTATUS_IN_DB = " INSERT INTO case_study ( case_title, department, case_description, status,  case_image, submission_date ) VALUES ( ?, ?, ?, ?, ?, ? ) ";
	public static final String INSERT_NEW_CASESTUDY_WITH_PUBLISHED_STATUS_IN_DB = " INSERT INTO case_study ( case_title, department, case_description, status,  case_image, submission_date, published_date ) VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
	public static final String IS_CASE_STUDY_IS_EXIST_WITH_ID_OR_NOT = " SELECT COUNT(*) FROM case_study WHERE case_id = ? ";
	public static final String DELETE_CASE_STUDY_BY_ID = " DELETE FROM case_study WHERE case_id = ? ";
	public static final String GET_ALL_AVAILABLE_CASE_STUDY_LIST = " SELECT * FROM case_study WHERE status = 'PUBLISHED' ORDER BY case_id DESC ";
	public static final String GET_ALL_CASE_STUDY_LIST = " SELECT * FROM case_study ORDER BY case_id DESC ";
	public static final String IS_CASESTUDY_IS_EXIST_WITH_DRAFT_STATUS_OR_NOT = " SELECT COUNT(*) FROM case_study WHERE case_id = ? AND status = 'DRAFT' ";
	public static final String PUBLISHED_DRAFTED_CASE_STUDY_BY_CASE_ID = " UPDATE case_study SET status = 'PUBLISHED', published_date = ? WHERE case_id = ?  ";
	public static final String INSERT_NEW_NEWS_WITH_DRAFTSTATUS_IN_DB = " INSERT INTO news ( news_title, news_type, status, news_media, submission_date) VALUES ( ?, ?, ?, ?, ? ) ";
	public static final String INSERT_NEW_NEWS_WITH_PUBLISHED_STATUS_IN_DB = " INSERT INTO news ( news_title, news_type, status, news_media, submission_date, published_date) VALUES ( ?, ?, ?, ?, ?, ? ) ";
	public static final String IS_NEWS_IS_EXIST_WITH_ID_OR_NOT = " SELECT COUNT(*) FROM news WHERE news_id = ? ";
	public static final String DELETE_NEWS_BY_ID = " DELETE FROM news WHERE  news_id = ? ";
	public static final String GET_ALL_AVAILABLE_NEWS_LIST = " SELECT * FROM news WHERE status = 'PUBLISHED' ORDER BY news_id DESC ";
	public static final String GET_ALL_NEWS_LIST = " SELECT * FROM news ORDER BY news_id DESC ";
	public static final String IS_NEWS_IS_EXIST_WITH_ID_AND_DRAFT_STATUS_OR_NOT = " SELECT COUNT(*) FROM news WHERE news_id = ? AND status = 'DRAFT' ";
	public static final String PUBLISHED_DRAFTED_NEWS_BY_ID = " UPDATE news SET status = 'PUBLISHED', published_date = ? WHERE news_id = ? ";
	public static final String INSERT_TOKEN_DETAILS_IN_DB = " insert into jwt_token ( session_id, token, created_time, updated_time ) VALUES ( ?, ?, ?, ? ) ";
	public static final String UPDATE_JWT_TOKEN_FOR_TIME_DETAILS = " update jwt_token set updated_time = ?  WHERE token = ? ";
	public static final String GET_TOKEN_DETAILS_BY_SESSION_ID_AND_TOKEN = " SELECT count(*) from jwt_token where session_id = ? and  token = ? ";
	public static final String GET_TOKEN_DETAILS_BY_TOKEN = " select * from jwt_token where token = ? ";
	public static final String GET_ALL_ACTIVE_JOB_POST_COUNT = " SELECT COUNT(*) FROM new_job_post WHERE job_status = 'ACTIVE'";
	public static final String GET_JOB_POST_DETAIL_BY_JOB_ID = "SELECT job_id, department, job_type, position_type, location, job_title, experience, job_description, job_status, created_date, updated_date FROM new_job_post WHERE job_id = ?";
	public static final String IS_BLOG_ID_IS_EXIST_WITH_PUBLISHED_STATUS = " SELECT COUNT(*) FROM blogs WHERE blog_id = ? AND status = ? ";
	public static final String GET_BLOG_DETAILS_BY_BLOG_ID = " SELECT * FROM blogs WHERE blog_id = ? ";
	public static final String IS_CASE_ID_IS_EXIST_WITH_PUBLISHED_STATUS = " SELECT COUNT(*) FROM case_study WHERE case_id = ? AND status = ? ";
	public static final String GET_CASE_STUDY_DETAILS_BY_CASE_ID = " SELECT * FROM case_study WHERE case_id = ?  ";
	public static final String IS_NEWS_ID_IS_EXIST_WITH_PUBLISHED_STATUS = " SELECT COUNT(*) FROM news WHERE news_id = ? AND status = ?  ";
	public static final String GET_NEWS_DETAILS_BY_NEWS_ID = " SELECT * FROM news WHERE news_id = ? ";

}

