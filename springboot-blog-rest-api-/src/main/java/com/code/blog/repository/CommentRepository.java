package com.code.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.blog.entity.Comment;

@Repository // not required because of SimpleRespository implements JpaRespository and
			// contain @Repository annotation
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> getByPostId(long postId);
}
