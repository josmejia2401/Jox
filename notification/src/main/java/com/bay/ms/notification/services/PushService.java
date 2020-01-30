package com.bay.ms.notification.services;

import com.bay.common.dto.FirebaseResponse;
import com.bay.common.dto.PushNotificationRequest;
import com.bay.common.exceptions.CustomException;

public interface PushService {
	FirebaseResponse sendNotification(PushNotificationRequest request) throws CustomException;
	FirebaseResponse sendDataNotification(PushNotificationRequest request) throws CustomException;
}
