	package com.bay.ms.notification.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
	public class MailContentBuilder {
	 
	    private TemplateEngine templateEngine;
	 
	    @Autowired
	    public MailContentBuilder(TemplateEngine templateEngine) {
	        this.templateEngine = templateEngine;
	    }
	 
	    public String build(String url) {
	        Context context = new Context();
	        context.setVariable("url", url);
	        return templateEngine.process("mailTemplate", context);
	    }
	 
	}