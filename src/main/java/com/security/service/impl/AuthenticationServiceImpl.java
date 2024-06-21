package com.security.service.impl;

import java.util.HashMap;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
	public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {
		Optional<User> user = userRepository.findByEmail(signUpRequest.getEmail());
		if (!user.isEmpty()){
			return ResponseEntity.ok("Username da ton tai");
		}
		User newUser = new User();

		newUser.setFirstName(signUpRequest.getFirstName());
		newUser.setLastName(signUpRequest.getLastName());
		newUser.setEmail(signUpRequest.getEmail());
		newUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		newUser.setRole(Role.USER);

		return ResponseEntity.ok("Dang nhap thanh cong");
	}

	@Override
	public ResponseEntity<?> signIn(SignInRequest signInRequest) {
		Optional<User> user = userRepository.findByEmail(signInRequest.getEmail());

		if (user.isEmpty()){
			return ResponseEntity.ok("Username khong dung");
		}

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));

		var jwt = jwtService.generateToken(user.get());
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user.get());

		JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		return ResponseEntity.ok(jwtAuthenticationResponse);

	}

}
