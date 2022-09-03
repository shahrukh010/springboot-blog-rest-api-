package com.code.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.blog.entity.Role;
import com.code.blog.entity.User;
import com.code.blog.payload.LoginDto;
import com.code.blog.payload.SignUpDto;
import com.code.blog.repository.RoleRepository;
import com.code.blog.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/signIn")
	public ResponseEntity<String> authentication(@RequestBody LoginDto loginDto) {

		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(auth);
		return new ResponseEntity<String>("user sign-in successfully", HttpStatus.CREATED);
	}

	@PostMapping("/signUp")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {

		// check for username exists in DB
		if (userRepository.existsByUsername(signUpDto.getUsername())) {

			return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
		}
		// check for email exists is DB
		if (userRepository.existsByEmail(signUpDto.getEmail())) {

			return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
		}

		// create new user

		User user = new User();

		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		Role role = roleRepository.findByName("ROLE_ADMIN");
		user.setRoles(Collections.singleton(role));

		userRepository.save(user);

		return new ResponseEntity<>("User register successfully", HttpStatus.OK);
	}
}
