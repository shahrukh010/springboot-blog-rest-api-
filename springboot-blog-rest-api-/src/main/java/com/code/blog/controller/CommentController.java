package com.code.blog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.blog.payload.CommentDto;
import com.code.blog.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {

	private CommentService commentService;

	public CommentController(CommentService commentService) {
		this.commentService = commentService;
	}

	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto commentDto,
			@PathVariable(name = "postId") long postId) {
		System.out.println(commentDto);

		return new ResponseEntity<CommentDto>(commentService.createComment(commentDto, postId), HttpStatus.CREATED);
	}

	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentsById(@PathVariable(name = "postId") long postId) {

		return commentService.getCommentByPostId(postId);

	}

	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable("postId") long postId,
			@PathVariable("commentId") long commentId) {

		return new ResponseEntity<CommentDto>(commentService.getCommentById(postId, commentId), HttpStatus.OK);

	}

	@PutMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> updateComments(@PathVariable(value = "postId") long postId,
			@PathVariable(value = "commentId") long commentId, @Valid @RequestBody CommentDto commentRequest) {

		return new ResponseEntity<CommentDto>(commentService.updateComment(commentRequest,postId,commentId),HttpStatus.OK);
	}

	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
			@PathVariable("commentId") long commentId) {

		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<String>("comment is delete successfully.", HttpStatus.OK);
	}

}
