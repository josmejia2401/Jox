package com.bay.security;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration.AccessLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.bay.common.dto.core.LocationDTO;
import com.bay.entity.core.location.TblLocation;

@SpringBootApplication
@EnableAsync
public class StartApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
    
    @Bean
    public ModelMapper modelMapper() {
    	ModelMapper modelMapper = new ModelMapper();
    	modelMapper.getConfiguration().setMethodAccessLevel(AccessLevel.PUBLIC);
    	modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PRIVATE);
    	modelMapper.getConfiguration().setFieldAccessLevel(AccessLevel.PROTECTED);
    	modelMapper.addMappings(new PropertyMap<TblLocation, LocationDTO>() {
            @Override
            protected void configure() {
                //skip(destination.getLocations());
            }
        });
        return modelMapper;
    }
    
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
    
    @Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages_es_CO");
		messageSource.setDefaultEncoding("ISO-8859-1");
		return messageSource;
	}
    
    @Override
    public void run(String... args) {
        log.info("StartApplication...");
    }

}