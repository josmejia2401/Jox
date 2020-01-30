package com.bay.ms.notification.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bay.common.dto.EmailDTO;
import com.bay.ms.notification.facade.EmailFacade;
import com.bay.ms.notification.services.EmailService;

@Service
public class EmailFacadeImpl implements EmailFacade {

	@Autowired
	private EmailService userService;

	@Override
	public void send(EmailDTO email) {
		userService.send(email);
	}

}
