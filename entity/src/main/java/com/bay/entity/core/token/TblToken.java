package com.bay.entity.core.token;

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
	private LocalDateTime expiryDate;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "type_token", nullable = false)
	private String typeToken;

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

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiry_date) {
		this.expiryDate = expiry_date;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTypeToken() {
		return typeToken;
	}

	public void setTypeToken(String typeToken) {
		this.typeToken = typeToken;
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