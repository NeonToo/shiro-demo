package com.sap.csc.app.persistence.repository.user.impl;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.csc.app.model.jpa.Department;
import com.sap.csc.app.persistence.repository.user.DepartmentRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class DepartmentRepositoryImpl extends SimpleJpaRepository<Department, Integer> implements DepartmentRepository {

	@Autowired
	public DepartmentRepositoryImpl(EntityManager em) {
		super(Department.class, em);
	}

	@Override
	public Department findByName(String name) {
		return super.findOne((root, query, cb) -> {
			return cb.equal(root.get("name"), name);
		});
	}
	
}
