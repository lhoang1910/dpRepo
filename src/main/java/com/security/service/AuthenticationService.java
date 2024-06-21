package com.security.service;

import com.security.dto.SignInRequest;
import com.security.dto.SignUpRequest;
import com.security.dto.reponse.JwtAuthenticationResponse;
import com.security.entites.User;

public interface AuthenticationService {
	User signUp(SignUpRequest signUpRequest);
	
	JwtAuthenticationResponse signIn(SignInRequest signInRequest);
}
