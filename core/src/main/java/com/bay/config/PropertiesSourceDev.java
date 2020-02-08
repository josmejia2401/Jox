package com.bay.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySource({ "classpath:application-dev.properties" })
@Profile("dev")
public class PropertiesSourceDev extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
	  public Executor taskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    executor.setCorePoolSize(2);
	    executor.setMaxPoolSize(2);
	    executor.setQueueCapacity(500);
	    executor.setThreadNamePrefix("GithubLookup-");
	    executor.initialize();
	    return executor;
	  }
	
	
}
