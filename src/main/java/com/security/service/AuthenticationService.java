package com.security.service;

import com.security.dto.SignInRequest;
import com.security.dto.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
	ResponseEntity<?> signUp(SignUpRequest signUpRequest);
	
	String signIn(SignInRequest signInRequest);
}
