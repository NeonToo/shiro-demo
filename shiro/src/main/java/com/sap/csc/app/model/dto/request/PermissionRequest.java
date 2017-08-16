package com.sap.csc.app.model.dto.request;

import java.io.Serializable;

import com.sap.csc.app.model.enumeration.ResourceType;
import com.sap.csc.app.model.enumeration.RestrictionType;

public class PermissionRequest implements Serializable {

	private static final long serialVersionUID = 796052779510144893L;
	
	private int id;
	
	private ResourceType resource;
	
	private RestrictionType read;
	
	private RestrictionType write;

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
