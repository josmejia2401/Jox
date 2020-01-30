package com.bay.common.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class EmailRegisterDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@NotEmpty(message = "Please provide a to")
	private String to;

	private boolean html;

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}
}
