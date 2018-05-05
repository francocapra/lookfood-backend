package com.lookfood.backend.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.lookfood.backend.security.SSUserDetails;

public class UserService {
		
	public static SSUserDetails authenticated() {
		try {
			return (SSUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}
