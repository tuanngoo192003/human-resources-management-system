package com.lab.server.payload.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EmployeeResponse {
	private int id;
	
	private String firstName;
	
	private String lastName;

	private String dateOfBirth;
	
	private String phoneNumber;
	
	private String address;
	
	private String hireDate;
	
	private Double salary;
	
	private String  status;
	
	private int userId;
	
	private int positionId;
	
	private int departmentId;
}
