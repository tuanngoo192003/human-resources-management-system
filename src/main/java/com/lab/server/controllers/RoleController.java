package com.lab.server.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.server.payload.role.RoleRequest;
import com.lab.server.services.RoleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Tag(name = "role")
@Validated
public class RoleController {

	private final RoleService roleService;

	@Operation(summary = "API get all roles")
	@GetMapping("")
	@PreAuthorize("hasAuthority('read_role')")
	public ResponseEntity<?> getAllRoles() {
		return ResponseEntity.ok(new ApiResponse<>(true, roleService.findAll()));
	}

	@Operation(summary = "API get role by id")
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('read_role')")
	public ResponseEntity<?> getRoleById(@PathVariable int id) {
		return ResponseEntity.ok(new ApiResponse<>(true, roleService.findRoleById(id)));
	}

	@Operation(summary = "API create new role")
	@PostMapping("")
	@PreAuthorize("hasAuthority('create_role')")
	public ResponseEntity<?> createRole(@RequestBody RoleRequest request) {
		return ResponseEntity.ok(new ApiResponse<>(true, roleService.createRole(request)));
	}

	@Operation(summary = "API update role by id")
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('update_role')")
	public ResponseEntity<?> updateRoleById(@PathVariable int id, @RequestBody RoleRequest request) {
		return ResponseEntity.ok(new ApiResponse<>(true, roleService.updateRoleById(id, request)));
	}

	@Operation(summary = "API delete role by id")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('delete_role')")
	public ResponseEntity<?> deleteRoleById(@PathVariable int id) {
		return ResponseEntity.ok(new ApiResponse<>(true, roleService.deleteRoleById(id)));
	}

}
