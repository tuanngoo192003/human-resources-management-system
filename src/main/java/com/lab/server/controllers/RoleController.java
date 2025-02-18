package com.lab.server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.server.services.RoleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

	private final RoleService roleService;
	
	@PostMapping("/mock")
	public ApiResponse<String> mockApi(@RequestParam("id") int id) throws Exception {
		String rep = "ahihi";
		return new ApiResponse<String>(true, rep, rep);
	}
}
