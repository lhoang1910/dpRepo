package com.security.dto.reponse;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
	private String token;
	private String refreshToken;

	public JwtAuthenticationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JwtAuthenticationResponse(String token, String refreshToken) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
