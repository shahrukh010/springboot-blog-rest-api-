package com.code.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public ResponseEntity<String> home() {

		return new ResponseEntity<String>("<h1 style=text-align:center>Hello SpringBoot</h1>", HttpStatus.OK);
	}
}
