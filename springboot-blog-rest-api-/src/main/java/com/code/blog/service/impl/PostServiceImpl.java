package com.code.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.code.blog.entity.Post;
import com.code.blog.exception.ResourceNotFoundException;
import com.code.blog.payload.PostResponse;
import com.code.blog.payload.PostDto;
import com.code.blog.repository.PostRepository;
import com.code.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepo;
	private ModelMapper mapper;

	@Autowired // if only one constructor is available and required object then not required to
				// Autowired it automatically does
	public PostServiceImpl(PostRepository postRepo,ModelMapper mapper) {
		this.postRepo = postRepo;
		this.mapper = mapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {

		Post newPost = mapToEntity(postDto);
		PostDto dto = mapToPostDTO(newPost);
		return dto;
	}

	// convert dto to entity
	private Post mapToEntity(PostDto postDto) {

		Post post = mapper.map(postDto, Post.class);
//		Post post = new Post();
//		post.setTitle(postDto.getTitle());
//		post.setDescription(postDto.getDescription());
//		post.setContent(postDto.getContent());

		Post newPost = postRepo.save(post);
		return newPost;
	}

	// convert entity to dto
	private PostDto mapToPostDTO(Post newPost) {

		PostDto dto = mapper.map(newPost, PostDto.class);
//		PostDto dto = new PostDto();
//		dto.setId(newPost.getId());
//		dto.setTitle(newPost.getTitle());
//		dto.setDescription(newPost.getDescription());
//		dto.setContent(newPost.getContent());
		return dto;
	}

	@Override
	public List<PostDto> getAllPost(long pageNo, long pageSize) {

		Pageable pageable = PageRequest.of((int)pageNo, (int)pageSize);
		Page<Post> posts = postRepo.findAll(pageable);
		List<Post> listPosts = posts.getContent();
//		listPosts.forEach(u -> System.out.println(u));

		return listPosts.stream().map(post -> mapToPostDTO(post)).collect(Collectors.toList());

//		List<Post> post = postRepo.findAll();
//		List<PostDTO> dtoPost = new ArrayList<>();

//****************************************************************************************************
		// converting post list to dto and again convert dto to list<PostDTO>
		// wihout using stream jdk8
//		post.forEach(posts -> dtoPost.add(mapToPostDTO(posts)));

//****************************************************************************************************
		// converting post list to dto and again convert dto to list<PostDTO>
		// using stream jdk8
//		return post.stream().map(posts -> mapToPostDTO(posts)).collect(Collectors.toList());

//		return dtoPost;

	}

	@Override
	public PostDto getPostById(int id) {

//		Post posts = postRepo.findById(id).get();
//		return mapToPostDTO(posts);

		Post posts = postRepo.findById((long) id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

		return mapToPostDTO(posts);
	}

	@Override
	public PostDto updatePostById(PostDto postDto, long id) {

		// Post post = postRepo.findById(id).get();
		Post post = postRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());

		Post posts = postRepo.save(post);

		return mapToPostDTO(posts);
	}

	@Override
	public void deletePost(long id) {

		Post post = postRepo.findById((long) id).orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
		postRepo.delete(post);

	}

	@Override
	public PostResponse getAllPosts(long pageNO, long pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		// Pageable pageable = PageRequest.of(pageNO, pageSize);
		Pageable pageable = PageRequest.of((int)pageNO, (int)pageSize, sort);
		Page<Post> posts = postRepo.findAll(pageable);
		List<Post> listPosts = posts.getContent();
		List<PostDto> listDto = listPosts.stream().map(pos -> mapToPostDTO(pos)).collect(Collectors.toList());

		PostResponse pageResponse = new PostResponse();
		pageResponse.setContent(listDto);
		pageResponse.setPageNo(posts.getNumber());
		pageResponse.setPageSize(posts.getSize());
		pageResponse.setTotalElements((int) posts.getTotalElements());
		pageResponse.setTotoalPages(posts.getTotalPages());
		pageResponse.setLast(posts.isLast());
		return pageResponse;
	}

}
