package com.bay.common.dto.master;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

public class MasterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;

	private String code;

	private String description;

	private LocalDateTime created;

	private Set<DetailDTO> details;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public Set<DetailDTO> getDetails() {
		return details;
	}

	public void setDetails(Set<DetailDTO> details) {
		this.details = details;
	}
}
