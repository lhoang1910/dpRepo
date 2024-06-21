package com.security.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dto.SignInRequest;
import com.security.dto.SignUpRequest;
import com.security.dto.reponse.JwtAuthenticationResponse;
import com.security.entites.Role;
import com.security.entites.User;
import com.security.repository.UserRepository;
import com.security.service.AuthenticationService;
import com.security.service.JwtService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtService jwtService;

	@Override
	public User signUp(SignUpRequest signUpRequest) {
		User user = new User();

		user.setFirstName(signUpRequest.getFirstName());
		user.setLastName(signUpRequest.getLastName());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(Role.USER);

		return userRepository.save(user);
	}

	@Override
	public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
		// TODO Auto-generated method stub
		var user = userRepository.findByEmail(signInRequest.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

		var jwt = jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		return jwtAuthenticationResponse;	

	}

}
