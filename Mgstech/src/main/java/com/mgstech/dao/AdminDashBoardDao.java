package com.mgstech.dao;

import java.util.List;

import com.mgstech.entities.JobPostPositionRecords;

public interface AdminDashBoardDao {

	int getActiveJobPosition(String technology);

	List<JobPostPositionRecords> getOpenJobPositionListAccordingToPagination(String orderBy, Integer pageSize, Integer offset,
			String ascdsc);

	Integer isUserNameExist(String user_name);

	Integer isPasswordValid(String user_name, String password);

	int persistContactUsRecords(String firstName, String lastName, String emailId, String department, String message);

	int getTotalRecorsCount();

}
