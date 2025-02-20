package com.lab.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab.lib.api.PaginationResponse;
import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.entities.Role;
import com.lab.server.entities.User;
import com.lab.server.payload.role.RoleResponse;
import com.lab.server.payload.user.UserRequest;
import com.lab.server.payload.user.UserResponse;
import com.lab.server.repositories.RoleRepository;
import com.lab.server.repositories.UserRepository;

//import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService extends BaseService<User, Integer> {

    private final UserRepository repository;
    
    @Autowired
    private  RoleRepository roleRepository;
    

    protected UserService(BaseRepository<User, Integer> repository) {
        super(repository);
        this.repository = (UserRepository) repository;
	
    
    }
    @Transactional(readOnly = true)
	public PaginationResponse<UserResponse> getAllUsersWithConditions(int page, int perPage, String search) {
		long totalRecord = repository.countAllUsersWithConditions(search);
		int offset = PagingUtil.getOffset(page, perPage);
		int totalPage = PagingUtil.getTotalPage(totalRecord, perPage);
		List<User> userList = repository.findAllUsersWithConditions(offset, perPage, search);
		List<UserResponse> response = new ArrayList<>();
		if(userList != null) {
			response = userList.stream().map(user -> responseBuilder(user)).toList();
		}

		return PaginationResponse.<UserResponse>builder().page(page).perPage(perPage).data(response)
				.totalPage(totalPage).totalRecord(totalRecord).build();
	}
	
	private UserResponse responseBuilder(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .roleName(user.getRoleId().getRoleName().toString())
                .build();
    }
    
//

    @Transactional(readOnly = true)
    public UserResponse findUserById(int id) {
        User user = repository.findById(id).orElse(null);
        return new UserResponse(user.getUserId(),user.getUsername(), user.getEmail(), user.getPassword(), user.getRoleId().getRoleName().toString());
    }

    public UserResponse createUser(UserRequest request) {
    	Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setRoleId(role);

        repository.save(user);
        return new UserResponse(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getRoleId().getRoleName().toString()
        );
    }

    public UserResponse updateUserById(int id, UserRequest request) {
    	Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        User user = repository.findById(id).orElse(null);
        if (user == null) return null;

        if (request.getUsername() != null && !request.getUsername().isEmpty()) {
            user.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            user.setPassword(request.getPassword()); 
        }
        if (request.getEmail() != null && !request.getEmail().isEmpty()) {
            user.setEmail(request.getEmail());
        }
        if (role != null) {
            user.setRoleId(role);
        }

        repository.save(user);
        return new UserResponse(
            user.getUserId(),
            user.getUsername(),
            user.getEmail(),
            user.getPassword(),
            user.getRoleId().getRoleName().toString()
        );
    }

    public String deleteUserById(int id) {
        User user = repository.findById(id).orElse(null);
        if (user == null) return "User not found!";

        repository.delete(user);
        return "Delete user " + id + " successfully!";
    }

 
}
