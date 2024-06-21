package com.security.controller;

import com.security.dto.reponse.LoginResponse;
import com.security.repository.UserRepository;
import com.security.service.UserService;
import com.security.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.security.dto.SignInRequest;
import com.security.dto.SignUpRequest;
import com.security.dto.reponse.JwtAuthenticationResponse;
import com.security.entites.User;
import com.security.service.AuthenticationService;

import javax.swing.text.html.Option;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationService authenticationService;

	@Autowired
	UserRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserServiceImpl service;

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
	}
	
	@PostMapping("/signin")
	public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest){
		Optional<User> loginUser = repository.findByEmail(signInRequest.getEmail());

		if (loginUser.isEmpty()){
			return ResponseEntity.ok(new LoginResponse(false ,"Username khong dung", null));
		} else {
			if (!passwordEncoder.matches(loginUser.get().getPassword(), signInRequest.getPassword())){
				return ResponseEntity.ok(new LoginResponse(false ,"Password khong dung", null));
			} else {
				return ResponseEntity.ok(new LoginResponse(true , String.valueOf(authenticationService.signIn(signInRequest)),
						service.userDetailsService()
								.loadUserByUsername(signInRequest.getEmail())));
			}
		}

	}
}
