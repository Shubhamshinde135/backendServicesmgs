package com.mgstech.service;

import java.util.HashMap;

public interface CaseStudyService {

	HashMap<String, Object> addNewCaseStudyToDraft(String caseStudyTitle, String department, String caseStudyImage,
			String caseStudyDescription);

	HashMap<String, Object> addNewCaseStudyToPublish(String caseStudyTitle, String department, String caseStudyImage,
			String caseStudyDescription);

	HashMap<String, Object> deleteCaseStudyByIdService(int caseId);

	HashMap<String, Object> getAvailableAllPublishedCaseStudy();

	HashMap<String, Object> getAllCaseStudyForAdminService();

	HashMap<String, Object> publishedPendingCaseStudyService(int caseId);

	HashMap<String, Object> getCaseStudyDetailsByCaseIdService(int caseId);

}
