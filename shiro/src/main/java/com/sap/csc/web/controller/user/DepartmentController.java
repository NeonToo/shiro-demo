package com.sap.csc.web.controller.user;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sap.csc.app.model.dto.response.DepartmentResponse;
import com.sap.csc.app.model.jpa.Department;
import com.sap.csc.service.user.DepartmentService;
import com.sap.csc.web.controller.GeneralController;

@RestController
@RequestMapping("/api")
public class DepartmentController extends GeneralController {

	private final DepartmentService departmentService;

	@Autowired
	public DepartmentController(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/departments")
	public Collection<DepartmentResponse> findAll() {
		List<Department> departments = departmentService.findAll();

		if (CollectionUtils.isNotEmpty(departments)) {
			return departments.stream().map(department -> new DepartmentResponse(department))
					.collect(Collectors.toList());
		}

		return CollectionUtils.EMPTY_COLLECTION;
	}

}
