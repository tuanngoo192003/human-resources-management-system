package com.lab.server.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab.lib.exceptions.BadRequestException;
import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.server.configs.language.MessageSourceHelper;
import com.lab.server.entities.Permission;
import com.lab.server.entities.Role;
import com.lab.server.payload.permission.PermissionResponse;
import com.lab.server.payload.role.RoleResponse;
import com.lab.server.payload.rolepermission.RolePermissionRequest;
import com.lab.server.payload.rolepermission.RolePermissionResponse;
import com.lab.server.repositories.PermissionRepository;
import com.lab.server.repositories.RoleRepository;


@Service
@Transactional
public class RolePermissionService extends BaseService<Role, Integer>{
	
	private final RoleRepository repository;
	private final PermissionService permissionService;
	private final PermissionRepository permissionRepository;
	private final MessageSourceHelper messageSourceHelper;
	
	protected RolePermissionService(BaseRepository<Role, Integer> repository,PermissionService permissionService,
			PermissionRepository permissionRepository, MessageSourceHelper messageSourceHelper) {
		super(repository);
		this.permissionRepository = permissionRepository;
		this.permissionService = permissionService;
		this.messageSourceHelper = messageSourceHelper;
		this.repository = (RoleRepository) repository;
	}
	
	@Transactional(readOnly = true)
	public List<RolePermissionResponse> getAllRolePermission () {
		return repository.findAllRolePermissions();
	}
	
	@Transactional(readOnly = true)
	public RolePermissionResponse geRolePermission(RolePermissionRequest request) {
		return repository.findRolePermission(request.getRoleId(), request.getPermissionId()).orElse(null);
	}
	
	public RolePermissionResponse createRolePermission(RolePermissionRequest request) {
		Role role= repository.findById(request.getRoleId()).orElse(null);
		Permission permission = permissionRepository.findById(request.getPermissionId()).orElse(null);
		if(role!= null && permission != null) {
		role.getPermissions().add(permission);
		repository.save(role);
		return new RolePermissionResponse(role.getRoleName().toString(), permission.getPermissionName());
		}
		else throw new BadRequestException(messageSourceHelper.getMessage("error.roleNotFound", request.getRoleId()));
	}
	
	public void deleteRolePermission(RolePermissionRequest request) {
		Role role= repository.findById(request.getRoleId()).orElse(null);
		Permission permission = permissionRepository.findById(request.getPermissionId()).orElse(null);
		if(role!= null && permission != null) {
			role.getPermissions().remove(permission);
			repository.save(role);
		}
		else throw new BadRequestException(messageSourceHelper.getMessage("error.deleteRolePermission", request.getRoleId(), request.getPermissionId()));			
	}
	
	public List<RoleResponse> getRolesByPermissionId(int id) {
		return permissionService.getRoleListByPermissionId(id);
	}
	
	public List<PermissionResponse> getPermissionsByRoleId(int id) {
		List<PermissionResponse> list = new ArrayList<>();
		repository.findById(id).orElse(null).getPermissions().forEach(p -> {
			list.add(new PermissionResponse(p.getPermissionId(),p.getPermissionName(),p.getDescription()));
		});;
		return list;
	}
}
