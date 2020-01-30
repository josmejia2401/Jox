package com.bay.ms.notification.services;

import com.bay.common.dto.EmailDTO;

public interface VerificationService {
	void createVerificationTokenForUser(EmailDTO email);
}
