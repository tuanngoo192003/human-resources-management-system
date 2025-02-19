package com.lab.server.payload.role;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoleRequest {
	private String name;
	private String description;
}
