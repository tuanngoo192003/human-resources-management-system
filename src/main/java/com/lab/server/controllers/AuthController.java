package com.lab.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.server.payload.auth.LoginRequest;
import com.lab.server.payload.auth.LoginResponse;
import com.lab.server.services.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;

	@PostMapping("/login")
	public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
		return new ApiResponse<LoginResponse>(true, authService.login(loginRequest));
	}
	
	@GetMapping("/secured-endpoint")
	public ResponseEntity<String> securedEndpoint() {
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    log.info("Current user: {}", authentication);
	    return ResponseEntity.ok("Success!");
	}
}
