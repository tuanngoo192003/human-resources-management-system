package com.lab.server.payload.role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {
	private int id;
	private String name;
	private String description;
}
