package com.bay.common.dto.core.post;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;


public class PostCustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	@NotEmpty(message = "{validation.post.text.notempty}")
	private String text;
	private String[] filesNames;
	@NotEmpty(message = "{validation.post.idCustomer.notempty}")
	private Long idCustomer;
	private LocalDateTime created;

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