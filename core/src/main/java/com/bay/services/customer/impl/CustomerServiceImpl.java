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
import com.bay.common.dto.response.ResponseDTO;
import com.bay.common.exceptions.BayException;
import com.bay.common.exceptions.CustomException;
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
	public ResponseDTO<CustomerDTO> signIn(String username, String password) {
		LOGGER.debug("CustomerServiceImpl.signIn start with data: {} - {}", username, password);
		try {
			if (username != null && password != null) {
				CustomerDTO customerDto = userRepo.signIn(username, password).map(x -> {
					CustomerDTO dto = modelMapper.map(x, CustomerDTO.class);
					dto.setPassword("");
					return dto;
				}).orElse(null);
				
				ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
				response.setCode(1L);
				response.setData(null);
				response.setMessage("Error de inicio de sesión; ID de usuario o contraseña no válidos.");
				
				if (customerDto != null && customerDto.getStatus() != null && customerDto.getStatus().equalsIgnoreCase("A")) {
					response.setCode(0L);
					response.setData(customerDto);
					response.setMessage("OK");
				}
				return response;
			} else {
				throw new BayException("Error de inicio de sesión; ID de usuario o contraseña no válidos.");
			}
		} catch (BayException e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signIn.BayException", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signIn.Exception", e);
			throw new CustomException("Error de inicio de sesión; Falla interna, intente mas tarde por favor.", e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.signIn finish");
		}
	}

	/**
	 * La localización no se guarda por aqui; cuando se registra una peluqueria o
	 * almacen deben seleccionar la posición de la ubicación del mismo.
	 */
	@Override
	public ResponseDTO<CustomerDTO> signUp(CustomerDTO user) {
		LOGGER.debug("CustomerServiceImpl.signUp start with data: {} ", user);
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
				ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
				response.setCode(0L);
				response.setData(dto);
				response.setMessage("Se ha enviado un enlace para activar su cuenta " + user.getEmail());
				return response;
			} else {
				throw new BayException("Error de creación de cuenta; Ingrese la información requerida.");
			}
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signUp.DataIntegrityViolationException", e);
			throw new CustomException("Error de creación de cuenta; Ya existe el usuario o email", e);
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signUp.Exception", e);
			throw new CustomException("Error de creación de cuenta; Falla interna, intente mas tarde por favor.", e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.signUp finish");
		}
	}

	@Override
	public ResponseDTO<CustomerDTO> forgotPassword(ForgotPasswordDTO forgot) {
		try {
			LOGGER.debug("CustomerServiceImpl.forgotPassword start with data: {} ", forgot);
			ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
			response.setCode(1L);
			response.setData(null);
			response.setMessage("Si esa dirección de correo electrónico está en nuestra base de datos, le enviaremos un correo electrónico para restablecer su contraseña.");
			
			if (forgot.getEmail() != null && !forgot.getEmail().isEmpty()) {
				final CustomerDTO customer = this.userRepo.findByEmail(forgot.getEmail()).map(x -> {
					CustomerDTO dto = modelMapper.map(x, CustomerDTO.class);
					dto.setPassword("");
					return dto;
				}).orElse(null);
				if (customer != null) {
					CompletableFuture.supplyAsync(() -> {
						this.enviarRecuperarCuenta(customer);
						return "";
					});
					response.setCode(0L);
					response.setData(null);
					response.setMessage("Si esa dirección de correo electrónico está en nuestra base de datos, le enviaremos un correo electrónico para restablecer su contraseña.");
				}
			}
			return response;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.forgotPassword.Exception", e);
			throw new CustomException("Error de recuperar cuenta; Falla interna, intente mas tarde por favor.", e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.forgotPassword finish");
		}
	}

	@Override
	public  ResponseDTO<CustomerDTO> verifyAccount(VerifyAccountDTO verify) {
		try {
			LOGGER.debug("CustomerServiceImpl.verifyAccount start with data: {} ", verify);
			ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
			response.setCode(1L);
			response.setData(null);
			response.setMessage("Código no es válido.");
			TblCustomer customer = null;
			if (verify.getEmail() != null && !verify.getEmail().isEmpty()) {
				customer = this.userRepo.findByEmail(verify.getEmail()).orElse(null);
			} else if (verify.getUsername() != null && !verify.getUsername().isEmpty()) {
				customer = this.userRepo.findByEmail(verify.getUsername()).orElse(null);
			}
			TblToken tblToken = tokenRepo.findTokenUserId(customer.getId(), verify.getToken(), LocalDateTime.now()).orElse(null);
			if (tblToken != null) {
				tblToken.setState("R");
				tokenRepo.save(tblToken);
				customer.setStatus("A");
				this.userRepo.save(customer);
				final CustomerDTO dto = modelMapper.map(customer, CustomerDTO.class);
				CompletableFuture.supplyAsync(() -> {
					this.enviarBienvenido(dto);
					return "";
				});
				response.setCode(0L);
				response.setData(null);
				response.setMessage("Cuenta verificada correctamente.");
			}
			return response;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.verifyAccount.Exception", e);
			throw new CustomException("Error de verificar cuenta; Falla interna, intente mas tarde por favor.", e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.verifyAccount finish");
		}
	}
	
	private void generarTokenVerificacion(Long idCustomer) {
		TblToken token = new TblToken();
		token.setExpiryDate(LocalDateTime.now().plusMinutes(60));
		token.setIdCustomer(idCustomer);
		token.setState("C");
		token.setToken(String.valueOf(Commons.generatedPin()));
		token.setType_token("V");
		tokenRepo.save(token);
	}
	
	private void enviarRecuperarCuenta(final CustomerDTO dto) {
		try {
			EmailRegisterDTO email = new EmailRegisterDTO();
			email.setTo(dto.getEmail());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EmailRegisterDTO> entity = new HttpEntity<>(email, headers);
			String fooResourceUrl = "http://localhost:8081/ms-notification-dev/ms/notification/account/forgot-account";
			restTemplate.postForEntity(fooResourceUrl, entity, EmailRegisterDTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.enviarRecuperarCuenta", e);
		}
	}
	
	/**
	 * Cuenta activada - bienvenida al usuario.
	 * @param dto
	 */
	private void enviarBienvenido(final CustomerDTO dto) {
		try {
			EmailRegisterDTO email = new EmailRegisterDTO();
			email.setTo(dto.getEmail());
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<EmailRegisterDTO> entity = new HttpEntity<>(email, headers);
			String fooResourceUrl = "http://localhost:8081/ms-notification-dev/ms/notification/account/welcome";
			restTemplate.postForEntity(fooResourceUrl, entity, EmailRegisterDTO.class);
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.enviarBienvenido", e);
		}
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
			LOGGER.error("ERROR: CustomerServiceImpl.enviarCodigoActivacion", e);
		}
	}
}
