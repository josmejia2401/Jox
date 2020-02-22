package com.bay.entity.core.post;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_posts_customers", schema = "bay_col")
public class TblPostCustomer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "text", nullable = false)
	private String text;

	@Column(name = "files_names", nullable = true, columnDefinition = "text[]")
	private String[] filesNames;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getFilesNames() {
		return filesNames;
	}

	public void setFilesNames(String[] filesNames) {
		this.filesNames = filesNames;
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