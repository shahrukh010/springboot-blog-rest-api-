package com.code.blog.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {

	private int pageNo;
	private int pageSize;
	private int totalElements;
	private int totoalPages;
	private boolean last;

	private List<PostDto> content;

}
