package com.bay.common.dto.filemananer;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class FileDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotEmpty(message="please provide an username")
	private String username;
	
	public FileDTO() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
