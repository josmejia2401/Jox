package com.bay.common.dto.notification;

import java.io.Serializable;
import java.util.List;

public class EmailSendDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> to;
	
	private String body;
	
	private String subject;
	
	private List<AttachmentDTO> attachment;
	
	private boolean html;

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
