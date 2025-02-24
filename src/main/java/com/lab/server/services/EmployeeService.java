package com.lab.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab.lib.api.ApiResponse;
import com.lab.lib.api.PaginationResponse;
import com.lab.lib.exceptions.BadRequestException;
import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.lib.utils.PagingUtil;
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
		List<Employee> list = repository.findAll();
		List<EmployeeResponse> result = new ArrayList<>();
		list.forEach(e -> {
			result.add(new EmployeeResponse(
					e.getEmployeeId(),
					e.getFirstName(),
					e.getLastName(),
					e.getDateOfBirth().toString(),
					e.getPhoneNumber(),
					e.getAddress(),
					e.getHireDate().toString(),
					e.getSalary(),
					e.getStatus() != null ? e.getStatus().toString() : "",
					e.getUserId() != null ? e.getUserId().getUserId() : 0,
					e.getPositionId() != null ? e.getPositionId().getPositionId() :0,
					e.getDepartmentId() != null ? e.getDepartmentId().getDepartmentId() :0 
					));
		});
		long totalRecord = list.size();
		int totalPage = PagingUtil.getTotalPage(totalRecord, perpage);
		return PaginationResponse.<EmployeeResponse>builder()
				.page(page)
				.perPage(perpage)
				.data(result)
				.totalRecord(totalRecord)
				.totalPage(totalPage)	
				.build();
	}
	
	@Transactional(readOnly = true)
	public EmployeeResponse findById(int id) {
		Employee e =  repository.findById(id).orElse(null);
		if(e != null) {
			return new EmployeeResponse(
					e.getEmployeeId(),
					e.getFirstName(),
					e.getLastName(),
					e.getDateOfBirth().toString(),
					e.getPhoneNumber(),
					e.getAddress(),
					e.getHireDate().toString(),
					e.getSalary(),
					e.getStatus() != null ? e.getStatus().toString() : "",
					e.getUserId() != null ? e.getUserId().getUserId() : 0,
					e.getPositionId() != null ? e.getPositionId().getPositionId() :0,
					e.getDepartmentId() != null ? e.getDepartmentId().getDepartmentId() :0
					);
		}
		else throw new BadRequestException(messageSourceHelper.getMessage("error.employeeNotFound", id));
	}
	
	public EmployeeResponse createEmployee(EmployeeRequest request) {
		Employee employee =  new Employee();
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
	    employee = repository.save(employee);
	    System.out.println(employee.getEmployeeId());
		return new EmployeeResponse(
				employee.getEmployeeId(),
				employee.getFirstName(),
				employee.getLastName(),
				employee.getDateOfBirth().toString(),
				employee.getPhoneNumber(),
				employee.getAddress(),
				employee.getHireDate().toString(),
				employee.getSalary(),
				employee.getStatus() != null ? employee.getStatus().toString() : "",
				employee.getUserId() != null ? employee.getUserId().getUserId() : 0,
				employee.getPositionId() != null ? employee.getPositionId().getPositionId() :0,
				employee.getDepartmentId() != null ? employee.getDepartmentId().getDepartmentId() :0
				);
	}
	
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
	    return new EmployeeResponse(
				employee.getEmployeeId(),
				employee.getFirstName(),
				employee.getLastName(),
				employee.getDateOfBirth().toString(),
				employee.getPhoneNumber(),
				employee.getAddress(),
				employee.getHireDate().toString(),
				employee.getSalary(),
				employee.getStatus() != null ? employee.getStatus().toString() : "",
				employee.getUserId() != null ? employee.getUserId().getUserId() : 0,
				employee.getPositionId() != null ? employee.getPositionId().getPositionId() :0,
				employee.getDepartmentId() != null ? employee.getDepartmentId().getDepartmentId() :0
				);
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
			List<Employee> list = repository.findAllByDepartmentId(d);
			List<EmployeeResponse> result = new ArrayList<>();
			list.forEach(e -> {
				result.add(new EmployeeResponse(
						e.getEmployeeId(),
						e.getFirstName(),
						e.getLastName(),
						e.getDateOfBirth().toString(),
						e.getPhoneNumber(),
						e.getAddress(),
						e.getHireDate().toString(),
						e.getSalary(),
						e.getStatus() != null ? e.getStatus().toString() : "",
						e.getUserId() != null ? e.getUserId().getUserId() : 0,
						e.getPositionId() != null ? e.getPositionId().getPositionId() :0,
						e.getDepartmentId() != null ? e.getDepartmentId().getDepartmentId() :0
						));
			});
			long totalRecord = list.size();
			int totalPage = PagingUtil.getTotalPage(totalRecord, perpage);
			return PaginationResponse.<EmployeeResponse>builder()
					.page(page)
					.perPage(perpage)
					.data(result)
					.totalRecord(totalRecord)
					.totalPage(totalPage)	
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
			List<Employee> list = repository.findAllByPositionId(p);
			List<EmployeeResponse> result = new ArrayList<>();
			list.forEach(e -> {
				result.add(new EmployeeResponse(
						e.getEmployeeId(),
						e.getFirstName(),
						e.getLastName(),
						e.getDateOfBirth().toString(),
						e.getPhoneNumber(),
						e.getAddress(),
						e.getHireDate().toString(),
						e.getSalary(),
						e.getStatus() != null ? e.getStatus().toString() : "",
						e.getUserId() != null ? e.getUserId().getUserId() : 0,
						e.getPositionId() != null ? e.getPositionId().getPositionId() :0,
						e.getDepartmentId() != null ? e.getDepartmentId().getDepartmentId() :0
						));
			});
			long totalRecord = list.size();
			int totalPage = PagingUtil.getTotalPage(totalRecord, perpage);
			return PaginationResponse.<EmployeeResponse>builder()
					.page(page)
					.perPage(perpage)
					.data(result)
					.totalRecord(totalRecord)
					.totalPage(totalPage)	
					.build();
		}
		else {
			throw new BadRequestException(messageSourceHelper.getMessage("error.employeeNotFoundPositionId", id));
		}
	}
	
	
}
