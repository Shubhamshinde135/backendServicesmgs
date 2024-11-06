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

import com.mgstech.dao.NewsDao;
import com.mgstech.entities.News;
import com.mgstech.entities.ResponseBean;
import com.mgstech.service.NewsService;
import com.mgstech.util.SSHRConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

	@Autowired
	NewsDao newsDao;
	
	@Value("${all.mandatoryfields}")
	private String mandatoryfields;

	@Value("${news.isaddedtodraft}")
	private String newsAddedToDraft;
	
	@Value("${news.isnotexist.withdraftstatus}")
	private String newsNotExistWithDraftStatus;
	
	@Value("${news.newsidnotexist}")
	private String newsNotExist;

	@Value("${news.ispublished}")
	private String newsIsPublished;

	@Value("${news.isdeletedsuccessfully}")
	private String newsDeletedSuccessfully;
	
	@Override
	public HashMap<String, Object> addNewNewsToDraftService(String newsTitle, String newsTpye, String newsMedia) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			log.debug("input : newsTpye "+newsTpye);
			log.debug("input : newsTitle "+newsTitle);
			
			boolean isAllNonNull = Stream.of(newsTitle, newsTpye, newsMedia).allMatch(Objects::nonNull);

			boolean isStringNotEmpty = Stream.of(newsTitle, newsTpye, newsMedia).anyMatch(String::isEmpty);

			log.debug(" addNewNewsToDraftService : isNull Or Not : isAllNonNull : " + isAllNonNull);
			log.debug(" addNewNewsToDraftService : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty);

			if (isAllNonNull) {
				if (!isStringNotEmpty) {

					String newsStatus = SSHRConstants.DRAFT;
					LocalDate localDate = LocalDate.now();
					java.sql.Date currentDate = Date.valueOf(localDate);

					log.debug(" currentDate : " + currentDate);

					Integer count = newsDao.addNewNewsToDraftDao(newsTitle, newsTpye, newsStatus, newsMedia, currentDate);

					if (count > 0) {
						String message = newsAddedToDraft;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while persisting java code :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" addNewNewsToDraftService : ERROR MESSAGE : " + message);
					}
				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug(" addNewNewsToDraftService : isEmpty Or Not : ERROR MESSAGE : " + message);
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug(" addNewNewsToDraftService : isNull Or Not : ERROR MESSAGE : " + message);
			}
		} catch (Exception e) {
			e.printStackTrace();
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" addNewNewsToDraftService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;			
	}
	
	@Override
	public HashMap<String, Object> addNewNewsToPublishService(String newsTitle, String newsTpye, String newsMedia) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			boolean isAllNonNull = Stream.of(newsTitle, newsTpye, newsMedia).allMatch(Objects::nonNull);

			boolean isStringNotEmpty = Stream.of(newsTitle, newsTpye, newsMedia).anyMatch(String::isEmpty);

			log.debug(" addNewNewsToPublishService : isNull Or Not : isAllNonNull : " + isAllNonNull);
			log.debug(" addNewNewsToPublishService : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty);

			if (isAllNonNull) {
				if (!isStringNotEmpty) {

					String newsStatus = SSHRConstants.PUBLISHED;
					LocalDate localDate = LocalDate.now();
					java.sql.Date currentDate = Date.valueOf(localDate);

					log.debug(" currentDate : " + currentDate);

					Integer count = newsDao.addNewNewsToPublishDao(newsTitle, newsTpye, 
							newsStatus, newsMedia, currentDate, currentDate);

					if (count > 0) {
						String message = newsIsPublished;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while persisting java code Please try again. ";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" addNewNewsToPublishService : ERROR MESSAGE : " + message);
					}
				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug(" addNewNewsToPublishService : isEmpty Or Not : ERROR MESSAGE : " + message);
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug(" addNewNewsToPublishService : isNull Or Not : ERROR MESSAGE : " + message);
			}
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" addNewNewsToPublishService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;		
	}

	@Override
	public HashMap<String, Object> deleteNewsByIdService(int newsId) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			int isNewsIdIsExistOrNot = newsDao.isNewsIdIsExistOrNotDao(newsId);

			log.debug(" deleteNewsByIdService : isNewsIdIsExistOrNot : " + isNewsIdIsExistOrNot);

			if (isNewsIdIsExistOrNot > 0) {

				int isNewsIsDeleted = newsDao.deleteNewsByIdDao(newsId);

				if (isNewsIsDeleted > 0) {
					String message = newsDeletedSuccessfully;
					bean.setStatus(SSHRConstants.SUCCESS);
					bean.setErrorMessage(message);
					log.debug(message);
				} else {
					String message = "Internal server error while Deleting Blog please try again :";
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(503);
					log.debug(" deleteNewsByIdService : ERROR MESSAGE : " + message);
				}

			} else {
				String message = "News Id is not exist with given news id : " + newsId
						+ " : Please enter valid NewsId and try again.";
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.debug(" deleteNewsByIdService : " + message);
			}

		} catch (Exception e) {

			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(SSHRConstants.ERR_CD_999);
			log.error(" deleteNewsByIdService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;			
	}

	@Override
	public HashMap<String, Object> getAvailableAllPublishedNewsService() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<News> newsPostList            = new ArrayList <News>();
		
		try {
			
			newsPostList = newsDao.getAvailableAllPublishedNewsDao();
			
			AllDetails.put("newsList", newsPostList);
			bean.setStatus(SSHRConstants.SUCCESS);		
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAvailableAllPublishedNewsService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}


	@Override
	public HashMap<String, Object> getAllNewsForAdminService() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<News> newsList                = new ArrayList <News>();
		
		try {
			
			newsList = newsDao.getAllNewsForAdminServiceDao();
			
			AllDetails.put("newsList", newsList);
			bean.setStatus(SSHRConstants.SUCCESS);	
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAllNewsForAdminService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;		
	}

	@Override
	public HashMap<String, Object> publishedPendingNewsService(int newsId) {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			int isNewsIsExistWithDraftStatus = newsDao.isNewsIdIsExistOrNotWithDraftDao(newsId);
			int isNewsIdIsExist = newsDao.isNewsIdIsExistOrNotDao(newsId);

			log.debug(" publishedPendingNewsService : isNewsIsExistWithDraftStatus : " + isNewsIsExistWithDraftStatus);

			if ( isNewsIdIsExist > 0 ) {
				
				if (isNewsIsExistWithDraftStatus > 0) {
	
					int isNewsIsGettingPublished = newsDao.isNewsIsGettingPublishedDao(newsId);
	
					if (isNewsIsGettingPublished > 0) {
						String message = newsIsPublished;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while Publishing News please try again :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" publishedPendingNewsService : ERROR MESSAGE : " + message);
					}
	
				} else {
					String message = newsNotExistWithDraftStatus;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(404);
					log.debug(" publishedPendingNewsService : " + message);
				}
			} else {
				String message = newsNotExist + newsId ;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.debug(" publishedPendingNewsService : " + message);
			}

		} catch (Exception e) {

			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" publishedPendingNewsService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}

		return respMap;		
	}

	@Override
	public HashMap<String, Object> getNewsyDetailsByNewsIdService(int newsId) {
		
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		ResponseBean bean               = new ResponseBean();
		News newsDetails                = new News();
		
		try {
			
			Integer isNewsIsExistWithId = newsDao.isNewsIdIsExistOrNotWithPublishedStatus(newsId);
			
			log.debug(" getNewsyDetailsByNewsIdService : count : "+ isNewsIsExistWithId);
			
			if ( isNewsIsExistWithId > 0 ) {
			
				newsDetails = newsDao.getNewsDetailsByNewsIdDao(newsId);
				
				log.debug(" newsDetails : "+newsDetails);
	
				bean.setStatus(SSHRConstants.SUCCESS);

			} else {
				String message = newsNotExist + newsId ;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.error(" getNewsyDetailsByNewsIdService : ERROR : " + message);
			}
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getNewsyDetailsByNewsIdService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, newsDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			newsDetails = null;
		}
		return respMap;

	}

}
