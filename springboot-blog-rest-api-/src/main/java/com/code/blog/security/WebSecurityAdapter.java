package com.code.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // if we apply method level security use this annotation.
public class WebSecurityAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.GET, "/api/**").permitAll().// get request
																										// allowed to
																										// everyone
				anyRequest().authenticated().and().formLogin().and().httpBasic();

	}

//****************************************************************************************************
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		
	// InMemory Authenticaton
//		UserDetails hector = User.builder().username("hector").password(passwordEncoder().encode("annie")).roles("Admin")
//				.build();
//		UserDetails admin = User.builder().username("annie").password(passwordEncoder().encode("hector")).roles("Editor")
//				.build();
//
//		return new InMemoryUserDetailsManager(hector, admin);
//		InMemoryUserDetailsManager userService = new InMemoryUserDetailsManager();
//
//		UserDetails user = User.withUsername("annie").password(passwordEncoder().encode("hector")).roles("rwx").build();
//
//		userService.createUser(user);
//		return userService;
//	}

//****************************************************************************************************

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// database authentication
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());

	}

}
