package com.bay.services.customer.impl;

import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.notification.EmailRegisterDTO;
import com.bay.common.exceptions.BayException;
import com.bay.common.exceptions.NotFoundException;
import com.bay.entity.core.TblCustomer;
import com.bay.repositories.customer.CustomerRepository;
import com.bay.services.customer.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CustomerDTO signIn(String username, String password) {
		LOGGER.debug("Data de entrada {} - {}", username, password);
		
		modelMapper.addMappings(new PropertyMap<TblCustomer, CustomerDTO>() {
            @Override
            protected void configure() {
                skip(destination.getLocations());
            }
        });
		if (username != null && password != null) {
			return userRepo.signIn(username, password).map(x -> {
				CustomerDTO dto = modelMapper.map(x, CustomerDTO.class);
				dto.setPassword("");
				return dto;
			}).orElseThrow(() -> new NotFoundException(username));
		} else {
			throw new BayException("User or password invalid.");
		}
	}

	@Override
	public CustomerDTO signUp(CustomerDTO user) {
		LOGGER.debug("Data de entrada {}", user);
		if (user != null) {
			TblCustomer entity = modelMapper.map(user, TblCustomer.class);
			entity = userRepo.save(entity);
			final CustomerDTO dto = modelMapper.map(entity, CustomerDTO.class);
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

	private void enviarCodigoActivacion(final CustomerDTO dto) {
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
