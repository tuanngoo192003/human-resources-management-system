package com.lab.server.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab.lib.api.ApiResponse;
import com.lab.lib.api.PaginationResponse;
import com.lab.lib.enumerated.EmployeeStatus;
import com.lab.lib.exceptions.BadRequestException;
import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.server.configs.language.MessageSourceHelper;
import com.lab.server.entities.Department;
import com.lab.server.entities.Employee;
import com.lab.server.entities.Position;
import com.lab.server.entities.User;
import com.lab.server.payload.employee.EmployeeRequest;
import com.lab.server.payload.employee.EmployeeResponse;
import com.lab.server.repositories.DepartmentRepository;
import com.lab.server.repositories.EmployeeRepository;
import com.lab.server.repositories.PositionRepository;
import com.lab.server.repositories.UserRepository;

@Service
public class EmployeeService extends BaseService<Employee, Integer>{
	
	private final EmployeeRepository repository;
	private final UserRepository userRepository;
	private final PositionRepository positionRepository;
	private final DepartmentRepository departmentRepository;
	private final MessageSourceHelper messageSourceHelper;
	
	protected EmployeeService(BaseRepository<Employee, Integer> repository, 
			DepartmentRepository departmentRepository, PositionRepository positionRepository,
			UserRepository userRepository, MessageSourceHelper messageSourceHelper) {
		super(repository);
		this.userRepository = userRepository;
		this.departmentRepository = departmentRepository;
		this.positionRepository = positionRepository;
		this.messageSourceHelper = messageSourceHelper;
		this.repository = (EmployeeRepository) repository;
	}
	
	@Transactional(readOnly = true)
	public PaginationResponse<EmployeeResponse> findAll(
			int page, int perpage){
		Pageable pageable = PageRequest.of(page - 1, perpage);
		Page<Employee> employeePage = repository.findAll(pageable);
		List<EmployeeResponse> result = employeePage.getContent().stream().map(e -> toEmployeeResponse(e)).toList();
		return PaginationResponse.<EmployeeResponse>builder()
                .page(page)
                .perPage(perpage)
                .data(result)
                .totalRecord(employeePage.getTotalElements())
                .totalPage(employeePage.getTotalPages())
                .build();
	}
	
	@Transactional(readOnly = true)
	public EmployeeResponse findById(int id) {
		Employee e =  repository.findById(id).orElse(null);
		if(e != null) {
			return toEmployeeResponse(e);
		}
		else throw new BadRequestException(messageSourceHelper.getMessage("error.employeeNotFound", id));
	}
	
	@Transactional(rollbackFor = BadRequestException.class)
	public EmployeeResponse createEmployee(EmployeeRequest request) {
		Employee employee =  toEmployee(request);
	    employee = repository.save(employee);
	    System.out.println(employee.getEmployeeId());
		return toEmployeeResponse(employee);
	}
	
	@Transactional(rollbackFor = BadRequestException.class)
	public EmployeeResponse updateEmployee(int id, EmployeeRequest request) {
	    Employee employee = repository.findById(id).orElse(null);
	    if (employee == null) {
	        throw new BadRequestException(messageSourceHelper.getMessage("error.employeeNotFound", id));
	    }
	    if (request.getFirstName() != null) {
	        employee.setFirstName(request.getFirstName());
	    }
	    if (request.getLastName() != null) {
	        employee.setLastName(request.getLastName());
	    }
	    if (request.getDateOfBirth() != null) {
	        employee.setDateOfBirth(request.getDateOfBirth());
	    }
	    if (request.getPhoneNumber() != null) {
	        employee.setPhoneNumber(request.getPhoneNumber());
	    }
	    if (request.getAddress() != null) {
	        employee.setAddress(request.getAddress());
	    }
	    if (request.getHireDate() != null) {
	        employee.setHireDate(request.getHireDate());
	    }
	    if (request.getSalary() != null) {
	        employee.setSalary(request.getSalary());
	    }
	    if (request.getStatus() != null) {
	        employee.setStatus(request.getStatus());
	    }
	    User user = userRepository.findById(request.getUserId()).orElse(null);
	    if(user != null) {
	    	employee.setUserId(user);
	    }
	    Position position = positionRepository.findById(request.getPositionId()).orElse(null);
	    if (position != null) {
	        employee.setPositionId(position);
	    }
	    Department department = departmentRepository.findById(request.getDepartmentId()).orElse(null);
	    if (department != null) {
	        employee.setDepartmentId(department);
	    }
	    repository.save(employee);
	    return toEmployeeResponse(employee);
	}
	
	public ApiResponse<String> deleteEmployee(int id){
		Employee employee = repository.findById(id).orElse(null);
		if(employee!=null) {
			repository.delete(employee);
			return new ApiResponse<>(true, messageSourceHelper.getMessage("success.deleteEmployee", id));
		}
		else return new ApiResponse<>(false, messageSourceHelper.getMessage("error.employeeNotFound", id));
	}
	
	@Transactional(readOnly = true)
	public PaginationResponse<EmployeeResponse> findAllByDepartmentId(
			int id, int page, int perpage){
		Department d = departmentRepository.findById(id).orElse(null);
		if(d!=null) {
			Pageable pageable = PageRequest.of(page - 1, perpage);
			Page<Employee> employeePage = repository.findAllByDepartmentId(d, pageable);
			List<EmployeeResponse> result = employeePage.getContent().stream().map(e -> toEmployeeResponse(e)).toList();
			return PaginationResponse.<EmployeeResponse>builder()
	                .page(page)
	                .perPage(perpage)
	                .data(result)
	                .totalRecord(employeePage.getTotalElements())
	                .totalPage(employeePage.getTotalPages())
	                .build();
		}
		else {
			throw new BadRequestException(messageSourceHelper.getMessage("error.employeeNotFoundDepartmentId", id));
		}
	}
	
	@Transactional(readOnly = true)
	public PaginationResponse<EmployeeResponse> findAllByPositionId(
			int id, int page, int perpage){
		Position p = positionRepository.findById(id).orElse(null);
		if(p!=null) {
			Pageable pageable = PageRequest.of(page - 1, perpage);
			Page<Employee> employeePage = repository.findAllByPositionId(p, pageable);
			List<EmployeeResponse> result = employeePage.getContent().stream().map(e -> toEmployeeResponse(e)).toList();
			return PaginationResponse.<EmployeeResponse>builder()
	                .page(page)
	                .perPage(perpage)
	                .data(result)
	                .totalRecord(employeePage.getTotalElements())
	                .totalPage(employeePage.getTotalPages())
	                .build();
		}
		else {
			throw new BadRequestException(messageSourceHelper.getMessage("error.employeeNotFoundPositionId", id));
		}
	}
	private Employee toEmployee(EmployeeRequest request) {
		new Employee();
		return Employee.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.dateOfBirth(request.getDateOfBirth())
				.phoneNumber(request.getPhoneNumber())
				.address(request.getAddress())
				.hireDate(request.getHireDate() == null ? LocalDate.now() : request.getHireDate())
				.salary(request.getSalary())
				.status(request.getStatus() == null ? EmployeeStatus.DEFAULT : request.getStatus())
				.userId(userRepository.findById(request.getUserId()).orElse(null))
				.positionId(positionRepository.findById(request.getPositionId()).orElse(null))
				.departmentId(departmentRepository.findById(request.getDepartmentId()).orElse(null))
				.build();
	}
	
	private EmployeeResponse toEmployeeResponse(Employee e) {
		new EmployeeResponse();
		return EmployeeResponse.builder()
				.id(e.getEmployeeId())
				.firstName(e.getFirstName())
				.lastName(e.getLastName())
				.dateOfBirth(e.getDateOfBirth().toString())
				.phoneNumber(e.getPhoneNumber())
				.address(e.getAddress())
				.hireDate(e.getHireDate().toString())
				.salary(e.getSalary())
				.status(e.getStatus().toString())
				.userId(e.getUserId() != null ? e.getUserId().getUserId() : 0)
				.positionId(e.getPositionId() != null ? e.getPositionId().getPositionId() :0)
				.departmentId(e.getDepartmentId() != null ? e.getDepartmentId().getDepartmentId() :0)
				.build();
	}
}
