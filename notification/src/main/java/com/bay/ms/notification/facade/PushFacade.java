package com.bay.ms.notification.facade;

import com.bay.common.dto.FirebaseResponse;
import com.bay.common.dto.PushNotificationRequest;
import com.bay.common.exceptions.CustomException;

public interface PushFacade {
	FirebaseResponse sendNotification(PushNotificationRequest request) throws CustomException;
	FirebaseResponse sendDataNotification(PushNotificationRequest request) throws CustomException;
}
