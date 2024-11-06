//package com.mgstech.filter;
//
//import java.io.IOException;
//import java.sql.Timestamp;
//import java.time.LocalDateTime;
//import java.util.Base64;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import com.mgstech.entities.JwtToken;
//import com.mgstech.util.SQLConstants;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//
//import javax.crypto.Cipher;
//import javax.crypto.spec.IvParameterSpec;
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class ApplicationFilter extends OncePerRequestFilter {
//
//	@Autowired
//	JdbcTemplate jdbcTemplate;
//	
//	@Value("${jwttwo.consecutiverequest.allowtime}")
//	private long SESSION_TIMEOUT;
//	
//	@Value("${jwt.token.expirationtime}")
//	private long jwtExpTimeInHours;
//	
//	@Value("${jwt.token.RESencryprtion.key}")
//	private String ResEncryptionKey;
//	
//	@Value("${jwt.secret.key}")
//	private String SECRET_KEY;
//	
//	@Value("${jwt.security.isenabled}")
//	private boolean isSecurityEnabled;
//	
//	private Map<String, String> getParamsMap(HttpServletRequest request) {
//		Map<String, String> typesafeRequestMap = new HashMap<String, String>();
//		Enumeration<?> requestParamNames = request.getParameterNames();
//		while (requestParamNames.hasMoreElements()) {
//			String requestParamName = (String) requestParamNames.nextElement();
//			String requestParamValue = request.getParameter(requestParamName);
//			typesafeRequestMap.put(requestParamName, requestParamValue);
//		}
//		return typesafeRequestMap;
//	}
//	
//	@Override
//	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//		return false;
////		return (request.getRequestURI().endsWith("/validateUserAndGenerateOtp"));		
//	}
//
//	@Override
//	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//			throws ServletException, IOException {
//
//		log.debug("***************************************");
//		log.debug(request.getMethod() + " : " + request.getRequestURL().toString());
//		log.debug(getParamsMap(request).toString());
//		log.debug("---------------------------------------");
//		
//		long start = System.nanoTime();
//
//		if (request.getRequestURI().endsWith("/getAppVersion")) {
//
//			Map<String, String> paramsMap = getParamsMap(request);
//			String sessionId = paramsMap.get("sessionId");
//
//			log.info(" sessionId : " + sessionId);
//
//			String token = generateToken(sessionId);
//
//			String appendedText = randomGeneratedText(10);
//			String finalToken = token + appendedText;
//			String EncryptedToken = null;
//			try {
//				EncryptedToken = encrypt(finalToken, ResEncryptionKey);
//				log.info(" EncryptedToken : " + EncryptedToken);
//			} catch (Exception e) {
//				log.error(" Error occured while encrypting token : " + e.getMessage());
//				e.printStackTrace();
//			}
//			log.info("EncryptedToken : " + EncryptedToken);
//			String publicKey = generateEncryptedSecretKeyString();
//
//			log.info("publicKey : " + publicKey);
//
//			Calendar cal = Calendar.getInstance();
//			java.sql.Timestamp created_date = new Timestamp(cal.getTimeInMillis());
//			java.sql.Timestamp updated_date = new Timestamp(cal.getTimeInMillis());
//
//			try {
//				/* int affectedRows = */ jdbcTemplate.update( SQLConstants.INSERT_TOKEN_DETAILS_IN_DB, 
//						sessionId, token, created_date, updated_date );
//
//			} catch (DataAccessException e) {
//				e.printStackTrace();
//				log.error("Error occured when fetching data for token : " + e.getMessage());
//			}
//			response.setHeader("Authorization", EncryptedToken);
//			response.setHeader("publicKey", generateEncryptedSecretKeyString());
//
//			filterChain.doFilter(request, response);
//			long end = System.nanoTime();
//			log.debug("---------------------------------------");
//			log.debug(String.format("Service execution time (sec): %.3f", ((end - start) / 1000000000.0)));
//			log.debug("***************************************");
//			return;
//		} else if (
//				request.getRequestURI().endsWith("/getAvailableAllActiveJobs")  ||
//				request.getRequestURI().endsWith("/submitJobApplication")  ||
//				request.getRequestURI().endsWith("/contactUs")  ||
//				request.getRequestURI().endsWith("/getJobDetailsByJobId")  ||
//				request.getRequestURI().endsWith("/sortAllAvailableJobsByInput")  ||
//				request.getRequestURI().endsWith("/getAvailableAllPublishedBlogs")  ||
//				request.getRequestURI().endsWith("/getAvailableAllPublishedCaseStudy")  ||
//				request.getRequestURI().endsWith("/getAvailableAllPublishedNews") ||
//				request.getRequestURI().endsWith("/getNewsyDetailsByNewsId") ||
//				request.getRequestURI().endsWith("/getCaseStudyDetailsByCaseId") ||
//				request.getRequestURI().endsWith("/getBlogDetailsByBlogId") 
//
//				) 
//		{
//
//			if ( isSecurityEnabled ) {
//				String encryptedSessionId = request.getHeader("sessionID");
//				
//				String sessionId = "";
//				try {
//					sessionId = decrypt(encryptedSessionId, ResEncryptionKey);
//				} catch (Exception e) {
//					e.printStackTrace();
//				};
//	
//				String token = request.getHeader("Authorization");
//		    	
//		    	String adjustedToken = token.substring(7);
//		    	String decryptedToken ="";
//		    	String tokenWithoutLastTenChar ="";
//		    	
//				try {
//					decryptedToken = decrypt(adjustedToken, ResEncryptionKey);
//					tokenWithoutLastTenChar = decryptedToken.substring(0,decryptedToken.length() -10);
//				} catch (Exception e) {
//					log.error("Error occured while decrypting token : "+e.getMessage());
//					e.printStackTrace();
//				}
//	
//				log.debug("Token                   : " + token);
//				log.debug("AdjustedToken           : " + adjustedToken);
//				log.debug("DecryptedToken          : " + decryptedToken);
//				log.debug("TokenWithoutLastTenChar : " + tokenWithoutLastTenChar);
//				
//				try {
//		    		boolean isTokenValid = validateTokenFromDbByDeviceId( tokenWithoutLastTenChar, sessionId );
//		    		log.info("isTokenValid : "+isTokenValid);
//		    		LocalDateTime currentTime = LocalDateTime.now();
//		    		log.info("currentTime : "+currentTime);
//		    		
//		    		if (isTokenValid) {
//		    			Timestamp lastUpdatedTime = null;
//		    			try {
//		    				lastUpdatedTime = getLastUpdatdTime( tokenWithoutLastTenChar);
//		    				log.info("response of userrepository :"+lastUpdatedTime);
//		    			} catch (Exception e) {
//		    				e.printStackTrace();
//		    				log.error("Error : "+e.getMessage());
//		    			}
//		    			log.info("lastUpdatedTime : "+lastUpdatedTime);  			
//		    			
//		    			Timestamp currentTym = new Timestamp(System.currentTimeMillis());
//		    			
//		    			long millisecondsDifference = currentTym.getTime() - lastUpdatedTime.getTime();
//		    			long secondsDifference = millisecondsDifference / 1000;
//		    			
//		    			log.info("Difference between two api hits in seconds : " + secondsDifference);
//		    			
//						if (secondsDifference > SESSION_TIMEOUT) {							
//			                response.sendError(419, "Session is Expired");
//			                log.error("Session is Expired : ");
//							return;
//						}
//						
//						Calendar cal = Calendar.getInstance();
//						java.sql.Timestamp updated_date = new Timestamp(cal.getTimeInMillis());
//						
//						/* int affectedRows = */ jdbcTemplate.update(SQLConstants.UPDATE_JWT_TOKEN_FOR_TIME_DETAILS, 
//								updated_date, tokenWithoutLastTenChar);
//	
//						String tokenWithappendedText = tokenWithoutLastTenChar + randomGeneratedText(10);
//	
//						String EncryptedToken = encrypt(tokenWithappendedText,ResEncryptionKey);
//						
//						response.setHeader("publicKey", generateEncryptedSecretKeyString());
//						response.setHeader("Authorization", EncryptedToken);
//						
//						filterChain.doFilter(request, response);
//			
//				        long end = System.nanoTime();
//						log.debug("---------------------------------------");
//						log.debug(String.format("Service execution time (sec): %.3f", ((end - start) / 1000000000.0)));
//						log.debug("***************************************");					
//		    		} else {
//		    			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");	    			
//				        long end = System.nanoTime();
//						log.debug("---------------------------------------");
//						log.debug(String.format("Service execution time (sec): %.3f", ((end - start) / 1000000000.0)));
//						log.debug("***************************************");		
//		    		}
//		    	} catch (Exception e) {
//		    		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
//		    		e.printStackTrace();
//		    		log.error("Error occured : "+e.getMessage());
//	                return;
//		    	}
//			} else {
//				filterChain.doFilter(request, response);	
//				long end = System.nanoTime();
//				log.debug("---------------------------------------");
//				log.debug(String.format("Service execution time (sec): %.3f", ((end - start) / 1000000000.0)));
//				log.debug("***************************************");
//				return;
//			}
//		} else {
//			filterChain.doFilter(request, response);	
//			long end = System.nanoTime();
//			log.debug("---------------------------------------");
//			log.debug(String.format("Service execution time (sec): %.3f", ((end - start) / 1000000000.0)));
//			log.debug("***************************************");
//			return;
//		}
//	}
//	
//	private String generateToken(String subject) {
//
//		Map<String, Object> claims = new HashMap<>();
//
//		long jwtTokenExpirationTime = System.currentTimeMillis() + (jwtExpTimeInHours * 60 * 60 * 1000);
//
//		return Jwts.builder().
//				setHeaderParam("typ", "JWT").
//				setIssuer("Mgstech").
//				setClaims(claims).
//				setSubject(subject).
//				setIssuedAt(new Date()).
//				setExpiration(new Date(jwtTokenExpirationTime)).
//				signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
//	}
//	
//	public String randomGeneratedText(int num) {
//		String randomText;
//		char[] charArray = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
//				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
//				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
//				'8', '9' };
//
//		int arraylength = charArray.length;
//
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < num; i++) {
//			Random random = new Random();
//			int randomNumber = random.nextInt(arraylength);
//
//			sb = sb.append(charArray[randomNumber]);
//		}
//		randomText = sb.toString();
//		System.out.println("randomTextGenerated : " + randomText);
//		return randomText;
//	}
//	
//	  private static byte[] hexStringToByteArray(String s) {
//		    int len = s.length();
//		    byte[] data = new byte[len / 2];
//		    for (int i = 0; i < len; i += 2) {
//		      data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
//		          + Character.digit(s.charAt(i + 1), 16));
//		    }
//		    return data;
//		  }
//	
//	public String encrypt(String plaintext, String ResEncryptionKey) throws Exception {
//		
//	    byte[] keyBytes = hexStringToByteArray(ResEncryptionKey);
//	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
//
//	    String ivString = ResEncryptionKey.substring(0, 16);
//	    
//	    IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
//
//	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//	    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
//	    byte[] encrypted = cipher.doFinal(plaintext.getBytes());
//	    return Base64.getEncoder().encodeToString(encrypted);
//		
//	}
//	
//	public String decrypt(String ciphertext, String ResEncryptionKey) throws Exception {
//
//	    byte[] keyBytes = hexStringToByteArray(ResEncryptionKey);
//	    SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
//
//	    String ivString = ResEncryptionKey.substring(0, 16);
//	    IvParameterSpec iv = new IvParameterSpec(ivString.getBytes());
//
//	    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//	    cipher.init(Cipher.DECRYPT_MODE, key, iv);
//	    byte[] decodedCiphertext = Base64.getDecoder().decode(ciphertext);
//	    byte[] decrypted = cipher.doFinal(decodedCiphertext);
//	    return new String(decrypted);
//		
//	}
//	
//	public String generateEncryptedSecretKeyString() {
//
//		String plainText = ResEncryptionKey;
//		
//		log.info("ResEncryptionKey plain key : "+ResEncryptionKey);
//
//		String firstEncodingbase64String = encodeToBase64(plainText);
//
//		StringBuilder sb1 = new StringBuilder(firstEncodingbase64String);
//		String secondappendedbase64String = sb1.append(randomGeneratedText(5)).toString();
//		String secondEncodingbase64String = encodeToBase64(secondappendedbase64String);
//		
//		StringBuilder sb2 = new StringBuilder(secondEncodingbase64String);
//		String thirdappendedbase64String = sb2.append(randomGeneratedText(4)).toString();
//		String thirdEncodingbase64String = encodeToBase64(thirdappendedbase64String);
//
//		StringBuilder sb3 = new StringBuilder(thirdEncodingbase64String);
//		String fourthappendedbase64String = sb3.append(randomGeneratedText(6)).toString();
//		String fourthEncodingbase64String = encodeToBase64(fourthappendedbase64String);
//
//		StringBuilder sb4 = new StringBuilder(fourthEncodingbase64String);
//		String fifthappendedbase64String = sb4.append(randomGeneratedText(5)).toString();
//		String fifthEncodingbase64String = encodeToBase64(fifthappendedbase64String);
//		
//		log.info("ResEncryptionKey Encoded final key : "+fifthEncodingbase64String);
//		
//		return fifthEncodingbase64String;
//	}
//	
//	public String removeLastCharacters(String str, int n) {
//		return str.substring(0, str.length() - n);
//	}
//
//	public String encodeToBase64(String plainText) {
//		byte[] encodedBytes = Base64.getEncoder().encode(plainText.getBytes());
//		return new String(encodedBytes);
//	}
//
//	public String decodeFromBase64(String base64String) {
//		byte[] decodedBytes = Base64.getDecoder().decode(base64String);
//		return new String(decodedBytes);
//	}
//	
//	
//	private boolean validateTokenFromDbByDeviceId( String tokenWithoutLastTenChar, String sessionId ) {
//		try {
//
//			Integer count = jdbcTemplate.queryForObject(SQLConstants.GET_TOKEN_DETAILS_BY_SESSION_ID_AND_TOKEN, 
//					Integer.class, sessionId, tokenWithoutLastTenChar );
//			
//			log.info("validateTokenFromDatabaseByDeviceId is token found in DB count : " + count);
//			
//			boolean isTokenValid = (count == 1) ? true : false;
//			return isTokenValid;
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//			log.error("Error occured while fetching the token details : "+e.getMessage());
//			return false;
//		}
//	}
//
//	public Timestamp getLastUpdatdTime(String tokenWithoutLastTenChar) {
//		JwtToken tokenDetails = null;
//
//		try {
//			log.info("SQLConstants.GET_TOKEN_DETAILS_BY_TOKEN : " + SQLConstants.GET_TOKEN_DETAILS_BY_TOKEN);
//			log.info("tokenWithoutLastTenChar for MySql querry : " + tokenWithoutLastTenChar);
//
//			tokenDetails = jdbcTemplate.queryForObject(SQLConstants.GET_TOKEN_DETAILS_BY_TOKEN,
//					new BeanPropertyRowMapper<>(JwtToken.class), tokenWithoutLastTenChar);
//			log.info("lastUpdatedTime : " + tokenDetails.getUpdated_time());
//
//		} catch (Exception e) {
//			log.error("Error occured while getting last updated time :"+e.getMessage());
//			e.printStackTrace();
//		}
//		return tokenDetails.getUpdated_time();
//	}	
//
//}
