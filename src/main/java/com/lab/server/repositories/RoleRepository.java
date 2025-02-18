package com.lab.server.repositories;

import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, Integer> {
}
