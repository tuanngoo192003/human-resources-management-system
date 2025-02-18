package com.lab.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Table(name = "departments", schema = "public")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Department implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "department_id", unique = true, nullable = false)
	private int departmentId;
	
	@Column(name = "departmen_name", nullable = false, length = 255)
	private String departmentName;
	
	@Column(name = "description")
	private String description;
	
}
