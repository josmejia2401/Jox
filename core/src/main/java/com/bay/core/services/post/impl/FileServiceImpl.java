package com.bay.core.services.post.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.bay.common.dto.filemananer.FileDTO;
import com.bay.common.dto.filemananer.UploadFileResponse;
import com.bay.common.exceptions.FileManagerException;
import com.bay.common.util.MultipartInputStreamFileResource;
import com.bay.core.services.file.FileService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FileServiceImpl implements FileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public List<UploadFileResponse> add(FileDTO info, MultipartFile[] files) throws FileManagerException {
		try {
			if (mapper == null) {
				 mapper = new ObjectMapper();
			}
			final String targetUrl = "http://localhost:8082/ms-filemanager-dev/api/post/uploadFiles";			
			final MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			for (MultipartFile file : files) {
	            if (!file.isEmpty()) {
	            	body.add("file[]", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
	            }
	        }
			String jsonInString = mapper.writeValueAsString(info);
			body.add("data", jsonInString);
			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			ResponseEntity<UploadFileResponse[]> entityResponse = restTemplate.postForEntity(targetUrl, requestEntity, UploadFileResponse[].class);
			List<UploadFileResponse> x = Arrays.asList(entityResponse.getBody());
			return x;
		} catch (Exception e) {
			LOGGER.error("ERROR: FileServiceImpl.consumeWs", e);
			throw new FileManagerException(e);
		}
	}

	@Override
	public List<UploadFileResponse> delete(FileDTO info) {
		// TODO Auto-generated method stub
		return null;
	}
}
