package com.lab.server.payload.employee;

import java.time.LocalDate;

import com.lab.lib.enumerated.EmployeeStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeRequest {
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private LocalDate dateOfBirth;
	
	private String phoneNumber;
	
	private String address;
	
	private LocalDate hireDate;
	
	private Double salary;
	
	private EmployeeStatus status;
	
	private int userId;
	
	private int positionId;
	
	private int departmentId;
}
