package com.mgstech.service;

import java.util.HashMap;

public interface AdminDashBoardService {

	HashMap<String, Object> getAdminDashBoardDetails();

	HashMap<String, Object> openJobPositionList(String orderBy, Integer pageSize, Integer offset, String ascdesc);

	HashMap<String, Object> userAuthenticateService(String user_name, String password);

	HashMap<String, Object> contactUsThroghWebsite(String firstName, String lastName, String emailId, String department,
			String message);

	HashMap<String, Object> getAppVersionBySession(String sessionId);

}
