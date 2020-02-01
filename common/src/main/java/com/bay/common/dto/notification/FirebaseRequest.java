package com.bay.common.dto.notification;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FirebaseRequest {

	private String to;
	private String priority;
	private PushNotificationRequest notification;
	private Object data;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public Object getNotification() {
		return notification;
	}

	public void setNotification(PushNotificationRequest notification) {
		this.notification = notification;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}