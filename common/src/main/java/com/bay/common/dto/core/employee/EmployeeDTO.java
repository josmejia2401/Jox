package com.bay.common.dto.core.employee;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.bay.common.dto.master.DetailDTO;
import com.bay.common.validators.EmailAnotation;

public class EmployeeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	@NotEmpty(message = "Please provide a fullName")
	private String fullName;
	
	private LocalDateTime dateCreated;
	
	@NotEmpty(message = "Please provide a fullName")
	private String username;
	
	@NotEmpty(message = "Please provide a password")
	private String password;
	
	@EmailAnotation(email = "correo electrónico")
	@NotEmpty(message = "Please provide a email")
	private String email;
	
	private DetailDTO gender;	
	Set<DetailDTO> typesOfEmployees = new HashSet<>();
	private LocalDateTime created;

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

	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public DetailDTO getGender() {
		return gender;
	}

	public void setGender(DetailDTO gender) {
		this.gender = gender;
	}

	public Set<DetailDTO> getTypesOfEmployees() {
		return typesOfEmployees;
	}

	public void setTypesOfEmployees(Set<DetailDTO> typesOfEmployees) {
		this.typesOfEmployees = typesOfEmployees;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	
}