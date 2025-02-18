package com.lab.server.repositories;


import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.Department;

@Repository
public interface DepartmentRepository extends BaseRepository<Department, Integer> {
}
