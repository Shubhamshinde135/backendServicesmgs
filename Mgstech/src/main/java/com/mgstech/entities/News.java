package com.mgstech.entities;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
@NoArgsConstructor
public class News {

	private Long news_id;
	private String news_title;
	private String news_type;
	private String status;
	private byte[] news_media;
	private Date submission_date;
	private Date published_date;
	private String base64Media;
	
}
