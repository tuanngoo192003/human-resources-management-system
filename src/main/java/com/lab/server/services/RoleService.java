package com.lab.server.services;

import java.lang.module.ModuleDescriptor.Builder;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lab.lib.api.PaginationResponse;
import com.lab.lib.repository.BaseRepository;
import com.lab.lib.service.BaseService;
import com.lab.lib.utils.PagingUtil;
import com.lab.server.entities.Role;
import com.lab.server.payload.role.RoleResponse;
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

	public PaginationResponse<RoleResponse> findRoleWithConditions(int page, int perpage, String search) {
		long totalRecord = repository.countAllBySearch(search);
		int offset = PagingUtil.getOffset(page, perpage);
		int totalPage = PagingUtil.getTotalPage(totalRecord, perpage);
		List<Role> roleList = repository.findAllRolesWithConditions(offset, perpage, search);
		List<RoleResponse> resultList = new ArrayList<>();
		if(roleList!=null) {
			for(Role role: roleList) {
				resultList.add(RoleResponse.builder()
						.id(role.getRoleId())
						.name(role.getRoleName().name())
						.description(role.getDescription())
						.build());
			}
		}
		return PaginationResponse.<RoleResponse>builder()
				.page(page)
				.perPage(perpage)
				.data(resultList)
				.totalRecord(totalRecord)
				.totalPage(totalPage)	
				.build();
	}
}
