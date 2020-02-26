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
@Table(name = "tbl_posts_files", schema = "bay_col")
public class TblPostFile {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@Column(name = "filename", nullable = false)
	private String filename;
	
	@Column(name = "size", nullable = false)
	private Long size;
	
	@Column(name = "content_type", nullable = false)
	private String content_type;
	
	@Column(name = "id_post_customer", nullable = false)
	private Long idPostCustomer;

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

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Long getSize() {
		return size;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public String getContent_type() {
		return content_type;
	}

	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}

	public Long getIdPostCustomer() {
		return idPostCustomer;
	}

	public void setIdPostCustomer(Long idPostCustomer) {
		this.idPostCustomer = idPostCustomer;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	
}