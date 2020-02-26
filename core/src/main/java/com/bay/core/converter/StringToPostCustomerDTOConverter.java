package com.bay.core.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bay.common.dto.core.post.PostCustomerDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StringToPostCustomerDTOConverter implements Converter<String, PostCustomerDTO> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PostCustomerDTO convert(String source) {
    	try {
    		if (objectMapper == null) {
    			objectMapper = new ObjectMapper();
    		}
    		PostCustomerDTO user = objectMapper.readValue(source, PostCustomerDTO.class);
    		return user;
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
    	return null;
    }
}
