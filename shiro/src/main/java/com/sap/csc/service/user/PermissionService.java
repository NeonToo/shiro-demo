package com.sap.csc.service.user;

import java.util.List;

import com.sap.csc.app.model.dto.request.PermissionRequest;
import com.sap.csc.app.model.jpa.Permission;

public interface PermissionService {

	Permission insert(PermissionRequest permissionRequest);
	
	List<Permission> batchInsert(List<PermissionRequest> permissionsRequest, String roleName);
	
}
