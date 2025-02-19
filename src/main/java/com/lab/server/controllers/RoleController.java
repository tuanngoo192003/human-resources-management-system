package com.lab.server.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.lib.api.PaginationResponse;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.configs.language.MessageSourceHelper;
import com.lab.server.payload.role.RoleResponse;
import com.lab.server.services.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/role")
public class RoleController {

	private final RoleService roleService;
	private final MessageSourceHelper helper;
	
	@PostMapping("/mock")
	public ApiResponse<String> mockApi(@RequestParam("id") int id) throws Exception {
		String rep = helper.getMessage("success.saved");
		log.error("Test");
		return new ApiResponse<String>(true, rep, rep);
	}
	
	@GetMapping("/pagination/mock")
	public PaginationResponse<RoleResponse> mockApi(
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_PAGE) int page,
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_SIZE) int perpage, 
			@RequestParam(required = false, defaultValue = StringUtils.EMPTY) String search){
		return roleService.findRoleWithConditions(page, perpage, search);
	}
}
