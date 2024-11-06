package com.mgstech.messagingUtil;

import java.io.IOException;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SendMailWithAttachment {

	@Value("${imap.sendmail.clientid}")
	private String clientId;

	@Value("${imap.sendmail.clientsecret}")
	private String clientSecret;

	@Value("${imap.sendmail.tenantid}")
	private String tenantId;

	@Value("${smtp.mail.username}")
	private String username;

	private static final String AUTHORITY = "https://login.microsoftonline.com/%s/oauth2/v2.0/token";
	private static final String GRANT_TYPE = "client_credentials";
	private static final String SCOPE = "https://graph.microsoft.com/.default";

	private String accessToken;

	public boolean sendEmail(String recipientEmail, String subject, String body, String attachment, String attachmentType,
			String resumeName) {
		try {
			accessToken = getAccessToken();
			String jsonBody = createEmailJson(recipientEmail, subject, body, attachment, attachmentType, resumeName);
			sendEmailRequest(jsonBody);
			log.debug("Email sent successfully to " + recipientEmail);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error sending email: "+ e.getMessage());
			return false;
		}
	}

	private String getAccessToken() throws IOException {
		String authority = String.format(AUTHORITY, tenantId);
		String encodedCredentials = Base64.getEncoder().encodeToString((clientId + ":" + clientSecret).getBytes());

		URL url = new URL(authority);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "Basic " + encodedCredentials);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		Map<String, String> params = new HashMap<>();
		params.put("grant_type", GRANT_TYPE);
		params.put("scope", SCOPE);
		String postData = getPostData(params);

		conn.setDoOutput(true);
		conn.getOutputStream().write(postData.getBytes());

		String response = readResponse(conn);
		return extractAccessToken(response);
	}

	private String createEmailJson(String recipientEmail, String subject, String body, String attachment,
			String attachmentType, String resumeName) {

		String attachmentName = resumeName;

		String escapedBody = body.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n");

		return "{ \"message\": { " + "\"subject\": \"" + subject + "\", "
				+ "\"body\": { \"contentType\": \"HTML\", \"content\": \"" + escapedBody + "\" }, "
				+ "\"toRecipients\": [{ \"emailAddress\": { \"address\": \"" + recipientEmail + "\" } }], "
				+ "\"attachments\": [{ " + "\"@odata.type\": \"#microsoft.graph.fileAttachment\", " + "\"name\": \""
				+ attachmentName + "\", " + "\"contentBytes\": \"" + attachment + "\" " + "}]" + "} }";

	}

	private void sendEmailRequest(String jsonBody) throws IOException {
		String url = "https://graph.microsoft.com/v1.0/users/" + username + "/sendMail";

		HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Bearer " + accessToken);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setDoOutput(true);

		connection.getOutputStream().write(jsonBody.getBytes());

		int responseCode = connection.getResponseCode();

		if (responseCode != HttpURLConnection.HTTP_OK && responseCode != HttpURLConnection.HTTP_ACCEPTED) {
			StringBuilder response = new StringBuilder();

			InputStream errorStream = connection.getErrorStream();
			if (errorStream != null) {
				try (Scanner scanner = new Scanner(errorStream)) {
					while (scanner.hasNextLine()) {
						response.append(scanner.nextLine());
					}
				}
			} else {
				response.append("No error stream available.");
			}

			throw new IOException("Failed to send email: " + responseCode + " Response: " + response.toString());
		}

		if (responseCode == HttpURLConnection.HTTP_ACCEPTED) {
			log.debug("Email request accepted for processing.");
		} else {
			log.debug("Email sent successfully.");
		}
	}

	private String readResponse(HttpURLConnection conn) throws IOException {
		StringBuilder response = new StringBuilder();
		try (Scanner scanner = new Scanner(conn.getInputStream())) {
			while (scanner.hasNextLine()) {
				response.append(scanner.nextLine());
			}
		}
		return response.toString();
	}

	private String extractAccessToken(String response) {
		return response.substring(response.indexOf("\"access_token\":\"") + 16).split("\"")[0];
	}

	private static String getPostData(Map<String, String> params) throws IOException {
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, String> param : params.entrySet()) {
			if (postData.length() != 0) {
				postData.append('&');
			}
			postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		return postData.toString();
	}

}
