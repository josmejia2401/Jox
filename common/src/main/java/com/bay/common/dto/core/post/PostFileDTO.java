package com.bay.common.dto.core.post;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PostFileDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;

	private String filename;
	
	private String fileUri;
	
	private Long size;
	
	private String contentType;
	
	private Long idPostCustomer;

	private LocalDateTime created;

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

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String content_type) {
		this.contentType = content_type;
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

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	
}