package com.security.service;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface JwtService {
	String generateToken(UserDetails userDetails);
	String extractUsername (String token);
	boolean isTokenValid(String token, UserDetails userDetails);
	boolean isTokenExpired(String token);
	<T> T extractClaim(String token, Function<Claims, T> claimsResolvers);
	Claims extractAllClaims(String token);
	Key getSigninkey();
	String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
