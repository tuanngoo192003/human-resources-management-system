package com.lab.server.services;

import org.springframework.stereotype.Service;

import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.server.entities.Role;
import com.lab.server.repositories.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService extends BaseService<Role, Integer> {

    private final RoleRepository repository;

    protected RoleService(BaseRepository<Role, Integer> repository) {
        super(repository);
        this.repository = (RoleRepository) repository;
    }
}
