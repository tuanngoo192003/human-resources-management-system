package com.lab.server.controllers;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.server.entities.User;
import com.lab.server.payload.LoginRequest;
import com.lab.server.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	
	private final UserService userService;

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		System.out.println(loginRequest.getUsername());
		User user = userService.findByFields(Map.of("username", loginRequest.getUsername()));
		return user.getEmail();
	}
}
