package com.sap.csc.service.user.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.csc.app.model.dto.request.RoleRequest;
import com.sap.csc.app.model.dto.request.UserRequest;
import com.sap.csc.app.model.jpa.Department;
import com.sap.csc.app.model.jpa.Role;
import com.sap.csc.app.model.jpa.User;
import com.sap.csc.app.persistence.repository.user.DepartmentRepository;
import com.sap.csc.app.persistence.repository.user.RoleRepository;
import com.sap.csc.app.persistence.repository.user.UserRepository;
import com.sap.csc.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final DepartmentRepository departmentRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
			DepartmentRepository departmentRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.departmentRepository = departmentRepository;
	}

	@Override
	public User insertUser(UserRequest userRequest) {
		User user = new User();
		Integer departmentId = userRequest.getDepartment();

		if (departmentId != null) {
			Department department = departmentRepository.findOne(departmentId);

			if (department != null) {
				user.setDepartment(department);
			}
		}
		user.setUsername(userRequest.getUsername());
		user.setPassword(userRequest.getPassword());

		return userRepository.saveAndFlush(user);
	}

	@Override
	public User updateUser(UserRequest userRequest) {
		User user = userRepository.findOne(userRequest.getId());

		if (user != null) {
			List<RoleRequest> roleRequests = userRequest.getRoles();

			if (CollectionUtils.isNotEmpty(roleRequests)) {
				Set<Integer> roleIds = new HashSet<>();

				for (RoleRequest roleRequest : roleRequests) {
					roleIds.add(roleRequest.getId());
				}
				List<Role> roles = roleRepository.findByIds(roleIds);

				if (CollectionUtils.isNotEmpty(roles)) {
					user.setRoles(roles);
				}
			}

			user.setUsername(userRequest.getUsername());
			user.setPassword(userRequest.getPassword());

			return userRepository.saveAndFlush(user);
		}

		return null;
	}

	@Override
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findByRoleId(int roleId) {
		return userRepository.findByRoleId(roleId);
	}

	@Override
	public List<User> findByDepartment(Subject currentUser) {
		String username = currentUser.getPrincipals().getPrimaryPrincipal().toString();
		User user = userRepository.findByUsername(username);
		
		if(user != null) {
			return userRepository.findByDepartmentId(user.getDepartment().getId());
		}
		
		return null;
	}

}
