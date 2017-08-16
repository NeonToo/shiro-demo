package com.sap.csc.app.persistence.repository.user.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.csc.app.model.jpa.Role;
import com.sap.csc.app.model.jpa.User;
import com.sap.csc.app.persistence.repository.user.UserRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class UserRepositoryImpl extends SimpleJpaRepository<User, Long> implements UserRepository {

	@Autowired
	public UserRepositoryImpl(EntityManager em) {
		super(User.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByUsername(String username) {
		return super.findOne((root, query, cb) -> {
			return cb.equal(root.get("username"), username);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByRoleId(int roleId) {
		return super.findAll((root, query, cb) -> {
			Join<User, Role> userRole = root.join("roles", JoinType.LEFT);
			
			return cb.equal(userRole.get("id"), roleId);
		});
	}

	@Override
	public List<User> findByDepartmentId(int id) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get("department").get("id"), id);
		});
	}
	
}
