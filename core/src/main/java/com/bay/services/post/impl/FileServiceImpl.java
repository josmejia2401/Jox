package com.bay.services.post.impl;

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

import com.bay.common.dto.core.post.PostCustomerDTO;
import com.bay.common.dto.file.FileDTO;
import com.bay.common.dto.file.UploadFileResponse;
import com.bay.common.exceptions.BayException;
import com.bay.common.exceptions.CustomException;
import com.bay.common.util.MessageUtil;
import com.bay.services.file.FileService;

@Service
public class FileServiceImpl implements FileService<PostCustomerDTO> {

	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Autowired
	private MessageUtil messageUtil;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<UploadFileResponse> add(FileDTO<PostCustomerDTO> file) {
		LOGGER.debug("FileServiceImpl.add start with data: {}", file);
		try {
			if (file != null && file.getData() != null) {
				List<UploadFileResponse> result = this.consumeWs(file);
				return result;
			} else {
				throw new BayException(messageUtil.getMessage("customer.signin.error.credential_invalid"));
			}
		} catch (BayException e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signIn.BayException", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signIn.Exception", e);
			throw new CustomException(messageUtil.getMessage("customer.signin.error.internal_failure"), e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.signIn finish");
		}
	}


	@Override
	public List<UploadFileResponse> delete(FileDTO<PostCustomerDTO> file) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UploadFileResponse> consumeWs(final FileDTO<PostCustomerDTO> dto) {
		try {
			 final String targetUrl = "";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			for (MultipartFile x : dto.getFiles()) {
				body.add("file[]", x);
			}
			body.add("data", dto.getData());
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
			ResponseEntity<List> entityResponse = restTemplate.postForEntity(targetUrl, requestEntity, List.class);
			return (List<UploadFileResponse> ) entityResponse.getBody();
		} catch (Exception e) {
			LOGGER.error("ERROR: FileServiceImpl.consumeWs", e);
			throw e;
		}
	}
	
}
