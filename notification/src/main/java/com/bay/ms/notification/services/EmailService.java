package com.bay.ms.notification.services;

import com.bay.common.dto.EmailDTO;

public interface EmailService {
	void send(EmailDTO email);
}
