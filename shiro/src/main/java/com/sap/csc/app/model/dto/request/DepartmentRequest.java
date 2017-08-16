package com.sap.csc.app.model.dto.request;

import java.io.Serializable;

public class DepartmentRequest implements Serializable {

	private static final long serialVersionUID = 2076112105236961867L;
	
	private int id;
	
	private String name;

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
