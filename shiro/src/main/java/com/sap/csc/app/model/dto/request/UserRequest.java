package com.sap.csc.app.model.dto.request;

import java.io.Serializable;
import java.util.List;

public class UserRequest implements Serializable {

	private static final long serialVersionUID = -5307574967939863499L;

	private Long id;
	
	private String username;
	
	private String password;
	
	private Integer department;
	
	private List<RoleRequest> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getDepartment() {
		return department;
	}

	public void setDepartment(Integer department) {
		this.department = department;
	}

	public List<RoleRequest> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleRequest> roles) {
		this.roles = roles;
	}
}
