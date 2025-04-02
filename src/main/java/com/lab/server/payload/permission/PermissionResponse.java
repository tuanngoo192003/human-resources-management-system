package com.lab.server.payload.permission;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionResponse {
	private int id;
	private String name;
	private String description;
}
