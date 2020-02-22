package com.bay.entity.core.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.bay.entity.master.TblDetail;

@Entity
@Table(name = "tbl_users", schema = "bay_col", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class TblUser {

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

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_gender", referencedColumnName = "id")
	private TblDetail gender;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(schema = "bay_col", name = "tbl_rel_emp_tbl_details", joinColumns = {
			@JoinColumn(name = "id_user") }, inverseJoinColumns = { @JoinColumn(name = "id_detail") })
	List<TblDetail> typesOfEmployees;
	
	@Column(name = "created", nullable = true)
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

	public TblDetail getGender() {
		return gender;
	}

	public void setGender(TblDetail gender) {
		this.gender = gender;
	}

	public List<TblDetail> getTypesOfEmployees() {
		return typesOfEmployees;
	}

	public void setTypesOfEmployees(List<TblDetail> typesOfEmployees) {
		this.typesOfEmployees = typesOfEmployees;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	// @ManyToMany(mappedBy = "users")
	// private Set<TblCustomer> customers = new HashSet<>();

	
	
}