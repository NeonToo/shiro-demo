package com.sap.csc.web.controller.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.app.model.dto.request.RoleRequest;
import com.sap.csc.app.model.dto.response.RoleResponse;
import com.sap.csc.app.model.jpa.Role;
import com.sap.csc.service.user.RoleService;
import com.sap.csc.web.controller.GeneralController;

@RestController
@RequestMapping("/api")
public class RoleController extends GeneralController {

	private final RoleService roleService;

	@Autowired
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/roles")
	public Collection<RoleResponse> findAll() {
		Subject currentUser = SecurityUtils.getSubject();

		if (currentUser.hasRole("ADMIN")) {
			List<Role> roles = roleService.findAll();

			if (CollectionUtils.isNotEmpty(roles)) {
				return roles.stream().map(user -> new RoleResponse(user)).collect(Collectors.toSet());
			}
		}

		return CollectionUtils.EMPTY_COLLECTION;
	}

	@GetMapping("/roles/{id}")
	public RoleResponse findOne(@PathVariable int id) {
		Role role = roleService.findOne(id);

		if (role != null) {
			return new RoleResponse(role);
		}

		return null;
	}

	@PostMapping("/roles")
	public RoleResponse insertRole(@RequestBody RoleRequest roleRequest) {
		Role role = roleService.insert(roleRequest);

		return new RoleResponse(role);
	}

}
