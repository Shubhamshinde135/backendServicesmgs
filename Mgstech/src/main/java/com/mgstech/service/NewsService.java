package com.mgstech.service;

import java.util.HashMap;

public interface NewsService {

	HashMap<String, Object> addNewNewsToDraftService(String newsTitle, String newsTpye, String newsMedia);

	HashMap<String, Object> addNewNewsToPublishService(String newsTitle, String newsTpye, String newsMedia);

	HashMap<String, Object> deleteNewsByIdService(int newsId);

	HashMap<String, Object> getAvailableAllPublishedNewsService();

	HashMap<String, Object> getAllNewsForAdminService();

	HashMap<String, Object> publishedPendingNewsService(int newsId);

	HashMap<String, Object> getNewsyDetailsByNewsIdService(int newsId);

}
