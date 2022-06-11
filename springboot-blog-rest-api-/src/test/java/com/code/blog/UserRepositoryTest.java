package com.code.blog;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.code.blog.entity.User;
import com.code.blog.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

	@Autowired
	public UserRepository repo;

	@Test
	public void test() {

//		List<User>user = repo.findByEmail("annie.khan@gmail.com");
//		System.out.println(user+":::::::::::::::::");
		Optional<User> user = repo.findByUsername("annie.khan@gmail.com");
		
	}

}
