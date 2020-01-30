package com.bay.common.dto;

import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;

import com.bay.common.validators.EmailAnotation;

public class UserDTO {
	//@NotNull(message = "Please provide a id")
	private Long id;
	
	@NotEmpty(message = "Please provide a fullName")
	private String fullName;
	
	//@NotEmpty(message = "Please provide a dateOfBirth")
	private LocalDateTime dateOfBirth;

	//@Email
	@EmailAnotation(email = "correo electrónico")
	@NotEmpty(message = "Please provide a email")
	private String email;
	
	@NotEmpty(message = "Please provide a username")
	private String username;
	
	@NotEmpty(message = "Please provide a password")
	private String password;

	public UserDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public LocalDateTime getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDateTime dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
