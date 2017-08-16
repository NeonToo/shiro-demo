package com.sap.csc.service.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sap.csc.app.model.dto.request.DepartmentRequest;
import com.sap.csc.app.model.jpa.Department;
import com.sap.csc.app.persistence.repository.user.DepartmentRepository;
import com.sap.csc.service.user.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	
	private final DepartmentRepository departmentRepository;
	
	@Autowired
	public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	@Override
	public Department insertDepartment(DepartmentRequest departmentRequest) {
		Department department = new Department();
		
		department.setName(departmentRequest.getName());
		return departmentRepository.saveAndFlush(department);
	}

	@Override
	public Department findOne(int id) {
		return departmentRepository.findOne(id);
	}

	@Override
	public Department findByName(String name) {
		return departmentRepository.findByName(name);
	}

	@Override
	public List<Department> findAll() {
		return departmentRepository.findAll();
	}

}
