package com.bay.filemanager.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.bay.common.dto.filemananer.FileDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StringToUserConverter implements Converter<String, FileDTO> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public FileDTO convert(String source) {
    	try {
    		if (objectMapper == null) {
    			objectMapper = new ObjectMapper();
    		}
    		FileDTO user = objectMapper.readValue(source, FileDTO.class);
    		return user;
    	} catch (Exception e) {
    		e.printStackTrace();
    	} 
    	return null;
    }
}
