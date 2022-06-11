package com.code.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.code.blog.entity.Comment;
import com.code.blog.entity.Post;
import com.code.blog.exception.BlogApiException;
import com.code.blog.exception.ResourceNotFoundException;
import com.code.blog.payload.CommentDto;
import com.code.blog.repository.CommentRepository;
import com.code.blog.repository.PostRepository;
import com.code.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private PostRepository postRepository;
	private ModelMapper mapper;

	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {

		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, long postId) {
		
		Comment comment = mapToEntity(commentDto);

		Post post = postRepository.findById(postId).get();
		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);
		return mapToCommentDto(newComment);
	}

	private CommentDto mapToCommentDto(Comment comment) {

		CommentDto commentDto = mapper.map(comment, CommentDto.class);
//		CommentDto commentDto = new CommentDto();
//		commentDto.setId(comment.getId());
//		commentDto.setName(comment.getName());
//		commentDto.setEmail(comment.getEmail());
//		commentDto.setBody(comment.getBody());

		return commentDto;
	}

	private Comment mapToEntity(CommentDto commentDto) {

		Comment comment = mapper.map(commentDto, Comment.class);
//		Comment comment = new Comment();
//		comment.setName(commentDto.getName());
//		comment.setEmail(commentDto.getEmail());
//		comment.setBody(commentDto.getBody());

		return comment;
	}

	@Override
	public List<CommentDto> getCommentByPostId(long postId) {

		List<Comment> listComment = commentRepository.getByPostId(postId);
		return listComment.stream().map(comment -> mapToCommentDto(comment)).collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
//		Post post = postRepository.findById(postId)
//				.orElseThrow(() -> new ResourceNotFoundException("post", "id", commentId));
//		Comment comment = commentRepository.findById(commentId)
//				.orElseThrow(() -> new ResourceNotFoundException("post", "id", commentId));
//
//		if (!comment.getPost().getId().equals(post.getId())) {
//
//			throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
//		}

//		return mapToCommentDto(comment);
		return isExists(postId, commentId);

	}

	private CommentDto isExists(long postId, long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", commentId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment id not belong to this post");
		}
		return mapToCommentDto(comment);
	}

	@Override
	public CommentDto updateComment(CommentDto commentRequest, long postId, long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", commentId));

		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "post", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment id not belong to post");
		}

		comment.setName(commentRequest.getName());
		comment.setEmail(commentRequest.getEmail());
		comment.setBody(commentRequest.getBody());

		Comment updateComment = commentRepository.save(comment);

		return mapToCommentDto(updateComment);

	}

	@Override
	public void deleteComment(long postId, long commentId) {

		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "id", commentId));

		if (!comment.getPost().getId().equals(post.getId())) {

			throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment id not belong to this post");
		}

		commentRepository.deleteById(commentId);
	}

}
