package com.lab.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lab.lib.repository.BaseRepository;
import com.lab.server.entities.User;
import com.lab.server.repositories.model.UserModel;

@Repository
public interface UserRepository extends BaseRepository<User, Integer> {

	
	
	
	@Query(value = """
		    SELECT u.user_id as userId, u.username, u.email, r.role_name as roleName
		    FROM users u
		    LEFT JOIN roles r ON r.role_id = u.role_id
		    LEFT JOIN employees e ON e.user_id = u.user_id
		    LEFT JOIN departments d ON d.department_id = e.department_id
		    WHERE d.department_id = :departmentId
		    AND (:search IS NULL OR :search = '' 
		        OR u.username ILIKE CONCAT('%', :search, '%')
		        OR u.email ILIKE CONCAT('%', :search, '%'))
		    ORDER BY u.username ASC
		    LIMIT :limit OFFSET :offset
		    """, nativeQuery = true)
		List<UserModel> findAllUsersByDepartment(
		    @Param("departmentId") int departmentId, 
		    @Param("offset") int offset,
		    @Param("limit") int limit, 
		    @Param("search") String search
		);

	@Query(value = """
		    SELECT COUNT(*)
		    FROM users u
		    LEFT JOIN roles r ON r.role_id = u.role_id
		    LEFT JOIN employees e ON e.user_id = u.user_id
		    LEFT JOIN departments d ON d.department_id = e.department_id
		    WHERE d.department_id = :departmentId
		    AND (:search IS NULL OR :search = '' 
		        OR u.username ILIKE CONCAT('%', :search, '%')
		        OR u.email ILIKE CONCAT('%', :search, '%'))
		    """, nativeQuery = true)
		long countUsersByDepartment(
		    @Param("departmentId") int departmentId, 
		    @Param("search") String search
		);

	//
	@Query(value = """
			SELECT COUNT(*)
			FROM users
			WHERE (:search IS NULL OR :search = ''
			    OR username ILIKE CONCAT('%', :search, '%')
			    OR email ILIKE CONCAT('%', :search, '%')
			    OR to_tsvector('english', username) @@ plainto_tsquery('english', :search)
			    OR to_tsvector('english', email) @@ plainto_tsquery('english', :search))
			""", nativeQuery = true)
	long countUsersBySearch(@Param("search") String search);

	@Query(value = """
			SELECT user_id, username, email, role_id
			FROM users
			WHERE (:search IS NULL OR :search = ''
			    OR username ILIKE CONCAT('%', :search, '%')
			    OR email ILIKE CONCAT('%', :search, '%')
			    OR to_tsvector('english', username) @@ plainto_tsquery('english', :search)
			    OR to_tsvector('english', email) @@ plainto_tsquery('english', :search))
			ORDER BY username ASC
			LIMIT :limit OFFSET :offset
			""", nativeQuery = true)
	List<UserModel> searchUsersWithPagination(@Param("search") String search, @Param("limit") int limit,
			@Param("offset") int offset);

	//
	@Query(value = "SELECT u.user_id as userId, u.username, u.email, r.role_name as roleName FROM users u \r\n"
			+ "INNER JOIN roles r ON r.role_id = u.role_id\r\n"
			+ "INNER JOIN employees e ON e.user_id = u.user_id\r\n"
			+ "INNER JOIN departments d ON d.department_id = e.department_id\r\n"
			+ "WHERE ( :search IS NULL OR :search = '' OR to_tsvector('english', username) @@ plainto_tsquery('english', :search))\r\n"
			+ "AND ( :search IS NULL OR :search = '' OR to_tsvector('english', email) @@ plainto_tsquery('english', :search))\r\n"
			+ "LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<UserModel> findAllUsersWithConditionsForManager(@Param("offset") int offset, 
			@Param("limit") int limit, 
			@Param("search") String search);

	@Query(value = "SELECT COUNT(1) FROM users u \r\n"
			+ "INNER JOIN roles r ON r.role_id = u.role_id\r\n"
			+ "INNER JOIN employees e ON e.user_id = u.user_id\r\n"
			+ "INNER JOIN departments d ON d.department_id = e.department_id\r\n"
			+ "WHERE ( :search IS NULL OR :search = '' OR to_tsvector('english', username) @@ plainto_tsquery('english', :search))\r\n"
			+ "AND ( :search IS NULL OR :search = '' OR to_tsvector('english', email) @@ plainto_tsquery('english', :search))\r\n", nativeQuery = true)
	long countAllUsersWithConditionsForManager(@Param("search") String search);
	
	@Query(value = "SELECT u.user_id as userId, u.username, u.email, r.role_name as roleName FROM users u \r\n"
			+ "INNER JOIN roles r ON r.role_id = u.role_id\r\n"
			+ "WHERE ( :search IS NULL OR :search = '' OR to_tsvector('english', username) @@ plainto_tsquery('english', :search))\r\n"
			+ "AND ( :search IS NULL OR :search = '' OR to_tsvector('english', email) @@ plainto_tsquery('english', :search))\r\n"
			+ "LIMIT :limit OFFSET :offset", nativeQuery = true)
	List<UserModel> findAllUsersWithConditionsForAdmin(@Param("offset") int offset, 
			@Param("limit") int limit, 
			@Param("search") String search);

	@Query(value = "SELECT COUNT(1) FROM users u \r\n"
			+ "INNER JOIN roles r ON r.role_id = u.role_id\r\n"
			+ "WHERE ( :search IS NULL OR :search = '' OR to_tsvector('english', username) @@ plainto_tsquery('english', :search))\r\n"
			+ "AND ( :search IS NULL OR :search = '' OR to_tsvector('english', email) @@ plainto_tsquery('english', :search))\r\n", nativeQuery = true)
	long countAllUsersWithConditionsForAdmin(@Param("search") String search);

	@Query(value = "SELECT u.user_id as userId, u.username, u.email, r.role_name as roleName FROM users u \r\n"
			+ "INNER JOIN roles r ON r.role_id = u.role_id\r\n"
			+ "WHERE u.user_id = :id ", nativeQuery = true)
	UserModel findUserById(@Param("id") int id);
	
	@Query(value = "SELECT u.user_id as userId, u.username, u.email, r.role_name as roleName FROM users u \r\n"
			+ "INNER JOIN roles r ON r.role_id = u.role_id\r\n"
			+ "INNER JOIN employees e ON e.user_id = u.user_id\r\n"
			+ "INNER JOIN departments d ON d.department_id = e.department_id\r\n"
			+ "WHERE u.user_id = :id ", nativeQuery = true)
	UserModel findUserByIdForManager(@Param("id") int id);
}
