package com.sap.csc.app.persistence.repository.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.csc.app.model.jpa.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	List<User> findByRoleId(int roleId);

	List<User> findByDepartmentId(int id);
	
}
