package com.code.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.code.blog.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	List<Role> findByName(String name);
}

