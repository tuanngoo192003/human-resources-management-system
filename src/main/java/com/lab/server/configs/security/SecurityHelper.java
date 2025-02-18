package com.lab.server.configs.security;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.lab.server.entities.User;
import com.lab.server.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SecurityHelper {
	
	private final UserService userService;

	public User getCurrentUserLogin() throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            throw new Exception("User not found");
        } else {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            return userService.findByFields(Map.of("username", username));
        }
    }
}
