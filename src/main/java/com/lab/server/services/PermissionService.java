package com.lab.server.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lab.lib.api.PaginationResponse;
import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.entities.Permission;
import com.lab.server.payload.permission.PermissionRequest;
import com.lab.server.repositories.PermissionRepository;

@Service
@Transactional
public class PermissionService extends BaseService<Permission, Integer>{
	
	private final PermissionRepository repository;

	protected PermissionService(BaseRepository<Permission, Integer> repository) {
		super(repository);
		this.repository = (PermissionRepository) repository;
	}
	
	@Transactional(readOnly = true)
	@SuppressWarnings("null")
	public PaginationResponse<Permission> findAll(int page, int perpage, String search){
		long totalRecord = repository.countAllBySearch(search);
		int offset = PagingUtil.getOffset(page, perpage);
		int totalPage = PagingUtil.getTotalPage(totalRecord, perpage);
		List<Permission> list = repository.findAllPermissionsWithConditions(offset, perpage, search);
		return PaginationResponse.<Permission>builder()
				.page(page)
				.perPage(perpage)
				.data(list)
				.totalRecord(totalRecord)
				.totalPage(totalPage)	
				.build();
	}
	
	@Transactional(readOnly = true)
	public Permission findPermissionById(int id) {
		Permission permission= repository.findById(id).orElse(null);
		return permission;
	}
	
	public Permission createPermission(PermissionRequest request) {
		Permission permission = new Permission();
		permission.setPermissionName(request.getName());
		permission.setDescription(request.getDescription());
		repository.save(permission);
		return permission;
	}
	
	public Permission updatePermissionById(int id, PermissionRequest request) {
		Permission permission= repository.findById(id).orElse(null);
		if(!request.getName().isEmpty())
		permission.setPermissionName(request.getName());
		if(!request.getDescription().isEmpty())
		permission.setDescription(request.getDescription());
		repository.save(permission);
		return permission;
	}
	
	public String deletePermissionById(int id) {
		Permission permission= repository.findById(id).orElse(null);
		repository.delete(permission);
		return "Delete permission "+id+" successfully!";
	}
	
}
