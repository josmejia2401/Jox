package com.bay.security.services.customer;

import com.bay.common.dto.response.ResponseDTO;
import com.bay.common.dto.security.CustomerDTO;
import com.bay.common.dto.security.ForgotPasswordDTO;
import com.bay.common.dto.security.RecoverAccountDTO;
import com.bay.common.dto.security.VerifyAccountDTO;

public interface CustomerService {
	ResponseDTO<CustomerDTO> signIn(String username, String password);
	ResponseDTO<CustomerDTO> signUp(CustomerDTO	 user);
	ResponseDTO<CustomerDTO> forgotPassword(ForgotPasswordDTO forgot);
	ResponseDTO<CustomerDTO> forgotPasswordStep2(RecoverAccountDTO forgot);
	ResponseDTO<CustomerDTO> verifyAccount(VerifyAccountDTO verify);
}
