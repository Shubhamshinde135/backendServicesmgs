package com.mgstech.service.Impl;

import java.sql.Date;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mgstech.dao.CaseStudyDao;
import com.mgstech.entities.CaseStudy;
import com.mgstech.entities.ResponseBean;
import com.mgstech.service.CaseStudyService;
import com.mgstech.util.SSHRConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CaseStudyServiceImpl implements CaseStudyService {
	
	@Autowired
	CaseStudyDao caseStudyDao;
	
	@Value("${all.mandatoryfields}")
	private String mandatoryfields;
	
	@Value("${casestudy.addedtodraft}")
	private String casestudyAddedToDraft;
	
	@Value("${casestudy.published.successfully}")
	private String casestudyIsPublished;
	
	@Value("${casestudy.caseidnotexist.draftstatus}")
	private String caseidnotexistwithdraft;
	
	@Value("${casestudy.caseidnotexist}")
	private String caseidnotexist;
	
	@Value("${casestudy.ispublished}")
	private String caseStudyPublished;
	
	@Value("${casestudy.deletedsuccessfully}")
	private String caseStudeyIsDeleted;

	@Override
	public HashMap<String, Object> addNewCaseStudyToDraft(String caseStudyTitle, String department,
			String caseStudyImage, String caseStudyDescription) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			boolean isAllNonNull = Stream.of(caseStudyTitle, department, caseStudyImage, 
					caseStudyDescription).allMatch(Objects::nonNull);

			boolean isStringNotEmpty = Stream.of(caseStudyTitle, department, caseStudyImage, 
					caseStudyDescription).anyMatch(String::isEmpty);

			log.debug(" addNewCaseStudyToDraft : isNull Or Not : isAllNonNull : " + isAllNonNull);
			log.debug(" addNewCaseStudyToDraft : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty);

			if (isAllNonNull) {
				if (!isStringNotEmpty) {

					String caseStudyStatus = SSHRConstants.DRAFT;
					LocalDate localDate = LocalDate.now();
					java.sql.Date currentDate = Date.valueOf(localDate);

					log.debug(" currentDate : " + currentDate);

					Integer count = caseStudyDao.addNewCaseStudyToDraftDao(caseStudyTitle, department, caseStudyDescription, 
							caseStudyStatus, caseStudyImage, currentDate);

					if (count > 0) {
						String message = casestudyAddedToDraft;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while persisting java code :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" addNewCaseStudyToDraft : ERROR MESSAGE : " + message);
					}
				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug(" addNewCaseStudyToDraft : isEmpty Or Not : ERROR MESSAGE : " + message);
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug(" addNewCaseStudyToDraft : isNull Or Not : ERROR MESSAGE : " + message);
			}
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" addNewCaseStudyToDraft : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;	
	}

	@Override
	public HashMap<String, Object> addNewCaseStudyToPublish(String caseStudyTitle, String department,
			String caseStudyImage, String caseStudyDescription) {
		
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean = new ResponseBean();

		try {

			boolean isAllNonNull = Stream.of(caseStudyTitle, department, caseStudyImage, caseStudyDescription).allMatch(Objects::nonNull);

			boolean isStringNotEmpty = Stream.of(caseStudyTitle, department, caseStudyImage, caseStudyDescription).anyMatch(String::isEmpty);

			log.debug(" addNewCaseStudyToPublish : isNull Or Not : isAllNonNull : " + isAllNonNull);
			log.debug(" addNewCaseStudyToPublish : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty);

			if (isAllNonNull) {
				if (!isStringNotEmpty) {

					String caseStudyStatus = SSHRConstants.PUBLISHED;
					LocalDate localDate = LocalDate.now();
					java.sql.Date currentDate = Date.valueOf(localDate);

					log.debug(" currentDate : " + currentDate);

					Integer count = caseStudyDao.addNewCaseStudyToPublishDao(caseStudyTitle, department, caseStudyDescription,
							caseStudyStatus, caseStudyImage, currentDate, currentDate);

					if (count > 0) {
						String message = caseStudyPublished;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while persisting java code :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" addNewCaseStudyToPublish : ERROR MESSAGE : " + message);
					}
				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug(" addNewCaseStudyToPublish : isEmpty Or Not : ERROR MESSAGE : " + message);
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug(" addNewCaseStudyToPublish : isNull Or Not : ERROR MESSAGE : " + message);
			}
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" addNewCaseStudyToPublish : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
		
	}

	@Override
	public HashMap<String, Object> deleteCaseStudyByIdService(int caseId) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			int isCaseStudyIdIsExistOrNot = caseStudyDao.isCaseStudyIdIsExistOrNot(caseId);

			log.debug(" deleteCaseStudyByIdService : isCaseStudyIdIsExistOrNot : " + isCaseStudyIdIsExistOrNot);

			if (isCaseStudyIdIsExistOrNot > 0) {

				int isCaseStudyIsDeleted = caseStudyDao.deleteCaseStudyByIdDao(caseId);

				if (isCaseStudyIsDeleted > 0) {
					String message = caseStudeyIsDeleted;
					bean.setStatus(SSHRConstants.SUCCESS);
					bean.setErrorMessage(message);
					log.debug(message);
				} else {
					String message = "Internal server error while Deleting Blog please try again :";
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(500);
					log.debug(" deleteCaseStudyByIdService : ERROR MESSAGE : " + message);
				}

			} else {
				String message = "Case Study Id is not exist with given case id : " + caseId
						+ " : Please enter valid CaseId and try again.";
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.debug(" deleteCaseStudyByIdService : " + message);
			}

		} catch (Exception e) {

			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" deleteCaseStudyByIdService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;		
	}

	@Override
	public HashMap<String, Object> getAvailableAllPublishedCaseStudy() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<CaseStudy> publishedCaseStudy = new ArrayList <CaseStudy>();
		
		try {
			
			publishedCaseStudy = caseStudyDao.getAvailableAllPublishedCaseStudyDao();
			
			AllDetails.put("caseStudyList", publishedCaseStudy);
			bean.setStatus(SSHRConstants.SUCCESS);	
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAvailableAllPublishedCaseStudy : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;		
	}

	@Override
	public HashMap<String, Object> getAllCaseStudyForAdminService() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<CaseStudy> caseStudyList      = new ArrayList <CaseStudy>();
		
		try {
			
			caseStudyList = caseStudyDao.getAllCaseStudyForAdminDao();
			
			AllDetails.put("caseStudyList", caseStudyList);
			bean.setStatus(SSHRConstants.SUCCESS);	
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAllCaseStudyForAdminService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
		
	}

	@Override
	public HashMap<String, Object> publishedPendingCaseStudyService(int caseId) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			int isCaseIdIsExistWithDraftStatus = caseStudyDao.isCaseIdIsExistWithDraftStatus(caseId);
			int isCaseIdIsExist = caseStudyDao.isCaseStudyIdIsExistOrNot(caseId);

			log.debug(" publishedPendingCaseStudyService : isCaseIdIsExistWithDraftStatus : " + isCaseIdIsExistWithDraftStatus);

			if ( isCaseIdIsExist > 0 ) {
				
				if (isCaseIdIsExistWithDraftStatus > 0) {
	
					int isCaseIsGettingPublished = caseStudyDao.isCaseStudyIsGettingPublished(caseId);
	
					if (isCaseIsGettingPublished > 0) {
						String message = casestudyIsPublished;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while Publishing Blog please try again :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" publishedPendingCaseStudyService : ERROR MESSAGE : " + message);
					}
	
				} else {
					String message = caseidnotexistwithdraft;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(404);
					log.debug(" publishedPendingCaseStudyService : " + message);
				}
			} else {
				String message = caseidnotexist + caseId ;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.debug(" publishedPendingCaseStudyService : " + message);
			}

		} catch (Exception e) {

			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(401);
			log.error(" publishedPendingBlogService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}

		return respMap;
			
	}

	@Override
	public HashMap<String, Object> getCaseStudyDetailsByCaseIdService(int caseId) {
		
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		ResponseBean bean               = new ResponseBean();
		CaseStudy caseStudyDetails      = new CaseStudy();
		
		try {
			
			Integer isCaseStudyIsExistWithId = caseStudyDao.isCaseIdIsExistOrNotWithPublishedStatus(caseId);
			
			log.debug(" getCaseStudyDetailsByCaseIdService : count : "+ isCaseStudyIsExistWithId);
			
			if ( isCaseStudyIsExistWithId > 0 ) {
			
				caseStudyDetails = caseStudyDao.getCaseStudyDetailsByCaseIdDao(caseId);
				
				log.debug(" caseStudyDetails : "+caseStudyDetails);
	
				bean.setStatus(SSHRConstants.SUCCESS);

			} else {
				String message = caseidnotexist + caseId ;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.error(" getCaseStudyDetailsByCaseIdService : ERROR : " + message);
			}
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getCaseStudyDetailsByCaseIdService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, caseStudyDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			caseStudyDetails = null;
		}
		return respMap;
	}
	
}
