package com.bay.common.dto;

import javax.validation.constraints.NotEmpty;

public class PushNotificationRequest {
	
	@NotEmpty(message = "Please provide a title")
    private String title;
	@NotEmpty(message = "Please provide a message")
    private String message;
	@NotEmpty(message = "Please provide a topic")
    private String topic;
	@NotEmpty(message = "Please provide a token")
    private String token;

    public PushNotificationRequest() {
    }

    public PushNotificationRequest(String title, String messageBody, String topicName) {
        this.title = title;
        this.message = messageBody;
        this.topic = topicName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
