package com.lab.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.server.payload.rolepermission.RolePermissionRequest;
import com.lab.server.services.RolePermissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("")
@Tag(name = "role-permission")
@Validated
public class RolePermissionController {
	private final RolePermissionService rolePermissionService;
	
	@Operation(summary = "API get all role-permissions")
	@GetMapping("/role-permissions")
	@PreAuthorize("hasAuthority('read_role_permission')")
	public ResponseEntity<?> getAllRolePermissions() {
		return ResponseEntity.ok(new ApiResponse<>(true, rolePermissionService.getAllRolePermission()));
	}
	
	@Operation(summary = "API find role-permissions by roleId, permissionId")
	@GetMapping("/role-permissions/{roleId}/{permissionId}")
	@PreAuthorize("hasAuthority('read_role_permission')")
	public ResponseEntity<?> findRolePermission(@PathVariable int roleId, @PathVariable int permissionId) {
		return ResponseEntity.ok(new ApiResponse<>(true, rolePermissionService.geRolePermission(new RolePermissionRequest(roleId, permissionId))));
	}
	
	@Operation(summary = "API find permissions by roleId")
	@GetMapping("/roles/{roleId}/permissions")
	@PreAuthorize("hasAuthority('read_role_permission')")
	public ResponseEntity<?> findPermissionsByRoleId(@PathVariable int roleId) {
		return ResponseEntity.ok(new ApiResponse<>(true, rolePermissionService.getPermissionsByRoleId(roleId)));
	}
	
	@Operation(summary = "API find roles by permissions")
	@GetMapping("/permissions/{permissionId}/roles")
	@PreAuthorize("hasAuthority('read_role_permission')")
	public ResponseEntity<?> findRolesByPermissionId(@PathVariable int permissionId) {
		return ResponseEntity.ok(new ApiResponse<>(true, rolePermissionService.getRolesByPermissionId(permissionId)));
	}
	
	@Operation(summary = "API create permission for role")
	@PostMapping("/role-permissions")
	@PreAuthorize("hasAuthority('create_role_permission')")
	public ResponseEntity<?> createRolePermission(@RequestBody RolePermissionRequest request) {
		return ResponseEntity.ok(new ApiResponse<>(true, rolePermissionService.createRolePermission(request)));
	}
	
	@Operation(summary = "API delete permission for role")
	@PreAuthorize("hasAuthority('delete_role_permission')")
	@DeleteMapping("/role-permissions/{roleId}/{permissionId}")
	public ResponseEntity<?> deleteRolePermission(@PathVariable int roleId, @PathVariable int permissionId) {
		rolePermissionService.deleteRolePermission(new RolePermissionRequest(roleId, permissionId));
		return ResponseEntity.ok(new ApiResponse<>(true, "RolePermission deleted successfully!"));
	}
}
