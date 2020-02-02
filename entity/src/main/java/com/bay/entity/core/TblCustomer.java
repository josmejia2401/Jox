package com.bay.entity.core;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bay.entity.master.TblDetail;

@Entity
@Table(name = "tbl_customers", schema = "bay_col", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class TblCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "full_name", nullable = false, unique = false)
	private String fullName;

	@Column(name = "date_created", nullable = true, unique = false)
	private LocalDateTime dateCreated;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinColumn(name = "id_customer", referencedColumnName = "id")
	private List<TblLocation> locations;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(schema = "bay_col", name = "tbl_rel_cust_emp", joinColumns = {
			@JoinColumn(name = "id_customer") }, inverseJoinColumns = { @JoinColumn(name = "id_user") })
	private List<TblUser> employees;

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	@JoinTable(schema = "bay_col", name = "tbl_rel_cust_tbl_details", joinColumns = {
			@JoinColumn(name = "id_customer") }, inverseJoinColumns = { @JoinColumn(name = "id_detail") })
	private List<TblDetail> typesOfCustomers;

	@Column(name = "created")
	private LocalDateTime created;

	@PrePersist
	void preInsert() {
	   if (this.created == null) {
	       this.created = LocalDateTime.now();
	   }
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

	public List<TblLocation> getLocations() {
		return locations;
	}

	public void setLocations(List<TblLocation> locations) {
		this.locations = locations;
	}

	public List<TblUser> getEmployees() {
		return employees;
	}

	public void setEmployees(List<TblUser> employees) {
		this.employees = employees;
	}

	public List<TblDetail> getTypesOfCustomers() {
		return typesOfCustomers;
	}

	public void setTypesOfCustomers(List<TblDetail> typesOfCustomers) {
		this.typesOfCustomers = typesOfCustomers;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

}