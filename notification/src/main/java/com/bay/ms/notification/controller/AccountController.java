package com.bay.ms.notification.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bay.common.dto.EmailRegisterDTO;
import com.bay.ms.notification.event.OnRegistrationCompleteEvent;

@RestController
@RequestMapping("ms/notification/account")
@Validated
@CrossOrigin(origins = "*")
public class AccountController {

	@Autowired
    private ApplicationEventPublisher eventPublisher;

	@PostMapping("/code-activation")
	@ResponseStatus(HttpStatus.OK)
	EmailRegisterDTO sigIn(@Valid @RequestBody EmailRegisterDTO email, final HttpServletRequest request) {
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(email, request.getLocale()));
		return email;
	}
}
