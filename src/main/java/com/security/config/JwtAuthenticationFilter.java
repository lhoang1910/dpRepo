package com.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.security.service.JwtService;
import com.security.service.UserService;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authenHeader = request.getHeader("Authorization");
		final String jwt;
		final String userEmail;

		// kiem tra header phai bat dau bang bearer, neu khong thi tiep tuc voi filter tiep theo
		if (StringUtils.isEmpty(authenHeader)
				|| !org.apache.commons.lang3.StringUtils.startsWith(authenHeader, "Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		//lay token tu email nguoi dung
		jwt = authenHeader.substring(7);
		userEmail = jwtService.extractUsername(jwt);

		// kiem tra useremail co rong khong va da duoc dang nhap chua
		if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);
			//kiem tra tinh hop le cua nguoi dung, neu hop le thi tao UsernamePasswordAuthenticationToken, va cap nhat SecurityContext
			if (jwtService.isTokenValid(jwt, userDetails)) {
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null,
						userDetails.getAuthorities());
				token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				securityContext.setAuthentication(token);
				SecurityContextHolder.setContext(securityContext);
			}
		}
		
		filterChain.doFilter(request, response);
	}

}
