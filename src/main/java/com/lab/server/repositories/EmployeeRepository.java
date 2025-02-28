package com.lab.server.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.Employee;
import com.lab.server.entities.Position;
import com.lab.server.repositories.model.EmployeeModel;

import com.lab.server.entities.Department;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Integer> {

	@Query(value = "SELECT e.employee_id as employeeId, CONCAT(e.first_name || ' ' || e.last_name) as fullName, \r\n"
			+ "	e.department_id as departmentId, e.position_id as positionId FROM public.employees e\r\n"
			+ "	WHERE e.user_id = :userId ", nativeQuery = true)
	public EmployeeModel findEmployeeByUserId(@Param("userId") int userId);
	
	@Query(value = "SELECT e.employee_id as employeeId, CONCAT(e.first_name || ' ' || e.last_name) as fullName, \r\n"
			+ "	e.department_id as departmentId, e.position_id as positionId FROM public.employees e\r\n"
			+ "	WHERE e.user_id = :userId ", nativeQuery = true)
	public Employee findEmployeeByUserIdNotModel(@Param("userId") int userId);

	Page<Employee> findAllByDepartmentId(Department departmentId, Pageable pageable);

	Page<Employee> findAllByPositionId(Position position, Pageable pageable);

	@Query(value = """
			    SELECT * FROM employees e
			    WHERE (:search IS NULL OR :search = ''
			    OR e.first_name ILIKE CONCAT('%', :search, '%')
			    OR e.last_name ILIKE CONCAT('%', :search, '%')
			    OR e.phone_number ILIKE CONCAT('%', :search, '%')
			    OR CAST(e.status AS TEXT) ILIKE CONCAT('%', :search, '%'))
			    ORDER BY e.employee_id
			    LIMIT :limit OFFSET :offset
			""", nativeQuery = true)
	List<Employee> searchEmployeesWithPagination(@Param("search") String search, @Param("limit") int limit,
			@Param("offset") int offset);

	@Query(value = """
			    SELECT COUNT(*) FROM employees e
			    WHERE (:search IS NULL OR :search = ''
			    OR e.first_name ILIKE CONCAT('%', :search, '%')
			    OR e.last_name ILIKE CONCAT('%', :search, '%')
			    OR e.phone_number ILIKE CONCAT('%', :search, '%')
			    OR CAST(e.status AS TEXT) ILIKE CONCAT('%', :search, '%'))
			""", nativeQuery = true)
	long countEmployees(@Param("search") String search);

}
