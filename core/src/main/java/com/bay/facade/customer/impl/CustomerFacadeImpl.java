package com.bay.facade.customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.core.ForgotPasswordDTO;
import com.bay.common.dto.core.RecoverAccountDTO;
import com.bay.common.dto.core.VerifyAccountDTO;
import com.bay.common.dto.response.ResponseDTO;
import com.bay.facade.customer.CustomerFacade;
import com.bay.services.customer.CustomerService;

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
