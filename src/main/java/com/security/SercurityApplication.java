package com.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.repository.UserRepository;

@SpringBootApplication
public class SercurityApplication 
//			implements CommandLineRunner
						{

	public static void main(String[] args) {
		SpringApplication.run(SercurityApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

//	public void run(String... args) {
//		User adminAccount = userRepository.findByRole(Role.ADMIN);
//		if (null == adminAccount) {
//			User user = new User();
//
//			user.setFirstName("admin");
//			user.setLastName("admin");
//			user.setEmail("admin@2003.com");
//			user.setRole(Role.ADMIN);
//			user.setPassword(passwordEncoder.encode("admin123456"));
//			userRepository.save(user);
//		}
//	}

}
