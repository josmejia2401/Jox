package com.bay.ms.notification.event;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.bay.common.dto.EmailRegisterDTO;

@SuppressWarnings("serial")
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final Locale locale;
    private final EmailRegisterDTO user;

    public OnRegistrationCompleteEvent(final EmailRegisterDTO user, final Locale locale) {
        super(user);
        this.user = user;
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }

    public EmailRegisterDTO getUser() {
        return user;
    }

}