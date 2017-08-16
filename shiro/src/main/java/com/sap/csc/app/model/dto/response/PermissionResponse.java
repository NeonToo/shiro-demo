package com.sap.csc.app.model.dto.response;

import java.io.Serializable;

import com.sap.csc.app.model.enumeration.ResourceType;
import com.sap.csc.app.model.enumeration.RestrictionType;
import com.sap.csc.app.model.jpa.Permission;

public class PermissionResponse implements Serializable {

	private static final long serialVersionUID = -1750808852688155123L;

	private int id;

	private ResourceType resource;

	private RestrictionType read;

	private RestrictionType write;

	public PermissionResponse(Permission permission) {
		this.id = permission.getId();
		this.resource = permission.getResource();
		this.read = permission.getReadRestriction();
		this.write = permission.getWriteRestriction();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ResourceType getResource() {
		return resource;
	}

	public void setResource(ResourceType resource) {
		this.resource = resource;
	}

	public RestrictionType getRead() {
		return read;
	}

	public void setRead(RestrictionType read) {
		this.read = read;
	}

	public RestrictionType getWrite() {
		return write;
	}

	public void setWrite(RestrictionType write) {
		this.write = write;
	}

}
