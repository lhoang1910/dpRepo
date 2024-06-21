package com.security.service.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.security.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{
	
	//tao ra jwt tu key
	@Override
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 24))
				.signWith(getSigninkey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	@Override
	public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder().setClaims(extraClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 604800000))
				.signWith(getSigninkey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	
	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
	}
	
	//trich xuat tat ca xac nhan quyen so huu tu token
	@Override
	public Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder().setSigningKey(getSigninkey()).build().parseClaimsJws(token).getBody()	;
	} 
	
	//lay key dang nhap
	@Override
	public Key getSigninkey() {
		// TODO Auto-generated method stub
		byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
		return Keys.hmacShaKeyFor(key);
	}
	
	//trich xuat username tu Token
	@Override
	public String extractUsername (String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	//kiem tra token hop le
	@Override
	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	//kiem tra token het han (so sanh thoi diem het han voi thoi diem hien tai)
	@Override
	public boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}
	
}
