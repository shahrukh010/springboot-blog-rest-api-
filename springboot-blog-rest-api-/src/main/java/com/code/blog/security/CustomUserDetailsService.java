package com.code.blog.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.code.blog.entity.Role;
import com.code.blog.entity.User;
import com.code.blog.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository repository;
	

	public CustomUserDetailsService(UserRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = repository.findByUsernameOrEmail(username, username)
				.orElseThrow(() -> new UsernameNotFoundException("username not found"));

		//System.out.println(user + "::::::::::::");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRoleToAuthority(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRoleToAuthority(Set<Role> roles) {

		return roles.stream().map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

	}


}
