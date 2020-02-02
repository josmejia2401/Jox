package com.bay.entity.core;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_tokens", schema = "bay_col")
public class TblToken {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "token", nullable = false)
	private String token;

	@Column(name = "expiry_date", nullable = false)
	private LocalDateTime expiry_date;

	@Column(name = "state", nullable = false)
	private String state;

	@Column(name = "type_token", nullable = false)
	private String type_token;

	@Column(name = "id_customer", nullable = false)
	private Long idCustomer;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiry_date() {
		return expiry_date;
	}

	public void setExpiry_date(LocalDateTime expiry_date) {
		this.expiry_date = expiry_date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType_token() {
		return type_token;
	}

	public void setType_token(String type_token) {
		this.type_token = type_token;
	}

	public Long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(Long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
}