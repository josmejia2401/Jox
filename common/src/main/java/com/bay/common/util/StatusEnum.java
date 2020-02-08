package com.bay.common.util;

public enum StatusEnum {

	ACTIVE("A", "ACTIVE"),
	INACTIVE("I", "INACTIVE"),
	
	CREATED("C", "CREATED"),
	RECOVERED("R", "RECOVERED"),
	PENDING("P", "PENDING");
	
	private String code;
	private String name;
	
	private StatusEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
