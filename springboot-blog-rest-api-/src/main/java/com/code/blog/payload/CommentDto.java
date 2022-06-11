package com.code.blog.payload;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

	private long id;
	@NotEmpty(message = "name should be not null or empty")
	private String name;
	@NotEmpty(message = "email should be not null or empty")
	private String email;
	@NotEmpty
	@Size(min = 10, message = "Comment body must be 10 character")
	private String body;

}
