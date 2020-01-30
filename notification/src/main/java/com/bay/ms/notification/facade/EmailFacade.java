package com.bay.ms.notification.facade;

import com.bay.common.dto.EmailDTO;

public interface EmailFacade {
	void send(EmailDTO email);
}
