package com.lab.server.controllers;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.lab.lib.api.PaginationResponse;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.configs.language.MessageSourceHelper;
import com.lab.server.payload.auth.LoginRequest;
import com.lab.server.payload.auth.LoginResponse;
import com.lab.server.payload.user.UserRequest;
import com.lab.server.payload.user.UserResponse;
import com.lab.server.services.AuthService;
import com.lab.server.services.UserService;

import io.netty.util.internal.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final AuthService authService;
	private final UserService userService;
	private final MessageSourceHelper messageHelper;

	@PostMapping("/login")
	public ApiResponse<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws Exception {
		return new ApiResponse<LoginResponse>(true, authService.login(loginRequest));
	}

	@PostMapping("/logout")
	public ApiResponse<Object> logout(HttpServletRequest request) throws BadRequestException {
		authService.logout(request);
		return new ApiResponse<Object>(true, messageHelper.getMessage("notification.logoutSucessfully"));
	}

	@GetMapping("/me")
	public ApiResponse<UserResponse> getCurrentUser() {
		return new ApiResponse<>(true, userService.getCurrentUser());
	}

	@Operation(summary = "API get all user")
	@GetMapping
	@PreAuthorize("hasAuthority('READ_USERS')")
	public PaginationResponse<UserResponse> getAllUsers(
			@RequestParam(defaultValue = PagingUtil.DEFAULT_PAGE) int page,
			@RequestParam(defaultValue = PagingUtil.DEFAULT_SIZE) int perPage,
			@RequestParam(defaultValue = StringUtil.EMPTY_STRING) String search) {

		return userService.getAllUsersWithConditions(page, perPage, search);
	}

	@Operation(summary = "API get user by ID")
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('READ_USERS')")
	public ResponseEntity<UserResponse> getUserById(@PathVariable int id) {
		return ResponseEntity.ok(userService.findUserById(id));
	}

	@Operation(summary = "API create user")
	@PostMapping
	@PreAuthorize("hasAuthority('WRITE_USERS')")
	public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
	}

	@Operation(summary = "API update user")
	@PutMapping("/{id}")
	@PreAuthorize("hasAuthority('WRITE_USERS')")
	public ResponseEntity<UserResponse> updateUser(@PathVariable int id, @RequestBody @Valid UserRequest.UpdateUserRequest request) {
		return ResponseEntity.ok(userService.updateUserById(id, request));
	}

	@Operation(summary = "API delete user")
	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('DELETE_USERS')")
	public ResponseEntity<String> deleteUser(@PathVariable int id) {
		return ResponseEntity.ok(userService.deleteUserById(id));
	}
}
