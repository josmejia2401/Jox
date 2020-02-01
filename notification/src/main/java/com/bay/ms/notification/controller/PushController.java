package com.bay.ms.notification.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bay.common.dto.notification.FirebaseResponse;
import com.bay.common.dto.notification.PushNotificationRequest;
import com.bay.ms.notification.facade.PushFacade;

@RestController
@RequestMapping("ms/notification/push")
@Validated
@CrossOrigin(origins = "*")
public class PushController {

	@Autowired
	private PushFacade pushFacade;

	@PostMapping("/send/notification")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> sendNotification(@Valid @RequestBody PushNotificationRequest request) {
		FirebaseResponse firebaseResponse = this.pushFacade.sendNotification(request);
		if (firebaseResponse.getSuccess() == 1) {
			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(firebaseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/send/data")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> sendDataNotification(@Valid @RequestBody PushNotificationRequest request) {
		FirebaseResponse firebaseResponse = this.pushFacade.sendDataNotification(request);
		if (firebaseResponse.getSuccess() == 1) {
			return new ResponseEntity<>(firebaseResponse, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(firebaseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
