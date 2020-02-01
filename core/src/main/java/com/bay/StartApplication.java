package com.bay;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.bay.common.dto.core.LocationDTO;
import com.bay.entity.core.TblLocation;

@SpringBootApplication
@EnableAsync
//@EnableWebSecurity(debug = true)
//@ComponentScan({"com.bay"})
//@EntityScan("com.bay.entities")
//@EnableJpaRepositories("com.bay.repositories")
public class StartApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(StartApplication.class);

    
    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }
    
    @Bean
    public ModelMapper modelMapper() {
    	ModelMapper modelMapper = new ModelMapper();
    	modelMapper.addMappings(new PropertyMap<TblLocation, LocationDTO>() {
            @Override
            protected void configure() {
            	if (source != null && source.getLocation() != null) {
            		Float[] location = new Float[]{(float) source.getLocation().x, (float) source.getLocation().y};
            		destination.setLocation(location);
                //skip(destination.getLocations());
            	}
            }
        });
        return new ModelMapper();
    }
    
    @Override
    public void run(String... args) {
        log.info("StartApplication...");
    }

}