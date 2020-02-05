package com.bay.services.customer;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.core.ForgotPasswordDTO;
import com.bay.common.dto.core.VerifyAccountDTO;
import com.bay.common.dto.response.ResponseDTO;

public interface CustomerService {
	ResponseDTO<CustomerDTO> signIn(String username, String password);
	ResponseDTO<CustomerDTO> signUp(CustomerDTO	 user);
	ResponseDTO<CustomerDTO> forgotPassword(ForgotPasswordDTO forgot);
	ResponseDTO<CustomerDTO> verifyAccount(VerifyAccountDTO verify);
}
