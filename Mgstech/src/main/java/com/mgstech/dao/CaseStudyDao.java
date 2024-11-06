package com.mgstech.dao;

import java.sql.Date;
import java.util.List;

import com.mgstech.entities.CaseStudy;

public interface CaseStudyDao {

	Integer addNewCaseStudyToDraftDao(String caseStudyTitle, String department, String caseStudyDescription,
			String caseStudyStatus, String caseStudyImage, Date currentDate);

	Integer addNewCaseStudyToPublishDao(String caseStudyTitle, String department, String caseStudyDescription,
			String caseStudyStatus, String caseStudyImage, Date currentDate, Date currentDate2);

	int isCaseStudyIdIsExistOrNot(int caseId);

	int deleteCaseStudyByIdDao(int caseId);

	List<CaseStudy> getAvailableAllPublishedCaseStudyDao();

	List<CaseStudy> getAllCaseStudyForAdminDao();

	int isCaseIdIsExistWithDraftStatus(int caseId);

	int isCaseStudyIsGettingPublished(int caseId);

	Integer isCaseIdIsExistOrNotWithPublishedStatus(int caseId);

	CaseStudy getCaseStudyDetailsByCaseIdDao(int caseId);

}
