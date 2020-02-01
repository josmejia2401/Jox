package com.bay.ms.notification.facade;

import com.bay.common.dto.notification.EmailDTO;

public interface EmailFacade {
	void send(EmailDTO email);
}
