package com.bay.common.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

	private Locale locale = new Locale("es_CO");
	@Autowired
	private MessageSource message;
	
	public String getMessage(String code) {
		return message.getMessage(code,  null, locale);
	}
	
	public String getMessage(String code, String ... params) {
		return message.getMessage(code, params, locale);
	}
}
