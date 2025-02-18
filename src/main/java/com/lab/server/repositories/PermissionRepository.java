package com.lab.server.repositories;

import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.Permission;

@Repository
public interface PermissionRepository extends BaseRepository<Permission, Integer> {
}
