package com.code.blog.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.code.blog.payload.PostDto;
import com.code.blog.payload.PostResponse;
import com.code.blog.service.PostService;
import com.code.blog.utils.Application;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	private PostService postService;

//	@Autowired //not required if only one constructor are there 
	public PostController(PostService postService) {
		this.postService = postService;
	}

	@PreAuthorize("hasAnyRole('Editor','Admin')") // only rwx role user can
	// createPost
//	@PreAuthorize("hasRole('rwx')") // only rwx role user can createPost
	@PostMapping
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {

		return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);

	}

//	@GetMapping
//	public List<PostDTO> getAllPost(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
//			@RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize)
//			
//
//		return postService.getAllPost(pageNo, pageSize);
//	}

	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE) // by default response format is json if we want otherformat
															// just use jackson format dependenceis
	public PostResponse getAllPosts(
			@RequestParam(value = "pageNo", defaultValue = Application.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize", defaultValue = Application.DEFAULT_PAGE_SIZE, required = false) int pageSize,
			@RequestParam(value = "sortBy", defaultValue = Application.DEFAULT_SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = Application.DEFAULT_SORT_DIRECTION, required = false) String sortDir) {
		// instead of id you can pass title,description it will sort based on passed
		// value

		return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);

	}

	@GetMapping("/{id}")
	public ResponseEntity<PostDto> getPostById(@PathVariable(name = "id") int id) {

		return new ResponseEntity<PostDto>(postService.getPostById(id), HttpStatus.FOUND);

	}

	@PreAuthorize("hasRole('Editor')") // only rwx role can update post
	@PutMapping("/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable(name = "id") int id) {

		return new ResponseEntity<PostDto>(postService.updatePostById(postDto, id), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('Admin')") // only rwx role can delete post
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePostById(@PathVariable(name = "id") int id) {

		postService.deletePost(id);

		return new ResponseEntity<String>("Post entity deleted successfully:", HttpStatus.OK);
	}

}
