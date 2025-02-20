package com.lab.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.User;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {


	@Query(value = "SELECT * FROM users u WHERE u.username ILIKE CONCAT('%', :search, '%') OR u.email ILIKE CONCAT('%', :search, '%') LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<User> findAllUsersWithConditions(@Param("offset") int offset, @Param("limit") int limit, @Param("search") String search);

	@Query(value = "SELECT COUNT(*) FROM users u WHERE u.username ILIKE CONCAT('%', :search, '%') OR u.email ILIKE CONCAT('%', :search, '%')", nativeQuery = true)
	long countAllUsersWithConditions(@Param("search") String search);
}
