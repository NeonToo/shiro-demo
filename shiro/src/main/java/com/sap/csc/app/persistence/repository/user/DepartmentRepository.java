package com.sap.csc.app.persistence.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sap.csc.app.model.jpa.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

	Department findByName(String name);

}
