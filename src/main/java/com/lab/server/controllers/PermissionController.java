package com.lab.server.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.payload.permission.PermissionRequest;
import com.lab.server.services.PermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
@Tag(name = "permisison")
@Validated
public class PermissionController {
	private final PermissionService permissionService;
	
	@Operation(summary = "API get all permissions")
	@GetMapping("")
	@PreAuthorize("hasAuthority('READ_PERMISSIONS')")
	public ResponseEntity<?> getAllPermissions(
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_PAGE) int page,
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_SIZE) int perpage, 
			@RequestParam(required = false, defaultValue = StringUtils.EMPTY) String search){
		return ResponseEntity.ok(new ApiResponse<>(true, permissionService.findAll(page,perpage, search)));
	}
	
	@Operation(summary = "API get permisison by id")
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_PERMISSIONS')")
	public ResponseEntity<?> getPermissionById(@PathVariable int id) {
		return ResponseEntity.ok(new ApiResponse<>(true, permissionService.findPermissionById(id)));
	}
	
	@Operation(summary = "API create new permisison")
	@PostMapping("")
	@PreAuthorize("hasAuthority('WRITE_PERMISSIONS')")
	public ResponseEntity<?> createPermission(@RequestBody PermissionRequest request) {
		return ResponseEntity.ok(new ApiResponse<>(true, permissionService.createPermission(request)));
	}
	
	@Operation(summary = "API update permisison by id")
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('WRITE_PERMISSIONS')")
	public ResponseEntity<?> updatePermissionById(@PathVariable int id, @RequestBody PermissionRequest request) {
		return ResponseEntity.ok(new ApiResponse<>(true, permissionService.updatePermissionById(id,request)));
	}
	
	@Operation(summary = "API delete permisison by id")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('DELETE_PERMISSIONS')")
	public ResponseEntity<?> deletePermissionById(@PathVariable int id) {
		return ResponseEntity.ok(new ApiResponse<>(true, permissionService.deletePermissionById(id)));
	}
}
