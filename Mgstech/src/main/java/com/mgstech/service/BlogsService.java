package com.mgstech.service;

import java.util.HashMap;

public interface BlogsService {

	HashMap<String, Object> addNewBlogToDraft(String blogTitle, String blogBy, String blogImage,
			String blogDescription);

	HashMap<String, Object> addNewBlogToPublish(String blogTitle, String blogBy, String blogImage,
			String blogDescription);

	HashMap<String, Object> deleteBlogByBlogId(int blogId);

	HashMap<String, Object> getAvailableAllActiveBlogsService();

	HashMap<String, Object> getAllBlogsForAdminService();

	HashMap<String, Object> publishedPendingBlogService(int blogId);

	HashMap<String, Object> getBlogDetailsByBlogIdService(int blogId);

}
