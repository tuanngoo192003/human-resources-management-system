package com.lab.server.payload.user;

import com.lab.server.payload.role.RoleResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@AllArgsConstructor
public class UserRequest {

    private String username;	
    private String password;
    private String email;
    private int roleId; // ID của vai trò (Role)
}