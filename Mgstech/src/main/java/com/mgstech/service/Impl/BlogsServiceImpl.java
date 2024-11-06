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

import com.mgstech.dao.BlogsDao;
import com.mgstech.entities.Blogs;
import com.mgstech.entities.ResponseBean;
import com.mgstech.service.BlogsService;
import com.mgstech.util.SSHRConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlogsServiceImpl implements BlogsService {

	@Autowired
	BlogsDao blogsDao;
	
	@Value("${blog.addedtodraft}")
	private String blogaddedtodraft;
	
	@Value("${all.mandatoryfields}")
	private String mandatoryfields;
	
	@Value("${blog.ispublishedsuccessfully}")
	private String blogPublished;
	
	@Value("${blog.deleted}")
	private String blogDeleted;
	
	@Value("${blog.isnotexistwith.draft}")
	private String blogNotExistWithDraft;
	
	@Value("${blog.isnotexist.withblogid}")
	private String blogIsNotExistWithId;

	@Override
	public HashMap<String, Object> addNewBlogToDraft(String blogTitle, String blogBy, String blogImage,
			String blogDescription) {

		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();

		try {

			boolean isAllNonNull = Stream.of(blogTitle, blogBy, blogDescription).allMatch(Objects::nonNull);

			boolean isStringNotEmpty = Stream.of(blogTitle, blogBy, blogDescription).anyMatch(String::isEmpty);

			log.debug(" addNewBlogToDraft : isNull Or Not : isAllNonNull : " + isAllNonNull);
			log.debug(" addNewBlogToDraft : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty);

			if (isAllNonNull) {
				if (!isStringNotEmpty) {

					String blogStatus = SSHRConstants.DRAFT;
					LocalDate localDate = LocalDate.now();
					java.sql.Date currentDate = Date.valueOf(localDate);

					log.debug(" currentDate : " + currentDate);

					Integer count = blogsDao.addNewBlogToDraftDao(blogTitle, blogBy, blogDescription, blogStatus,
							blogImage, currentDate);

					if (count > 0) {
						String message = blogaddedtodraft;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while persisting java code :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" addNewBlogToDraft : ERROR MESSAGE : " + message);
					}
				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug(" addNewBlogToDraft : isEmpty Or Not : ERROR MESSAGE : " + message);
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug(" addNewBlogToDraft : isNull Or Not : ERROR MESSAGE : " + message);
			}
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" addNewBlogToDraft : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;

	}

	@Override
	public HashMap<String, Object> addNewBlogToPublish(String blogTitle, String blogBy, String blogImage,
			String blogDescription) {

		HashMap<String, Object> respMap = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean = new ResponseBean();

		try {

			boolean isAllNonNull = Stream.of(blogTitle, blogBy, blogDescription).allMatch(Objects::nonNull);

			boolean isStringNotEmpty = Stream.of(blogTitle, blogBy, blogDescription).anyMatch(String::isEmpty);

			log.debug(" addNewBlogToPublish : isNull Or Not : isAllNonNull : " + isAllNonNull);
			log.debug(" addNewBlogToPublish : isEmpty Or Not : isStringIsNotEmpty : " + isStringNotEmpty);

			if (isAllNonNull) {
				if (!isStringNotEmpty) {

					String blogStatus = SSHRConstants.PUBLISHED;
					LocalDate localDate = LocalDate.now();
					java.sql.Date currentDate = Date.valueOf(localDate);

					log.debug(" currentDate : " + currentDate);

					Integer count = blogsDao.addNewBlogToPublishedDao(blogTitle, blogBy, blogDescription, blogStatus,
							blogImage, currentDate, currentDate);

					if (count > 0) {
						String message = blogPublished;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while persisting java code :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" addNewBlogToPublish : ERROR MESSAGE : " + message);
					}
				} else {
					String message = mandatoryfields;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(SSHRConstants.ERR_CD_999);
					log.debug(" addNewBlogToPublish : isEmpty Or Not : ERROR MESSAGE : " + message);
				}
			} else {
				String message = mandatoryfields;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(SSHRConstants.ERR_CD_999);
				log.debug(" addNewBlogToPublish : isNull Or Not : ERROR MESSAGE : " + message);
			}
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" addNewBlogToPublish : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
	}

	@Override
	public HashMap<String, Object> deleteBlogByBlogId(int blogId) {

		HashMap<String, Object> respMap = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean = new ResponseBean();

		try {

			int isBlogIdIsExist = blogsDao.isBlogIdIsExistOrNot(blogId);

			log.debug(" deleteBlogByBlogId : isBlogIdIsExist : " + isBlogIdIsExist);

			if (isBlogIdIsExist > 0) {

				int isBlogIsDeleted = blogsDao.deleteBlogByIdDao(blogId);

				if (isBlogIsDeleted > 0) {
					String message = blogDeleted;
					bean.setStatus(SSHRConstants.SUCCESS);
					bean.setErrorMessage(message);
					log.debug(message);
				} else {
					String message = "Internal server error while Deleting Blog please try again :";
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(503);
					log.debug(" deleteBlogByBlogId : ERROR MESSAGE : " + message);
				}

			} else {
				String message = "Blog Id is not exist with given blog id : " + blogId
						+ " : Please enter valid BlogID and try again.";
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.debug(" deleteBlogByBlogId : " + message);
			}

		} catch (Exception e) {

			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" deleteBlogByBlogId : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}

		return respMap;
	}

	@Override
	public HashMap<String, Object> getAvailableAllActiveBlogsService() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<Blogs> jobPostList            = new ArrayList <Blogs>();
		
		try {
			
			jobPostList = blogsDao.getAvailableAllActiveBlogssDao();
			
			AllDetails.put("blogsList", jobPostList);
			bean.setStatus(SSHRConstants.SUCCESS);			
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAvailableAllActiveBlogsService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;
		
	}

	@Override
	public HashMap<String, Object> getAllBlogsForAdminService() {
		
		HashMap<String, Object> respMap    = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean                  = new ResponseBean();
		List<Blogs> jobPostList            = new ArrayList <Blogs>();
		
		try {
			
			jobPostList = blogsDao.getAllBlogsForAdminDao();
			
			AllDetails.put("blogsList", jobPostList);
			bean.setStatus(SSHRConstants.SUCCESS);		
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getAllBlogsForAdminService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, AllDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			AllDetails = null;
		}
		return respMap;

	}

	@Override
	public HashMap<String, Object> publishedPendingBlogService(int blogId) {
		
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		HashMap<String, Object> AllDetails = new HashMap<String, Object>();
		ResponseBean bean = new ResponseBean();

		try {

			int isBlogIdIsExistWithDraftStatus = blogsDao.isBlogIdIsExistWithDraftStatus(blogId);
			int isBlogIdIsExist = blogsDao.isBlogIdIsExistOrNot(blogId);

			log.debug(" publishedPendingBlogService : isBlogIdIsExistWithDraftStatus : " + isBlogIdIsExistWithDraftStatus);

			if ( isBlogIdIsExist > 0 ) {
				
				if (isBlogIdIsExistWithDraftStatus > 0) {
	
					int isBlogIsGettingPublished = blogsDao.isBlogIsGettingPublished(blogId);
	
					if (isBlogIsGettingPublished > 0) {
						String message = blogPublished;
						bean.setStatus(SSHRConstants.SUCCESS);
						bean.setErrorMessage(message);
						log.debug(message);
					} else {
						String message = "Internal server error while Publishing Blog please try again :";
						bean.setStatus(SSHRConstants.FAILED);
						bean.setErrorMessage(message);
						bean.setErrorCode(503);
						log.debug(" publishedPendingBlogService : ERROR MESSAGE : " + message);
					}
	
				} else {
					String message = blogNotExistWithDraft;
					bean.setStatus(SSHRConstants.FAILED);
					bean.setErrorMessage(message);
					bean.setErrorCode(404);
					log.debug(" publishedPendingBlogService : " + message);
				}
			} else {
				String message = blogIsNotExistWithId + blogId ;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.debug(" publishedPendingBlogService : " + message);
			}

		} catch (Exception e) {

			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
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
	public HashMap<String, Object> getBlogDetailsByBlogIdService(int blogId) {
		
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		ResponseBean bean               = new ResponseBean();
		Blogs blogPostDetails           = new Blogs();
		
		try {
			
			Integer isBlogIsExistWithId = blogsDao.isBlogIdIsExistOrNotWithActiveStatus(blogId);
			
			log.debug(" getBlogDetailsByBlogIdService : count : "+ isBlogIsExistWithId);
			
			if ( isBlogIsExistWithId > 0 ) {
			
				blogPostDetails = blogsDao.getBlogDetailsByBlogIdDao(blogId);
				
				log.debug(" blogPostDetails : "+blogPostDetails);
	
				bean.setStatus(SSHRConstants.SUCCESS);
				bean.setErrorCode(200);
			} else {
				String message = blogIsNotExistWithId + blogId ;
				bean.setStatus(SSHRConstants.FAILED);
				bean.setErrorMessage(message);
				bean.setErrorCode(404);
				log.error(" getBlogDetailsByBlogIdService : ERROR : " + message);
			}
			
		} catch (Exception e) {
			bean.setStatus(SSHRConstants.FAILED);
			bean.setErrorMessage(e.getMessage());
			bean.setErrorCode(500);
			log.error(" getBlogDetailsByBlogIdService : Exception : " + e.getMessage());

		} finally {
			respMap.put(SSHRConstants.DATA, blogPostDetails);
			respMap.put(SSHRConstants.RESPONSE, bean);

			bean = null;
			blogPostDetails = null;
		}
		return respMap;
		
	}

}
