package com.sap.csc.app.model.dto.request;

import java.io.Serializable;
import java.util.List;

public class RoleRequest implements Serializable {

	private static final long serialVersionUID = -7928072693841422157L;
	
	private int id;
	
	private String name;
	
	private List<PermissionRequest> permissions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PermissionRequest> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<PermissionRequest> permissions) {
		this.permissions = permissions;
	}

}
