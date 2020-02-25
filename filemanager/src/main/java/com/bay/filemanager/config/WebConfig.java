package com.bay.filemanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.bay.filemanager.converter.StringToUserConverter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@PropertySource({"classpath:application.properties"})
@Profile("default")
public class WebConfig implements WebMvcConfigurer {

	@Bean
	public ObjectMapper modelMapper() {
		return new ObjectMapper();
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToUserConverter());
	}

}
