package com.bay.common.dto.core;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RecoverAccountDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "{validation.email.notempty}")
	@Email
	private String email;
	
	@NotEmpty(message = "{validation.password.notempty}")
	private String password;
	
	@NotEmpty(message = "{validation.token.notempty}")
	private String token;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
