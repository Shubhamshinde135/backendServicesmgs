package com.mgstech.util;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceResponseLogger {

	@Value("${max.service.resultprintlines}")
	private int maxRsultLines;

	public void logResponse(HashMap<String, Object> response) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());

			String jsonResponse = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response);
			String[] lines = jsonResponse.split("\r\n|\r|\n");

			int maxLines = maxRsultLines;
			int trimmedLineCount = Math.min(lines.length, maxLines);
			StringBuilder trimmedJson = new StringBuilder();
			for (int i = 0; i < trimmedLineCount; i++) {
				trimmedJson.append(lines[i]).append(System.lineSeparator());
			}
			boolean truncated = lines.length > maxLines;

			if (truncated) {
				log.debug(trimmedJson + "\n" + " ... (truncated to " + maxLines + " lines)");
			} else {
				log.debug(trimmedJson.toString());
			}
		} catch (JsonProcessingException e) {
			log.error("Error converting response HashMap to JSON: {}" + e.getMessage());
		}
	}
}
