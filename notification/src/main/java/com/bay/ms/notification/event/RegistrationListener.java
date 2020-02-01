package com.bay.ms.notification.event;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.bay.common.dto.notification.EmailDTO;
import com.bay.common.dto.notification.EmailRegisterDTO;
import com.bay.common.exceptions.CustomException;
import com.bay.ms.notification.services.EmailService;
import com.bay.ms.notification.services.MailContentBuilder;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	@Autowired
	private EmailService service;
	
	@Autowired
	private MailContentBuilder mailContentBuilder;

	//@Autowired
	//MessageSource messageSource;

	@Override
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		final String token = UUID.randomUUID().toString();
		final EmailDTO email = constructEmailMessage(event, token);
		service.send(email);
	}

	private final EmailDTO constructEmailMessage(final OnRegistrationCompleteEvent event, final String token) {
		final EmailRegisterDTO user = event.getUser();
		if (user != null) {
			List<String> to = new ArrayList<>();
			EmailDTO e = new EmailDTO();
			e.setSubject("Registration Confirmation");
			String body = mailContentBuilder.build(token);
			e.setBody(body);
			to.add(user.getTo());
			e.setTo(to);
			e.setHtml(true);
			return e;
		} else {
			String message = "";//messageSource.getMessage("error.notfound", null, event.getLocale());
			throw new CustomException(message);
		}
	}

}