package com.mgstech.service.Impl;

import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.mgstech.dao.CareerDao;
import com.mgstech.entities.NewJobPost;
import com.mgstech.entities.ResponseBean;
import com.mgstech.messagingUtil.SendMailWithAttachment;
import com.mgstech.service.CareerService;
import com.mgstech.util.SSHRConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CareerServiceImpl implements CareerService {

	@Autowired
	CareerDao careerDao;
	
	@Autowired
	SendMailWithAttachment sendMailWithAttachment;
	
	@Value("${recruiter.mailid}")
	private String recruitmentTeamMailId;
	
	@Value("${all.mandatoryfields}")
	private String mandatoryfields;
	
	@Value("${jobpost.addedsuccessfully}")
	private String jobAddedSuccessfully;
	
	@Value("${jobpost.invactivate}")
	private String jobIsInactivate;
	
	@Value("${jobpost.jobid.isnotexist}")
	private String jobIdNotExist;
	
	@Value("${jobpost.jobid.isnotactive}")
	private String jobIsNotActive;
	
	@Override
	public HashMap<String, Object> addNewCareerPost (String department, String jobType, String positionType,
			String location, String jobTitle, String experience, String jobDescription) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		
		try {
			
			boolean isAllNonNull = Stream.of(department, jobType, positionType, location, jobTitle, experience, jobDescription ).allMatch(Objects::nonNull);

			boolean isStringNotEmpty = Stream.of(department, jobType, positionType, location, jobTitle, experience, jobDescription ).anyMatch(String::isEmpty);
				
			log.debug(" addNewCareerPost : isNull Or Not : isAllNonNull : " + isAllNonNull );
			log.debug(" addNewCareerPost : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty );
			
			if ( isAllNonNull) {
				if ( ! isStringNotEmpty) {
					
					String jobStatus = SSHRConstants.ACTIVE;
					LocalDate localDate = LocalDate.now(); 
					java.sql.Date currentDate = Date.valueOf(localDate);
					
					log.debug(" currentDate : "+currentDate);
					
					Integer count = careerDao.addNewJobPost(department, jobType, positionType, location,
							jobTitle, experience, jobDescription, jobStatus, currentDate);
					
					if (count > 0) {
						String message = jobAddedSuccessfully;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while persisting java code :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug( " addNewCareerPost : ERROR MESSAGE : " + message );
					}				
				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug( " addNewCareerPost : isEmpty Or Not : ERROR MESSAGE : " + message );
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug( " addNewCareerPost : isNull Or Not : ERROR MESSAGE : " + message );
			}
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" addNewCareerPost : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}

	@Override
	public HashMap<String, Object> getAvailableAllActiveJobsService() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<NewJobPost> jobPostList       = new ArrayList <NewJobPost>();
		
		try {
			
			jobPostList = careerDao.getAvailableAllActiveJobsDao();
			
			AllDetails.put("AvailableJobList", jobPostList);
			bean.setStatus(SSHRConstants.SUCCESS);		
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAvailableAllActiveJobsService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}

	@Override
	public HashMap<String, Object> changeJobStatusByJobIdService(int job_id) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		
		try {
			int isJobIdExistWithActiveStatus = careerDao.isJobIdExistWithActiveStatus(job_id);
			
			log.debug(" changeJobStatusByJobIdService : isJobIdExistWithActiveStatus : "+ isJobIdExistWithActiveStatus );
			
			if (isJobIdExistWithActiveStatus > 0) {
				
				int rowAffect = careerDao.updateJobStatusByIdDao(job_id);
				log.debug(" changeJobStatusByJobIdService : rowAffect : "+ rowAffect );
				
				if (rowAffect > 0) {
					
					bean.setStatus(SSHRConstants.SUCCESS);
					bean.setErrorMessage(jobIsInactivate);
					
				} else {
					
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(jobIdNotExist);
					bean.setErrorCode(404);
				
				}
			} else {
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(jobIsNotActive);
				bean.setErrorCode(404);
				log.debug(" changeJobStatusByJobIdService : "+"Entered JOB ID is not Exist with ACTIVE status.");
			}
				
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" changeJobStatusByJobIdService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}

	@Value("${jobpost.submit.jobapplication}")
	private String submitJobApplication;
	
	@Override
	public HashMap<String, Object> submitJobApplicationService(String applicantName, int jobId, String emailId,
			String contactNumber, String totalExperience, String about, String attachment, String attachmentType,
			String noticePeriod, String expectedCtc, String actualCtc, String readyForOnsite) {

		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		
		try {
			
	        boolean isAllNonNull = Stream.of(applicantName, emailId, contactNumber, totalExperience, about, 
	        		attachmentType, attachment ,noticePeriod, expectedCtc, 
	        		actualCtc, readyForOnsite ).allMatch(Objects::nonNull);
	        
	        boolean isStringNotEmpty = Stream.of(applicantName, emailId, contactNumber, totalExperience, about, 
	        		attachmentType, attachment,noticePeriod, expectedCtc, 
	        		actualCtc, readyForOnsite ).anyMatch(String::isEmpty);
			
			log.debug(" submitJobApplicationService : isNull Or Not : isAllNonNull : " + isAllNonNull );
			log.debug(" submitJobApplicationService : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty );
			
			if ( isAllNonNull) {
				if ( ! isStringNotEmpty) {
					
					int isJobApplicationAlreadyApplied = careerDao.isJobApplicationAlreadyExistOrNot(jobId, emailId, contactNumber);
						
						LocalDate localDate = LocalDate.now(); 
						java.sql.Date currentDate = Date.valueOf(localDate);
						
						log.debug(" currentDate : "+currentDate);
						log.debug(" isJobApplicationAlreadyApplied : "+isJobApplicationAlreadyApplied);
						
						Integer count = careerDao.addNewJobApplicant( jobId, applicantName, emailId, contactNumber, 
								totalExperience, about, currentDate, noticePeriod, expectedCtc, actualCtc, readyForOnsite );
						
						if (count > 0) {
							
							NewJobPost jobPostDetails = careerDao.getJobPostDetailsByJobId(jobId);
							log.debug(" NewJobPost : jobPostDetails : "+jobPostDetails.toString());
							
							String resumeName  = applicantName+"_CV_."+attachmentType.toLowerCase();
							String jobTitle    = jobPostDetails.getJobTitle();
							String mailSubject = jobTitle + " JobApplication By " + applicantName ;
							
							String mailBody = createMailBody(jobPostDetails, applicantName, emailId, contactNumber, 
									totalExperience, about, noticePeriod, expectedCtc, actualCtc, readyForOnsite);
													
							log.debug(" mailSubject : "+mailSubject);
							log.debug(" attachmentType : "+attachmentType);
							log.debug(" resumeName : "+resumeName);
							
							log.debug("mailBody : "+mailBody);
							
							boolean isMailSend = sendMailWithAttachment.sendEmail(recruitmentTeamMailId, 
									mailSubject, mailBody, attachment, attachmentType, resumeName);		
							if ( isMailSend ) {
								String message = submitJobApplication;
								bean.setStatus(SSHRConstants.SUCCESS);
								bean.setErrorMessage(message);
								log.debug(message);
							} else {
								String message = "Internal server error while sending an email please try again :";
								bean.setStatus(SSHRConstants.FAILED);
								bean.setErrorMessage(message);
								bean.setErrorCode(500);
								log.debug( " addNewCareerPost : ERROR MESSAGE : " + message );
							}							
						} else {
							String message = "Internal server error while persisting java code :";
							bean.setStatus(SSHRConstants.FAILED);
							bean.setErrorMessage(message);
							bean.setErrorCode(503);
							log.debug( " addNewCareerPost : ERROR MESSAGE : " + message );
						}						

				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug( " submitJobApplicationService : isEmpty Or Not : ERROR MESSAGE : " + message );
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug( " submitJobApplicationService : isNull Or Not : ERROR MESSAGE : " + message );
			}
		
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" submitJobApplicationService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}
	
	private String createMailBody(NewJobPost jobPostDetails, String applicantName, String emailId, 
			String contactNumber, String totalExperience, String about, String noticePeriod, 
			String expectedCtc, String actualCtc, String readyForOnsite ) {
		
		StringBuilder mailContent = new StringBuilder();
		
		mailContent.append("<strong>Job title : </strong>").append(jobPostDetails.getJobTitle()).append(",<br>")
				.append("<strong>Department : </strong>").append(jobPostDetails.getDepartment()).append(",<br>")
				.append("<strong>Experience required : </strong>").append(jobPostDetails.getExperience()).append(",<br>")
				.append("<strong>Job Posted Date : </strong>").append(jobPostDetails.getCreatedDate()).append(",<br>")
				.append("<strong>Job Type : </strong>").append(jobPostDetails.getJobType()).append(",<br>")
				.append("<strong>Position Type : </strong>").append(jobPostDetails.getPositionType()).append(",<br>")
				.append("<strong>Location : </strong>").append(jobPostDetails.getLocation()).append(".<br><br>")
				.append("<strong>Applicant Name : </strong>").append(applicantName).append(",<br>")
				.append("<strong>Notice Period : </strong>").append(noticePeriod).append(",<br>")
				.append("<strong>Expected CTC : </strong>").append(expectedCtc).append(",<br>")
				.append("<strong>Current CTC : </strong>").append(actualCtc).append(",<br>")
				.append("<strong>Ready For Travel Onsite : </strong>").append(readyForOnsite).append(",<br>")
				.append("<strong>Total Experience : </strong>").append(totalExperience).append(",<br>")
				.append("<strong>Email Id : </strong><a href='mailto:").append(emailId).append("'>").append(emailId)
				.append("</a>,<br>").append("<strong>Contact Number : </strong>").append("<a href='tel:")
				.append(contactNumber).append("'>").append(contactNumber).append("</a>").append(".<br><br>")
				.append("<strong>About Applicant : </strong>").append(about);
		
		return "<html><head><style>body { font-family: Calibri, sans-serif; }</style></head><body>"
				+ "Dear RecruitmentTeam,<br><br>" + mailContent.toString()
				+ ".<br><br><font color=\"#164A7C\">Thanks</font><br><br></body></html>";
		
	}

	@Override
	public HashMap<String, Object> getJobDetailsByJobIdService(int jobId) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		NewJobPost jobPostDetails          = new NewJobPost();
		
		try {
			
			Integer isJobIsExistWithId = careerDao.isJobIdExistWithActiveStatus(jobId);
			
			log.debug(" getJobDetailsByJobIdService : count : "+ isJobIsExistWithId);
			
			if ( isJobIsExistWithId > 0 ) {
			
				jobPostDetails = careerDao.getJobDetailsByJobIdsDao(jobId);
				
				log.debug(" jobPostDetails : "+jobPostDetails);
	
				bean.setStatus(SSHRConstants.SUCCESS);
				bean.setErrorCode(200);
			} else {
				String message = jobIdNotExist;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.error(" getJobDetailsByJobIdService : ERROR : " + message);
			}
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAvailableAllActiveJobsService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, jobPostDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			jobPostDetails = null;
		}
		return respMap;
	}

	@Override
	public HashMap<String, Object> sortAllAvailableJobsByInputService(String mod, String value) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<NewJobPost> jobPostList       = new ArrayList <NewJobPost>();
		
		try {
			
			if ( mod.contains("department") || mod.contains("position_type") ||mod.contains("location")  ) {
			
				jobPostList = careerDao.sortAllAvailableJobsByInputService(mod, value);
				
				AllDetails.put("AvailableJobList", jobPostList);
				bean.setStatus(SSHRConstants.SUCCESS);
			
			} else {
				String message = "mod value is incorrect please enter correct mod value like department, position_type, location. ";
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(503);
				log.error(" sortAllAvailableJobsByInputService : Exception : " + message);				
			}			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" sortAllAvailableJobsByInputService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
		
		
	}

}
