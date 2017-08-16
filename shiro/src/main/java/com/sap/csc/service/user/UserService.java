package com.sap.csc.service.user;

import java.util.List;

import org.apache.shiro.subject.Subject;

import com.sap.csc.app.model.dto.request.UserRequest;
import com.sap.csc.app.model.jpa.User;

public interface UserService {
	
	User findOne(Long id);

	User findByUsername(String username);

	User insertUser(UserRequest userRequest);

	User updateUser(UserRequest userRequest);
	
	List<User> findAll();
	
	List<User> findByRoleId(int roleId);

	List<User> findByDepartment(Subject currentUser);
}
