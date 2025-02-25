package com.lab.server.repositories;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.Employee;
import com.lab.server.entities.Position;
import com.lab.server.repositories.model.EmployeeModel;
import java.util.List;
import com.lab.server.entities.Department;


@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
	
	@Query(value = "SELECT e.employee_id as employeeId, CONCAT(e.first_name || ' ' || e.last_name) as fullName, \r\n"
			+ "	e.department_id as departmentId, e.position_id as positionId FROM public.employees e\r\n"
			+ "	WHERE e.user_id = :userId ", nativeQuery = true)
	public EmployeeModel findEmployeeByUserId(@Param("userId") int userId);
	
	List<Employee> findAllByDepartmentId(Department departmentId);
	List<Employee> findAllByPositionId(Position position);
}
