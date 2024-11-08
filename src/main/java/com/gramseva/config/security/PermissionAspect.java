package com.gramseva.config.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class PermissionAspect {

//	@Autowired
//	private IPermissionService permissionService;
//
//	@Autowired
//	private JwtUtils jwtUtils;

	@Before("@annotation(checkPermission)")
	public void checkUserPermission(CheckPermission checkPermission) {

		// Get the current HTTP request

		// Here you would retrieve the JWT token and parse it to get user permissions
		// Let's assume you have a method to extract JWT and check permissions

		if (!hasPermission(checkPermission.permission())) {

		}
	}

	private boolean hasPermission(String permission) {
		// Assuming you have stored the user's permissions as part of their JWT claims
		// Extract the user's permissions from the JWT token and check for the required
		// one.
		// Here you can implement the logic based on your security context.
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes();
		if (requestAttributes == null) {
			throw new RuntimeException("No HTTP request found");
		}

		HttpServletRequest request = requestAttributes.getRequest();

		return false;
	}
}
