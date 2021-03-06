package com.bay.security.facade.customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bay.common.dto.response.ResponseDTO;
import com.bay.common.dto.security.CustomerDTO;
import com.bay.common.dto.security.ForgotPasswordDTO;
import com.bay.common.dto.security.RecoverAccountDTO;
import com.bay.common.dto.security.VerifyAccountDTO;
import com.bay.security.facade.customer.CustomerFacade;
import com.bay.security.services.customer.CustomerService;

@Service
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private CustomerService userService;

	@Override
	public ResponseDTO<CustomerDTO> signIn(String username, String password) {
		return userService.signIn(username, password);
	}

	@Override
	public ResponseDTO<CustomerDTO> signUp(CustomerDTO user) {
		return userService.signUp(user);
	}

	@Override
	public ResponseDTO<CustomerDTO> forgotPassword(ForgotPasswordDTO forgot) {
		return this.userService.forgotPassword(forgot);
	}

	@Override
	public ResponseDTO<CustomerDTO> verifyAccount(VerifyAccountDTO verify) {
		return this.userService.verifyAccount(verify);
	}

	@Override
	public ResponseDTO<CustomerDTO> forgotPasswordStep2(RecoverAccountDTO forgot) {
		return this.userService.forgotPasswordStep2(forgot);
	}

}
