package com.code.blog.payload;

import java.util.List;

import javax.transaction.Transactional;

import lombok.Data;
//dto:->data transfer object

@Data
public class PostDto {

	private long id;
	private String title;
	private String description;
	private String content;
	private List<CommentDto>comments;
}
