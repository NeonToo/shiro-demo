package com.sap.csc.service.user;

import java.util.List;

import com.sap.csc.app.model.dto.request.DepartmentRequest;
import com.sap.csc.app.model.jpa.Department;

public interface DepartmentService {

	Department insertDepartment(DepartmentRequest departmentRequest);
	
	Department findOne(int id);
	
	Department findByName(String name);
	
	List<Department> findAll();
	
}
