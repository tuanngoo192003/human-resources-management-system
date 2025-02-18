package com.lab.server.repositories;

import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.Employee;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Integer> {
}
