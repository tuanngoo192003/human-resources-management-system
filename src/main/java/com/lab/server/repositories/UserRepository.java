package com.lab.server.repositories;

import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.User;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

}
