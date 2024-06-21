package com.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.repository.UserRepository;
import com.security.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService{
	@Autowired
    private UserRepository userRepository;

	@Override
	public UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		return new org.springframework.security.core.userdetails.UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				return userRepository.findByEmail(username)
						.orElseThrow(()-> new  UsernameNotFoundException("Username not found"));
			}
		};
	}
	
	
}
