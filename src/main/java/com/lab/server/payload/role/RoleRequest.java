package com.lab.server.payload.role;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleRequest {
	String name;
	String description;
}
