package com.sap.csc.app.model.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.sap.csc.app.model.jpa.Role;

public class RoleResponse implements Serializable {

	private static final long serialVersionUID = -7675432956841827031L;

	private static final List<PermissionResponse> EMPTY_PERMISSIONS = new ArrayList<>(0);

	private Integer id;

	private String name;

	private List<PermissionResponse> permissions;

	public RoleResponse(Role role) {
		this.id = role.getId();
		this.name = role.getName();
		this.permissions = CollectionUtils.isEmpty(role.getPermissions()) ? EMPTY_PERMISSIONS
				: role.getPermissions().stream().map(permission -> new PermissionResponse(permission))
						.collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PermissionResponse> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionResponse> permissions) {
		this.permissions = permissions;
	}

}
