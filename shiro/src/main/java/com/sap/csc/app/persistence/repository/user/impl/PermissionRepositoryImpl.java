package com.sap.csc.app.persistence.repository.user.impl;

import java.util.List;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sap.csc.app.model.jpa.Permission;
import com.sap.csc.app.persistence.repository.user.PermissionRepository;

@Repository
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@Transactional
public class PermissionRepositoryImpl extends SimpleJpaRepository<Permission, Long> implements PermissionRepository {
	
	private final EntityManager em;
	
	@Autowired
	public PermissionRepositoryImpl(EntityManager em) {
		super(Permission.class, em);
		this.em = em;
	}

	@Override
	public List<Permission> batchInsert(List<Permission> permissions) {
		final List<Permission> result = super.save(permissions);
		
		em.flush();
		return result;
	}

}
