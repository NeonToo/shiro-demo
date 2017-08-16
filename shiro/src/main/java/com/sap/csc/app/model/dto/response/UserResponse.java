package com.sap.csc.app.model.dto.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.sap.csc.app.model.jpa.User;

public class UserResponse implements Serializable {

	private static final long serialVersionUID = 120887761566689172L;

	private static final List<RoleResponse> EMPTY_ROLES = new ArrayList<>(0);

	private Long id;

	private String username;

	private List<RoleResponse> roles;

	public UserResponse(User user) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.roles = CollectionUtils.isEmpty(user.getRoles()) ? EMPTY_ROLES
				: user.getRoles().stream().map(role -> new RoleResponse(role)).collect(Collectors.toList());
	}

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

	public List<RoleResponse> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleResponse> roles) {
		this.roles = roles;
	}

}
