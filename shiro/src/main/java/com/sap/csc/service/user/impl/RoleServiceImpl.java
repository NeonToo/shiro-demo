package com.sap.csc.service.user.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.csc.app.model.dto.request.PermissionRequest;
import com.sap.csc.app.model.dto.request.RoleRequest;
import com.sap.csc.app.model.jpa.Permission;
import com.sap.csc.app.model.jpa.Role;
import com.sap.csc.app.persistence.repository.user.RoleRepository;
import com.sap.csc.service.user.PermissionService;
import com.sap.csc.service.user.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;
	
	private final PermissionService permissionService;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository, PermissionService permissionService) {
		this.roleRepository = roleRepository;
		this.permissionService = permissionService;
	}

	@Override
	public Role insert(RoleRequest roleRequest) {
		Role role = new Role();
		List<PermissionRequest> permissionsRequest = roleRequest.getPermissions();
		
		role.setName(roleRequest.getName());
		
		if(CollectionUtils.isNotEmpty(permissionsRequest)) {
			List<Permission> permissions = permissionService.batchInsert(permissionsRequest, roleRequest.getName());
			
			if(CollectionUtils.isNotEmpty(permissions)) {
				role.setPermissions(permissions);
			}
		}
		
		return roleRepository.saveAndFlush(role);
	}

	@Override
	public Role findOne(int id) {
		return roleRepository.findOne(id);
	}

	@Override
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Override
	public List<Role> findByUsername(String username) {
		return roleRepository.findByUsername(username);
	}

}
