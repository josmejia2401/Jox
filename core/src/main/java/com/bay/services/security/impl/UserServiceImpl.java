package com.bay.services.security.impl;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bay.common.dto.EmailRegisterDTO;
import com.bay.common.dto.UserDTO;
import com.bay.common.exceptions.BayException;
import com.bay.common.exceptions.NotFoundException;
import com.bay.entity.entities.UserEntity;
import com.bay.repositories.security.UserRepository;
import com.bay.services.security.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserDTO signIn(String username, String password) {
		LOGGER.debug("Data de entrada {} - {}", username, password);
		if (username != null && password != null) {
			return userRepo.signIn(username, password).map(x -> {
				UserDTO dto = modelMapper.map(x, UserDTO.class);
				dto.setPassword("");
				return dto;
			}).orElseThrow(() -> new NotFoundException(username));
		} else {
			throw new BayException("User or password invalid.");
		}
	}

	@Override
	public UserDTO signUp(UserDTO user) {
		LOGGER.debug("Data de entrada {}", user);
		if (user != null) {
			UserEntity entity = modelMapper.map(user, UserEntity.class);
			entity = userRepo.save(entity);
			final UserDTO dto = modelMapper.map(entity, UserDTO.class);
			dto.setPassword("");
			CompletableFuture.supplyAsync(() -> {
				enviarCodigoActivacion(dto);
				return "";
			});

			return dto;
		} else {
			throw new BayException("User or password invalid.");
		}
	}

	private void enviarCodigoActivacion(final UserDTO dto) {
		try {
			// RestTemplate restTemplate = new RestTemplate();
			EmailRegisterDTO email = new EmailRegisterDTO();
			email.setTo(dto.getEmail());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EmailRegisterDTO> entity = new HttpEntity<>(email, headers);
			String fooResourceUrl = "http://localhost:8081/ms-notification-dev/ms/notification/account/code-activation";
			// ResponseEntity<EmailDTO> response =
			// restTemplate.postForEntity(fooResourceUrl, entity, EmailDTO.class);
			restTemplate.postForEntity(fooResourceUrl, entity, EmailRegisterDTO.class);
		} catch (Exception e) {
			LOGGER.error("enviarCodigoActivacion", e);
		}
	}

}
