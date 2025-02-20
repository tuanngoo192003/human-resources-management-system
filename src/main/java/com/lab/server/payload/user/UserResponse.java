package com.lab.server.payload.user;

import com.lab.server.entities.Role;
import com.lab.server.payload.role.RoleResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
public class UserResponse {
	private int userId;
    private String username;	
    private String password;
    private String email;
    private String roleName; // ID của vai trò (Role)
}