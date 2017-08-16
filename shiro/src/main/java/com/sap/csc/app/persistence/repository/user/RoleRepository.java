package com.sap.csc.app.persistence.repository.user;

import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.csc.app.model.jpa.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	List<Role> findByUsername(String username);

	List<Role> findByIds(Collection<Integer> roleIds);
	
}
