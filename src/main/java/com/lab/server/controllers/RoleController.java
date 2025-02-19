package com.lab.server.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.lib.api.PaginationResponse;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.configs.language.MessageSourceHelper;
import com.lab.server.payload.role.RoleRequest;
import com.lab.server.payload.role.RoleResponse;
import com.lab.server.services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

	private final RoleService roleService;
	private final MessageSourceHelper helper;
	
	@Tag(name = "role")
	@Operation(summary = "API get all roles")
	@GetMapping("")
	public ResponseEntity<?> getAllRoles() {
		return ResponseEntity.ok(roleService.findAll());
	}
	
	@Tag(name = "role")
	@Operation(summary = "API get role by id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getRoleById(@PathVariable int id) {
		return ResponseEntity.ok(roleService.findRoleById(id));
	}
	
	@Tag(name = "role")
	@Operation(summary = "API create new role")
	@PostMapping("")
	public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
		return ResponseEntity.ok(roleService.createRole(request));
	}
	
	@Tag(name = "role")
	@Operation(summary = "API update role by id")
	@PutMapping("/{id}")
	public ResponseEntity<?> createRole(@PathVariable int id, @RequestBody RoleRequest request) {
		return ResponseEntity.ok(roleService.updateRoleById(id,request));
	}
	
	@Tag(name = "role")
	@Operation(summary = "API delete role by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> createRole(@PathVariable int id) {
		return ResponseEntity.ok(roleService.deleteRoleById(id));
	}
	
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
