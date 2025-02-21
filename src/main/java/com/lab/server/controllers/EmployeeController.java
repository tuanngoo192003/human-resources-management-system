package com.lab.server.controllers;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab.lib.api.ApiResponse;
import com.lab.lib.api.PaginationResponse;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.payload.employee.EmployeeRequest;
import com.lab.server.services.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@Tag(name = "employee")
@Validated
public class EmployeeController {
	private final EmployeeService employeeService;
	
	@Operation(summary = "Get all employees filter by name, phone numer, status")
	@GetMapping("")
	public ResponseEntity<?> getAll(
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_PAGE) int page, 
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_SIZE)int perpage
			){
		return ResponseEntity.ok(new ApiResponse<PaginationResponse<?>>(true, employeeService.findAll(page, perpage)));
	}
	
	@Operation(summary = "Get all employees filter by position id")
	@GetMapping("/position/{positionId}")
	public ResponseEntity<?> getAllByPosition(
			@PathVariable int positionId,
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_PAGE) int page, 
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_SIZE)int perpage 
			){
		return ResponseEntity.ok(new ApiResponse<PaginationResponse<?>>(true, employeeService.findAllByPositionId(positionId, page, perpage)));
	}
	
	@Operation(summary = "Get all employees filter by department id")
	@GetMapping("/department/{departmentId}")
	public ResponseEntity<?> getAllByDepartment(
			@PathVariable int departmentId,
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_PAGE) int page, 
			@RequestParam(required = false, defaultValue = PagingUtil.DEFAULT_SIZE)int perpage 
			){
		return ResponseEntity.ok(new ApiResponse<PaginationResponse<?>>(true, employeeService.findAllByDepartmentId(departmentId, page, perpage)));
	}
	
	@Operation(summary = "Get employee by id")
	@GetMapping("/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable int id){
		return ResponseEntity.ok(new ApiResponse<>(true, employeeService.findById(id)));
	}
	
	@Operation(summary = "Create new employee")
	@PostMapping("")
	public ResponseEntity<?> createNewEmployee(@ParameterObject EmployeeRequest request){
		return ResponseEntity.ok(new ApiResponse<>(true, employeeService.createEmployee(request)));
	}
	
	@Operation(summary = "Update employee by id")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployeeById(@PathVariable int id, @ParameterObject EmployeeRequest request){
		return ResponseEntity.ok(new ApiResponse<>(true, employeeService.updateEmployee(id, request)));
	}
	
	@Operation(summary = "Delete employee by id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> DeleteEmployeeById(@PathVariable int id){
		return ResponseEntity.ok(new ApiResponse<>(true, employeeService.deleteEmployee(id)));
	}
	
}
