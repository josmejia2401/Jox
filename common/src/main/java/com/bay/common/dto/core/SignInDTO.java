package com.bay.common.dto.core;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignInDTO {
	@NotEmpty(message = "Please provide a username")
	@Size(min=4, max=12, message = "Username: min 4 y max 12") 
    private String username;
	
	@NotEmpty(message = "Please provide a password")
	//@Min(8) @Max(16)
	@Size(min=8, max=12, message = "Password: min 4 y max 12")
    private String password;
    
	/*
	 * @DateTimeFormat(pattern="MM/dd/yyyy")
    	@NotNull @Past
    */
    public SignInDTO() {
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}
