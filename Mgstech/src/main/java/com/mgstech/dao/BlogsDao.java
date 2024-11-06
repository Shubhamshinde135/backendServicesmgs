package com.mgstech.dao;

import java.sql.Date;
import java.util.List;

import com.mgstech.entities.Blogs;

public interface BlogsDao {

	Integer addNewBlogToDraftDao(String blogTitle, String blogBy, String blogDescription, String blogStatus,
			String blogImage, Date currentDate);

	Integer addNewBlogToPublishedDao(String blogTitle, String blogBy, String blogDescription, String blogStatus,
			String blogImage, Date currentDate, Date currentDate2);

	int isBlogIdIsExistOrNot(int blogId);

	int deleteBlogByIdDao(int blogId);

	List<Blogs> getAvailableAllActiveBlogssDao();

	List<Blogs> getAllBlogsForAdminDao();

	int isBlogIdIsExistWithDraftStatus(int blogId);

	int isBlogIsGettingPublished(int blogId);

	Integer isBlogIdIsExistOrNotWithActiveStatus(int blogId);

	Blogs getBlogDetailsByBlogIdDao(int blogId);

}
