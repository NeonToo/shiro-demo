package com.sap.csc.app.model.enumeration;

public enum RestrictionType implements ScalableEnum {
	
	NO_RESTRICTION(0, "All"),
	DEPARTMENT(1, "Department"),
	OWNER(2, "Owner");
	
	private final int value;
	
	private final String description;
	
	RestrictionType(int value, String description) {
		this.value = value;
		this.description = description;
	}

	@Override
	public int getValue() {
		return value;
	}

	@Override
	public String getDescription() {
		return description;
	}

}
