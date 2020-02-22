package com.bay.common.util;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.bay.common.dto.notification.EmailSendDTO;

@Component
public class RestTemplateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateUtil.class);

	@Autowired
	private RestTemplate restTemplate;

	public EmailSendDTO send(final EmailSendDTO dto, final String targetUrl) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EmailSendDTO> entity = new HttpEntity<>(dto, headers);
			ResponseEntity<EmailSendDTO> entityResponse = restTemplate.postForEntity(targetUrl, entity, EmailSendDTO.class);
			return entityResponse.getBody();
		} catch (Exception e) {
			LOGGER.error("ERROR: RestTemplateUtil.sendAsync", e);
			throw e;
		}
	}

	public void sendAsync(final EmailSendDTO dto, final String targetUrl) {
		CompletableFuture.supplyAsync(() -> {
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<EmailSendDTO> entity = new HttpEntity<>(dto, headers);
				restTemplate.postForEntity(targetUrl, entity, EmailSendDTO.class);
			} catch (Exception e) {
				LOGGER.error("ERROR: RestTemplateUtil.sendAsync", e);
			}
			return "";
		});
	}
	
	public <T> T consumeWs(final T dto, final String targetUrl) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<T> entity = new HttpEntity<>(dto, headers);
			ResponseEntity<T> entityResponse = (ResponseEntity<T>) restTemplate.postForEntity(targetUrl, entity, Object.class);
			return entityResponse.getBody();
		} catch (Exception e) {
			LOGGER.error("ERROR: RestTemplateUtil.sendAsync", e);
			throw e;
		}
	}
}
