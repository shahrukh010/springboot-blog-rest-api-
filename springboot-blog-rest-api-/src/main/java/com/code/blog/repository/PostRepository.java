package com.code.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.code.blog.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Long>{

}
