package com.sap.csc.app.persistence.repository.user.impl;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.csc.app.model.jpa.Role;
import com.sap.csc.app.persistence.repository.user.RoleRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class RoleRepositoryImpl extends SimpleJpaRepository<Role, Integer> implements RoleRepository {

	@Autowired
	public RoleRepositoryImpl(EntityManager em) {
		super(Role.class, em);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findByUsername(String username) {
		return super.findAll((root, query, cb) -> {
			return cb.equal(root.get("username"), username);
		});
	}

	@Override
	@Transactional(readOnly = true)
	public List<Role> findByIds(Collection<Integer> roleIds) {
		return super.findAll((root, query, cb) -> {
			return root.get("id").in(roleIds);
		});
	}

}
