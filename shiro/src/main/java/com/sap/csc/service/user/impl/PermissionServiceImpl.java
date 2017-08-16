package com.sap.csc.service.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.csc.app.model.dto.request.PermissionRequest;
import com.sap.csc.app.model.enumeration.ResourceType;
import com.sap.csc.app.model.enumeration.RestrictionType;
import com.sap.csc.app.model.jpa.Permission;
import com.sap.csc.app.persistence.repository.user.PermissionRepository;
import com.sap.csc.service.user.PermissionService;

@Service
public class PermissionServiceImpl implements PermissionService {

	private final PermissionRepository permissionRepository;

	@Autowired
	public PermissionServiceImpl(PermissionRepository permissionRepository) {
		this.permissionRepository = permissionRepository;
	}

	@Override
	public Permission insert(PermissionRequest permissionRequest) {
		Permission permission = new Permission();

		permission.setResource(permissionRequest.getResource());
		permission.setReadRestriction(permissionRequest.getRead());
		permission.setWriteRestriction(permissionRequest.getWrite());

		return permissionRepository.saveAndFlush(permission);
	}

	@Override
	public List<Permission> batchInsert(List<PermissionRequest> permissionsRequest, String roleName) {
		List<Permission> permissions = new ArrayList<>();
		ResourceType resource = null;
		RestrictionType readRestriction = RestrictionType.NO_RESTRICTION;
		RestrictionType writeRestriction = RestrictionType.NO_RESTRICTION;
		StringBuilder sb = new StringBuilder();
		StringBuilder readSb = new StringBuilder();
		StringBuilder writeSb = new StringBuilder();

		for (PermissionRequest permissionRequest : permissionsRequest) {
			Permission permission = new Permission();
			resource = permissionRequest.getResource();
			readRestriction = permissionRequest.getRead();
			writeRestriction = permissionRequest.getWrite();
			sb = new StringBuilder(resource.getDescription());

			// set permission description
			if(readRestriction != RestrictionType.NO_RESTRICTION
					|| writeRestriction != RestrictionType.NO_RESTRICTION) {
				readSb = new StringBuilder(roleName + ":" + resource.getDescription());
				writeSb = new StringBuilder(roleName + ":" + resource.getDescription());
				
				// read restriction
				readSb.append(":Read");
				if(readRestriction != RestrictionType.NO_RESTRICTION) {
					readSb.append(":" + readRestriction.getDescription());
				}
				
				// write restriction
				writeSb.append(":Write");
				if(writeRestriction != RestrictionType.NO_RESTRICTION) {
					writeSb.append(":" + writeRestriction.getDescription());
				}
				
				sb = readSb.append(";").append(writeSb);
			}

			permission.setResource(resource);
			permission.setReadRestriction(readRestriction);
			permission.setWriteRestriction(writeRestriction);
			permission.setDescription(sb.toString());
			permissions.add(permission);
		} 

		return permissionRepository.batchInsert(permissions);
	}

}
