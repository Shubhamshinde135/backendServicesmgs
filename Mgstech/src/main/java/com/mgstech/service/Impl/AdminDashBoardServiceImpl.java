package com.mgstech.service.Impl;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mgstech.dao.AdminDashBoardDao;
import com.mgstech.entities.JobPostPositionRecords;
import com.mgstech.entities.ResponseBean;
import com.mgstech.messagingUtil.SimpleSendTextMail;
import com.mgstech.service.AdminDashBoardService;
import com.mgstech.util.SSHRConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdminDashBoardServiceImpl implements AdminDashBoardService {

	@Autowired
	AdminDashBoardDao adminDashBoardDao;
	
	@Autowired
	SimpleSendTextMail simpleSendTextMail;
	
	@Value("${info.mailid}")
	private String inforecipientmailid;
	
    @Value("${all.mandatoryfields}")
    private String mandatoryFieldsErrorMessage;
    
	@Value("${username.invalid}")
	private String invalidUsername;
	
	@Value("${password.invalid}")
	private String invalidPassword;
	
	@Value("${contact.us.mailsubject}")
	private String mailSubjectContactUs;
	
	@Override
	public HashMap<String, Object> getAdminDashBoardDetails() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		
		int technologyPosition = 0;
		int oraclePosition = 0;
		int bpoPosition = 0;
		int operationPosition = 0;
		
		try {
			
			technologyPosition =  adminDashBoardDao.getActiveJobPosition(SSHRConstants.TECHNOLOGY);
			oraclePosition = adminDashBoardDao.getActiveJobPosition(SSHRConstants.ORACLE);
			bpoPosition = adminDashBoardDao.getActiveJobPosition(SSHRConstants.BPO);
			operationPosition = adminDashBoardDao.getActiveJobPosition(SSHRConstants.OPERATION);
			
			log.debug(" getAdminDashBoardDetails : technologyPosition : " + technologyPosition);
			log.debug(" getAdminDashBoardDetails : oraclePosition     : " + oraclePosition);
			log.debug(" getAdminDashBoardDetails : bpoPosition        : " + bpoPosition);
			log.debug(" getAdminDashBoardDetails : operationPosition  : " + operationPosition);			
			
			AllDetails.put("technologyPosition", technologyPosition);
			AllDetails.put("oraclePosition", oraclePosition);
			AllDetails.put("bpoPosition", bpoPosition);
			AllDetails.put("operationPosition", operationPosition);
			
			bean.setStatus(SSHRConstants.SUCCESS);
					
		} catch (Exception e) {
			e.printStackTrace();
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAdminDashBoardDetails : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}
	
    private boolean isNullOrEmpty(String str) {
        return Optional.ofNullable(str).map(String::isEmpty).orElse(true);
    } 

	@Override
	public HashMap<String, Object> openJobPositionList(String orderBy, Integer pageSize, Integer offset, String ascdsc) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<JobPostPositionRecords> data  = new ArrayList<JobPostPositionRecords>();
		HashMap<String, Object> AllDetails = new LinkedHashMap<String, Object>();
		
		try {
			
			boolean orderByNullOrEmpty = isNullOrEmpty(orderBy);
			boolean ascdscNullOrEmpty = isNullOrEmpty(ascdsc);
			
			if (!orderByNullOrEmpty && !ascdscNullOrEmpty ) {
				
				if (pageSize == null || pageSize <= 0) {
		            log.debug(" openJobPositionList : Invalid pageSize : must be greater than 0 : original pageSize : " + pageSize);
		            pageSize = 5;
		        }
		        if (offset == null || offset < 0) {
		            log.debug(" openJobPositionList : Invalid offset: must be non-negative : original offset : " + offset);
		            offset = 0;
		        }
				data = adminDashBoardDao.getOpenJobPositionListAccordingToPagination( orderBy, pageSize, offset, ascdsc );
				
				int technologyPosition = 0;
				int oraclePosition = 0;
				int bpoPosition = 0;
				int operationPosition = 0;
				
				int totalRecords = 0;
				int max_rownum = 0;
				
				totalRecords = adminDashBoardDao.getTotalRecorsCount();
				max_rownum =  (int) Math.ceil((double) totalRecords / pageSize);
						
				technologyPosition =  adminDashBoardDao.getActiveJobPosition(SSHRConstants.TECHNOLOGY);
				oraclePosition = adminDashBoardDao.getActiveJobPosition(SSHRConstants.ORACLE);
				bpoPosition = adminDashBoardDao.getActiveJobPosition(SSHRConstants.BPO);
				operationPosition = adminDashBoardDao.getActiveJobPosition(SSHRConstants.OPERATION);
				
				log.debug(" getAdminDashBoardDetails : technologyPosition : " + technologyPosition);
				log.debug(" getAdminDashBoardDetails : oraclePosition     : " + oraclePosition);
				log.debug(" getAdminDashBoardDetails : bpoPosition        : " + bpoPosition);
				log.debug(" getAdminDashBoardDetails : operationPosition  : " + operationPosition);		
				
				AllDetails.put("technologyPosition", technologyPosition);
				AllDetails.put("oraclePosition", oraclePosition);
				AllDetails.put("bpoPosition", bpoPosition);
				AllDetails.put("operationPosition", operationPosition);
				
				AllDetails.put("totalRecords", totalRecords);
				AllDetails.put("max_rownum", max_rownum);

				AllDetails.put("response", data);
				bean.setStatus(SSHRConstants.SUCCESS);

			} else {
				String message = mandatoryFieldsErrorMessage;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorCode(404);
				bean.setErrorMessage(message);
				log.debug(" openJobPositionList : error message : " + message);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" openJobPositionList : Exception : " + e.getMessage());
		} finally {
			respMap.put(SSHRConstants.RESPONSE, bean);
			respMap.put(SSHRConstants.DATA, AllDetails);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}
	
	@Override
	public HashMap<String, Object> userAuthenticateService(String user_name, String password) {
		
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		HashMap<String, Object> AllData = new HashMap<String, Object>();
		ResponseBean bean               = new ResponseBean();

		try {
			
			Integer isUserNameExist = adminDashBoardDao.isUserNameExist(user_name);
			log.debug(" : userAuthenticateService : isUserNameExist : count : " + isUserNameExist);
			
			if ( isUserNameExist > 0) {
				
				Integer isPasswordValid = adminDashBoardDao.isPasswordValid(user_name, password);
				log.debug(" : userAuthenticateService : isPasswordValid : count : " + isPasswordValid);
				
				if ( isPasswordValid > 0 ) {
					bean.setStatus(SSHRConstants.SUCCESS);
					
				} else {
					String message = invalidPassword;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(404);
					log.error(" : userAuthenticateService : Error : "+message);
				}			
			} else {
				String message = invalidUsername;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.error(" : userAuthenticateService : Error : "+message);
			}
		} catch (Exception e) {
			log.error(" : userAuthenticateService : validateUser : " + e.getMessage());
			e.printStackTrace();
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
		} finally {
			respMap.put(SSHRConstants.RESPONSE, bean);
			respMap.put(SSHRConstants.DATA, AllData);

			bean = null;
			AllData = null;
		}
		return respMap;
	}

	@Override
	public HashMap<String, Object> contactUsThroghWebsite(String firstName, String lastName, String emailId,
			String department, String message) {
		
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		HashMap<String, Object> AllData = new HashMap<String, Object>();
		ResponseBean bean               = new ResponseBean();

		try {
			
	        boolean isAllNonNull = Stream.of(firstName, lastName, emailId, department, message ).allMatch(Objects::nonNull);
	        
	        boolean isStringNotEmpty = Stream.of(firstName, lastName, emailId, department, message ).anyMatch(String::isEmpty);
			
			if ( isAllNonNull) {
				if ( ! isStringNotEmpty) {
					int isContactUsPersisted = adminDashBoardDao.persistContactUsRecords(firstName, 
							lastName, emailId, department, message);
					
					if ( isContactUsPersisted > 0 ) {
					
						String mailSubject = mailSubjectContactUs;
						
						String mailBody = createMailBody(firstName, lastName, emailId, department, message);
						
						boolean isMailSend = simpleSendTextMail.sendEmail(inforecipientmailid, mailSubject, mailBody);
						if( isMailSend  ) {
							bean.setStatus(SSHRConstants.SUCCESS);
							bean.setErrorMessage("Thank you for contacting us. We will get back to you soon.");

						} else {
							String mesage = "Error while submitting application please try again.";
							bean.setStatus(SSHRConstants.FAILED);
							bean.setErrorMessage(message);
							bean.setErrorCode(503);
							log.debug( " submitJobApplicationService : ERROR MESSAGE : " + mesage );
						}
					} else {
						String mesage = "Internal server error while saving data in database.";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug( " submitJobApplicationService : " + mesage );
					}
				} else {
					String mesage = mandatoryFieldsErrorMessage;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug( " submitJobApplicationService : isNull Or Not : ERROR MESSAGE : " + mesage );
				}
			} else {
				String mesage = mandatoryFieldsErrorMessage;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug( " submitJobApplicationService : isNull Or Not : ERROR MESSAGE : " + mesage );
			}
			
		} catch (Exception e) {
			log.error(" : contactUsThroghWebsite : error : " + e.getMessage());
			e.printStackTrace();
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
		} finally {
			respMap.put(SSHRConstants.RESPONSE, bean);
			respMap.put(SSHRConstants.DATA, AllData);

			bean = null;
			AllData = null;
		}
		return respMap;
	}
	
	
	private String createMailBody(String firstName, String lastName, String emailId, String department,
			String message) {

		StringBuilder mailContent = new StringBuilder();

		 mailContent.append("<strong>FirstName  : </strong>").append(firstName).append(",<br>")
					.append("<strong>LastName   : </strong>").append(lastName).append(",<br>")
					.append("<strong>Email Id   : </strong><a href='mailto:").append(emailId)
					.append("'>").append(emailId).append("</a>,<br>")
					.append("<strong>Department : </strong>").append(department).append(",<br>")
					.append("<strong>Message    : </strong>").append(message).append(".<br>");

		return "<html><head><style>body { font-family: Calibri, sans-serif; }</style></head><body>"
				+ "Dear Team,<br><br>" + mailContent.toString()
				+ "<br><font color=\"#164A7C\">Thanks</font><br><br></body></html>";

	}

	@Override
	public HashMap<String, Object> getAppVersionBySession(String sessionId) {
		
        HashMap<String, Object> respMap         = new HashMap<String, Object>();
        ResponseBean bean                       = new ResponseBean();
        HashMap<String, Object> AllDetails      = new HashMap<String, Object>();

        try {

            bean.setStatus(SSHRConstants.SUCCESS);
            
        } catch (Exception e) {
            log.error(" getAppVersionBySession : SSHRException : " + e.getLocalizedMessage());

            AllDetails = null;
            bean.setStatus(SSHRConstants.FAILED);
            bean.setErrorMessage(e.getMessage());
            bean.setErrorCode(500);
 
        } finally {
            respMap.put(SSHRConstants.RESPONSE, bean);
            respMap.put(SSHRConstants.DATA, AllDetails);
 
            bean = null;
            AllDetails = null;
        }        
        return respMap;
		
	}
	

}
