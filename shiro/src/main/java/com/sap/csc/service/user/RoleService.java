package com.sap.csc.service.user;

import java.util.List;

import com.sap.csc.app.model.dto.request.RoleRequest;
import com.sap.csc.app.model.jpa.Role;

public interface RoleService {
	
	Role findOne(int id);

	Role insert(RoleRequest roleRequest);

	List<Role> findAll();
	
	List<Role> findByUsername(String username);
	
}
