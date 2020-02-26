package com.bay.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bay.core.converter.StringToPostCustomerDTOConverter;


@Configuration
@PropertySource({"classpath:application.properties"})
@Profile("default")
public class SpringSecurityConfig implements WebMvcConfigurer {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToPostCustomerDTOConverter());
	}

}
