package com.mgstech.dao;

import java.sql.Date;
import java.util.List;

import com.mgstech.entities.News;

public interface NewsDao {

	Integer addNewNewsToDraftDao(String newsTitle, String newsTpye, String newsStatus, String newsMedia, Date currentDate);

	Integer addNewNewsToPublishDao(String newsTitle, String newsTpye, String newsStatus, String newsMedia,
			Date currentDate, Date currentDate2);

	int isNewsIdIsExistOrNotDao(int newsId);

	int deleteNewsByIdDao(int newsId);

	List<News> getAvailableAllPublishedNewsDao();

	List<News> getAllNewsForAdminServiceDao();

	int isNewsIdIsExistOrNotWithDraftDao(int newsId);

	int isNewsIsGettingPublishedDao(int newsId);

	Integer isNewsIdIsExistOrNotWithPublishedStatus(int newsId);

	News getNewsDetailsByNewsIdDao(int newsId);

}
