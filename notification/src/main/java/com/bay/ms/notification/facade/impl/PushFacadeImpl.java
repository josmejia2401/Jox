package com.bay.ms.notification.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bay.common.dto.FirebaseResponse;
import com.bay.common.dto.PushNotificationRequest;
import com.bay.common.exceptions.CustomException;
import com.bay.ms.notification.facade.PushFacade;
import com.bay.ms.notification.services.PushService;

@Service
public class PushFacadeImpl implements PushFacade {

	@Autowired
	private PushService pushService;

	@Override
	public FirebaseResponse sendNotification(PushNotificationRequest request) throws CustomException {
		return this.pushService.sendNotification(request);
	}

	@Override
	public FirebaseResponse sendDataNotification(PushNotificationRequest request) throws CustomException{
		return this.pushService.sendDataNotification(request);
	}

}
