package com.bay.services.customer.impl;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.core.ForgotPasswordDTO;
import com.bay.common.dto.core.VerifyAccountDTO;
import com.bay.common.dto.notification.EmailRegisterDTO;
import com.bay.common.exceptions.BayException;
import com.bay.common.exceptions.CustomException;
import com.bay.common.exceptions.NotFoundException;
import com.bay.common.util.Commons;
import com.bay.entity.core.TblCustomer;
import com.bay.entity.core.TblToken;
import com.bay.repositories.customer.CustomerRepository;
import com.bay.repositories.customer.TokenRepository;
import com.bay.services.customer.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository userRepo;

	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CustomerDTO signIn(String username, String password) {
		LOGGER.debug("Data de entrada {} - {}", username, password);
		try {
			if (username != null && password != null) {
				return userRepo.signIn(username, password).map(x -> {
					CustomerDTO dto = modelMapper.map(x, CustomerDTO.class);
					dto.setPassword("");
					return dto;
				}).orElseThrow(() -> new NotFoundException(username));
			} else {
				throw new BayException("User or password invalid.");
			}
		} catch (Exception e) {
			LOGGER.error("ERROR: No se pudo realizar la autenticación CustomerServiceImpl.signIn.Exception", e);
			throw new CustomException("No se pudo realizar el login", e);
		}
	}

	/**
	 * La localización no se guarda por aqui; cuando se registra una peluqueria o
	 * almacen deben seleccionar la posición de la ubicación del mismo.
	 */
	@Override
	public CustomerDTO signUp(CustomerDTO user) {
		LOGGER.debug("Data de entrada {}", user);
		try {
			if (user != null) {
				TblCustomer entity = modelMapper.map(user, TblCustomer.class);
				entity = userRepo.save(entity);
				this.generarTokenVerificacion(entity.getId());
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
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			LOGGER.error("ERROR: Ya existe el usuario o email CustomerServiceImpl.signUp.DataIntegrityViolationException", e);
			throw new CustomException("Ya existe el usuario o email", e);
		} catch (Exception e) {
			LOGGER.error("ERROR: No se pudo realizar el registro CustomerServiceImpl.signUp.Exception", e);
			throw new CustomException("No se pudo realizar el registro", e);
		}
	}

	@Override
	public void forgotPassword(ForgotPasswordDTO forgot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyAccount(VerifyAccountDTO verify) {
		// TODO Auto-generated method stub
		
	}
	
	private void generarTokenVerificacion(Long idCustomer) {
		TblToken token = new TblToken();
		token.setExpiry_date(LocalDateTime.now().plusMinutes(60));
		token.setIdCustomer(idCustomer);
		token.setState("C");
		token.setToken(String.valueOf(Commons.generatedPin()));
		token.setType_token("V");
		tokenRepo.save(token);
	}
	
	private void enviarCodigoActivacion(final CustomerDTO dto) {
		try {
			EmailRegisterDTO email = new EmailRegisterDTO();
			email.setTo(dto.getEmail());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EmailRegisterDTO> entity = new HttpEntity<>(email, headers);
			String fooResourceUrl = "http://localhost:8081/ms-notification-dev/ms/notification/account/code-activation";
			restTemplate.postForEntity(fooResourceUrl, entity, EmailRegisterDTO.class);
		} catch (Exception e) {
			LOGGER.error("enviarCodigoActivacion", e);
		}
	}
}
