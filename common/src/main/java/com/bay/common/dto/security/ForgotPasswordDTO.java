package com.bay.common.dto.security;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ForgotPasswordDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "{validation.email.notempty}")
	@Email
	private String email;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}	
}
