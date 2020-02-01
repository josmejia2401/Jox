package com.bay.common.dto.notification;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;

public class EmailDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	private Long id;
	
	private String from;
	
	@NotEmpty(message = "Please provide a to")
	private List<String> to;
	
	@NotEmpty(message = "Please provide a body")
	private String body;
	
	@NotEmpty(message = "Please provide a subject")
	private String subject;
	
	private List<AttachmentDTO> attachment;
	private boolean html;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<AttachmentDTO> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<AttachmentDTO> attachment) {
		this.attachment = attachment;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}


}
