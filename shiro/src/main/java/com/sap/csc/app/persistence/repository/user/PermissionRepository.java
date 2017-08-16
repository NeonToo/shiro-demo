package com.sap.csc.app.persistence.repository.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.csc.app.model.jpa.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	List<Permission> batchInsert(List<Permission> permissions);

}
