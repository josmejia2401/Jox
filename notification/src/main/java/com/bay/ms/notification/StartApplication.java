package com.bay.ms.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableScheduling
@SpringBootApplication
public class StartApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartApplication.class);

    
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    
    @Override
    public void run(String... args) {
    	LOGGER.info("Start application...");
    }

}