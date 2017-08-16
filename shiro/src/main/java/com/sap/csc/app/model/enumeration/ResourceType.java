package com.sap.csc.app.model.enumeration;

public enum ResourceType implements ScalableEnum {
	
	ALL(0, "All"),
	USER(1, "User"), 
	ROLE(2, "Role");
	
	private final int value;
	
	private final String description;
	
	ResourceType(int value, String description) {
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
	
	public static ResourceType valueOf(int value) {
		for(ResourceType candidate: values()) {
			if(candidate.value == value) {
				return candidate;
			}
		}
		return null;
	}
}
