package com.sap.csc.app.model.dto.response;

import java.io.Serializable;

import com.sap.csc.app.model.jpa.Department;

public class DepartmentResponse implements Serializable {

	private static final long serialVersionUID = -4631858207929894244L;
	
	private int id;
	
	private String name;
	
	public DepartmentResponse(Department department) {
		this.id = department.getId();
		this.name = department.getName();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
