package com.code.blog.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.code.blog.payload.PostDto;
import com.code.blog.payload.PostResponse;

@Service
public interface PostService {

	public PostDto createPost(PostDto postDto);

	public List<PostDto> getAllPost(long pageNo, long pageSize);

	public PostDto getPostById(int id);

	public PostDto updatePostById(PostDto postDto, long id);

	public void deletePost(long id);

//	public PostResponse getAllPosts(int pageNO, int pageSize);

	public PostResponse getAllPosts(long pageNO, long pageSize, String sortBy,String sortDir);

}
