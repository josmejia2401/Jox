package com.bay.ms.notification.services;

import com.bay.common.dto.notification.EmailDTO;

public interface VerificationService {
	void createVerificationTokenForUser(EmailDTO email);
}
