package com.bay.common.dto.security;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.bay.common.dto.core.employee.EmployeeDTO;
import com.bay.common.dto.core.location.LocationDTO;
import com.bay.common.dto.master.DetailDTO;


public class CustomerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotEmpty(message = "Please provide a username")
	private String fullName;

	private LocalDateTime dateCreated;

	@NotEmpty(message = "Please provide a username")
	private String username;

	@NotEmpty(message = "Please provide a password")
	private String password;

	@NotEmpty(message = "Please provide a email")
	private String email;
	
	@NotEmpty(message = "Please provide a phone")
	private String phone;

	private String status;
	
	private List<LocationDTO> locations;

	private List<EmployeeDTO> employees;
	
	private List<DetailDTO> typesOfCustomers;
	
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<LocationDTO> getLocations() {
		return locations;
	}

	public void setLocations(List<LocationDTO> locations) {
		this.locations = locations;
	}

	public List<EmployeeDTO> getEmployees() {
		return employees;
	}

	public void setEmployees(List<EmployeeDTO> employees) {
		this.employees = employees;
	}

	public List<DetailDTO> getTypesOfCustomers() {
		return typesOfCustomers;
	}

	public void setTypesOfCustomers(List<DetailDTO> typesOfCustomers) {
		this.typesOfCustomers = typesOfCustomers;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}