package com.bay.services.customer.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.core.ForgotPasswordDTO;
import com.bay.common.dto.core.ForgotPasswordStep2DTO;
import com.bay.common.dto.core.VerifyAccountDTO;
import com.bay.common.dto.notification.EmailSendDTO;
import com.bay.common.dto.response.ResponseDTO;
import com.bay.common.exceptions.BayException;
import com.bay.common.exceptions.CustomException;
import com.bay.common.util.Commons;
import com.bay.common.util.MessageUtil;
import com.bay.common.util.RestTemplateUtil;
import com.bay.common.util.StatusEnum;
import com.bay.entity.core.TblCustomer;
import com.bay.entity.core.TblToken;
import com.bay.repositories.customer.CustomerRepository;
import com.bay.repositories.customer.TokenRepository;
import com.bay.services.customer.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Value("${app.ms.bay.urlSendCodeActivation}")
	private String urlSendCodeActivation;
	
	@Value("${app.ms.bay.urlSendRecoverAccount}")
	private String urlSendRecoverAccount;
	
	@Value("${app.ms.bay.urlSendWelcome}")
	private String urlSendWelcome;
	
	@Autowired
	private CustomerRepository userRepo;

	@Autowired
	private TokenRepository tokenRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestTemplateUtil restTemplateUtil;
	
	@Autowired
	private MessageUtil messageUtil;
	

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
				if (customerDto != null && customerDto.getStatus() != null && customerDto.getStatus().equalsIgnoreCase("A")) {
					response.setCode(0L);
					response.setData(customerDto);
					response.setMessage("OK");
				} else {
					response.setMessage(messageUtil.getMessage("customer.signin.error.credential_invalid"));
				}
				return response;
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
				entity.setStatus(StatusEnum.INACTIVE.getCode());
				entity = userRepo.save(entity);
				
				this.generarTokenVerificacion(entity.getId());
				final CustomerDTO dto = modelMapper.map(entity, CustomerDTO.class);
				dto.setPassword("");
				
				EmailSendDTO emailSend = new EmailSendDTO();
				List<String> email = new ArrayList<String>();
				email.add(entity.getEmail());
				emailSend.setTo(email);
				restTemplateUtil.sendAsync(emailSend, this.urlSendCodeActivation);
				
				ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
				response.setCode(0L);
				response.setData(dto);
				response.setMessage(messageUtil.getMessage("customer.signup.message.link_send", new String[] {user.getEmail()}));
				return response;
			} else {
				throw new BayException(messageUtil.getMessage("customer.signup.error.invalid_account"));
			}
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signUp.DataIntegrityViolationException", e);
			throw new CustomException(messageUtil.getMessage("customer.signup.error.account_exists"), e);
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.signUp.Exception", e);
			throw new CustomException(messageUtil.getMessage("customer.signup.error.internal_failure"), e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.signUp finish");
		}
	}

	/**
	 * Existe la posibilidad, cuando se recupere enviar un token, y hacer el mismo proceso de verificar cuenta.
	 */
	@Override
	public ResponseDTO<CustomerDTO> forgotPassword(ForgotPasswordDTO forgot) {
		try {
			LOGGER.debug("CustomerServiceImpl.forgotPassword start with data: {} ", forgot);
			ResponseDTO<CustomerDTO> response = new ResponseDTO<CustomerDTO>();
			response.setData(null);
			response.setMessage(messageUtil.getMessage("customer.forgotpassword.message.send_message"));
			if (forgot.getEmail() != null && !forgot.getEmail().isEmpty()) {
				final CustomerDTO customer = this.userRepo.findByEmail(forgot.getEmail()).map(x -> {
					CustomerDTO dto = modelMapper.map(x, CustomerDTO.class);
					dto.setPassword("");
					return dto;
				}).orElse(null);
				if (customer != null) {
					TblToken tblToken = tokenRepo.findTokenValid(customer.getId(), LocalDateTime.now()).orElse(null);
					String token = null;
					if (tblToken == null) {
						token = this.generarTokenVerificacion(customer.getId());
					} else {
						token = tblToken.getToken();
					}
					EmailSendDTO emailSend = new EmailSendDTO();
					List<String> email = new ArrayList<String>();
					email.add(customer.getEmail());
					emailSend.setTo(email);
					emailSend.setBody(token);
					restTemplateUtil.sendAsync(emailSend, this.urlSendRecoverAccount);
					response.setCode(0L);
				} else {
					response.setCode(3L);
				}
			} else {
				response.setCode(1L);
				response.setMessage(messageUtil.getMessage("customer.forgotpassword.error.required_information"));
			}
			return response;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.forgotPassword.Exception", e);
			throw new CustomException(messageUtil.getMessage("customer.forgotpassword.error.internal_failure"), e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.forgotPassword finish");
		}
	}

	@Override
	public ResponseDTO<CustomerDTO> forgotPasswordStep2(ForgotPasswordStep2DTO forgot) {
		try {
			LOGGER.debug("CustomerServiceImpl.forgotPasswordStep2 start with data: {} ", forgot);
			ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
			response.setCode(1L);
			response.setData(null);
			TblCustomer customer = this.userRepo.findByEmail(forgot.getEmail()).orElseThrow(() -> new CustomException(this.messageUtil.getMessage("customer.forgotpasswordstep2.error.invalid_information")));
			TblToken tblToken = tokenRepo.findTokenUserId(customer.getId(), forgot.getToken(), LocalDateTime.now()).orElse(null);
			if (tblToken != null) {
				tblToken.setStatus(StatusEnum.RECOVERED.getCode());
				tokenRepo.save(tblToken);
				
				customer.setStatus(StatusEnum.ACTIVE.getCode());
				customer.setPassword(forgot.getPassword());
				this.userRepo.save(customer);
				
				final CustomerDTO dto = modelMapper.map(customer, CustomerDTO.class);
				
				EmailSendDTO emailSend = new EmailSendDTO();
				List<String> email = new ArrayList<String>();
				email.add(dto.getEmail());
				emailSend.setTo(email);
				restTemplateUtil.sendAsync(emailSend, this.urlSendWelcome);
				
				response.setCode(0L);
				response.setData(null);
				response.setMessage(this.messageUtil.getMessage("customer.forgotpasswordstep2.message.correctly_verified"));
			} else {
				response.setMessage(this.messageUtil.getMessage("customer.forgotpasswordstep2.error.invalid_information"));
			}
			return response;
		} catch (CustomException e) {
			LOGGER.error("ERROR: CustomerServiceImpl.forgotPasswordStep2.CustomException", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.forgotPasswordStep2.Exception", e);
			throw new CustomException(this.messageUtil.getMessage("customer.forgotpasswordstep2.error.internal_failure"), e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.forgotPasswordStep2 finish");
		}
	}
	
	@Override
	public  ResponseDTO<CustomerDTO> verifyAccount(VerifyAccountDTO verify) {
		try {
			LOGGER.debug("CustomerServiceImpl.verifyAccount start with data: {} ", verify);
			ResponseDTO<CustomerDTO> response = new ResponseDTO<>();
			response.setCode(1L);
			response.setData(null);
			TblCustomer customer = this.userRepo.findByEmail(verify.getEmail()).orElseThrow(() -> new CustomException(this.messageUtil.getMessage("customer.verifyaccount.error.invalid_information")));
			TblToken tblToken = tokenRepo.findTokenUserId(customer.getId(), verify.getToken(), LocalDateTime.now()).orElse(null);
			if (tblToken != null) {
				tblToken.setStatus(StatusEnum.RECOVERED.getCode());
				tokenRepo.save(tblToken);
				
				customer.setStatus(StatusEnum.ACTIVE.getCode());
				this.userRepo.save(customer);
				
				final CustomerDTO dto = modelMapper.map(customer, CustomerDTO.class);
				
				EmailSendDTO emailSend = new EmailSendDTO();
				List<String> email = new ArrayList<String>();
				email.add(dto.getEmail());
				emailSend.setTo(email);
				restTemplateUtil.sendAsync(emailSend, this.urlSendWelcome);
				
				response.setCode(0L);
				response.setData(null);
				response.setMessage(this.messageUtil.getMessage("customer.verifyaccount.message.correctly_verified"));
			} else {
				response.setMessage(this.messageUtil.getMessage("customer.verifyaccount.error.invalid_information"));
			}
			return response;
		} catch (CustomException e) {
			LOGGER.error("ERROR: CustomerServiceImpl.verifyAccount.CustomException", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("ERROR: CustomerServiceImpl.verifyAccount.Exception", e);
			throw new CustomException(this.messageUtil.getMessage("customer.verifyaccount.error.internal_failure"), e);
		} finally {
			LOGGER.debug("CustomerServiceImpl.verifyAccount finish");
		}
	}
	
	private String generarTokenVerificacion(Long idCustomer) {
		TblToken token = new TblToken();
		token.setExpiryDate(LocalDateTime.now().plusMinutes(60));
		token.setIdCustomer(idCustomer);
		token.setStatus(StatusEnum.CREATED.getCode());
		token.setToken(String.valueOf(Commons.generatedPin()));
		token.setTypeToken("V");
		tokenRepo.save(token);
		return token.getToken();
	}


}
