package com.bay.ms.notification.services.impl;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bay.common.dto.FirebaseRequest;
import com.bay.common.dto.FirebaseResponse;
import com.bay.common.dto.PushNotificationRequest;
import com.bay.common.exceptions.CustomException;
import com.bay.ms.notification.interceptor.HeaderRequestInterceptor;
import com.bay.ms.notification.services.PushService;

@Service
public class PushServiceImpl implements PushService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PushServiceImpl.class);

	@Value("${app.notifications.firebase.server_url}")
	private String serverUrl;

	@Value("${app.notifications.firebase.key}")
	private String serverKey;

	@Override
	public FirebaseResponse sendDataNotification(PushNotificationRequest data) throws CustomException {
		try {
			final FirebaseRequest firebase = new FirebaseRequest();
			firebase.setData(data);
			HttpEntity<FirebaseRequest> request = new HttpEntity<>(firebase);
			CompletableFuture<FirebaseResponse> pushNotification = this.send(request);
			CompletableFuture.allOf(pushNotification).join();
			FirebaseResponse firebaseResponse = pushNotification.get();
			return firebaseResponse;
		} catch (Exception e) {
			LOGGER.error("ERROR, se ha generado al enviar la notificacion data.", e);
			throw new CustomException(e.getMessage(), e);
		}
	}

	@Override
	public FirebaseResponse sendNotification(PushNotificationRequest data) throws CustomException {
		try {
			final FirebaseRequest firebase = new FirebaseRequest();
			firebase.setNotification(data);
			HttpEntity<FirebaseRequest> request = new HttpEntity<>(firebase);
			CompletableFuture<FirebaseResponse> pushNotification = this.send(request);
			CompletableFuture.allOf(pushNotification).join();
			FirebaseResponse firebaseResponse = pushNotification.get();
			return firebaseResponse;
		} catch (Exception e) {
			LOGGER.error("ERROR, se ha generado al enviar la notificacion.", e);
			throw new CustomException(e.getMessage(), e);
		}
	}

	@Async
	public CompletableFuture<FirebaseResponse> send(HttpEntity<?> entity) {
		RestTemplate restTemplate = new RestTemplate();
		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + serverKey));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);
		FirebaseResponse firebaseResponse = restTemplate.postForObject(serverUrl, entity, FirebaseResponse.class);
		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
