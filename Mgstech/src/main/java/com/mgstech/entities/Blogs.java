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
public class Blogs {
	
	private Long blog_id;
	private String blog_title;
	private String blog_by;
	private String blog_description;
	private String status;
	private byte[] blog_image;
	private Date submission_date;
	private Date published_date;
	private String base64Image;

}
