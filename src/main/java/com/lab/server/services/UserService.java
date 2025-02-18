package com.lab.server.services;

import org.springframework.stereotype.Service;

import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.server.entities.User;
import com.lab.server.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService extends BaseService<User, Integer> {

    private final UserRepository repository;

    protected UserService(BaseRepository<User, Integer> repository) {
        super(repository);
        this.repository = (UserRepository) repository;
    }
}
