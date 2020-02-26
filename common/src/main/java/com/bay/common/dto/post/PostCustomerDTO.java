package com.bay.common.dto.post;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


public class PostCustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	@NotEmpty(message = "{validation.post.text.notempty}")
	private String text;
	@NotNull(message = "{validation.post.idCustomer.notempty}")
	private Long idCustomer;
	private List<PostFileDTO> postFiles;
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

	public List<PostFileDTO> getPostFiles() {
		return postFiles;
	}

	public void setPostFiles(List<PostFileDTO> postFiles) {
		this.postFiles = postFiles;
	}
	
	
}