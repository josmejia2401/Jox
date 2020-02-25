package com.bay.security.facade.customer;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.core.ForgotPasswordDTO;
import com.bay.common.dto.core.RecoverAccountDTO;
import com.bay.common.dto.core.VerifyAccountDTO;
import com.bay.common.dto.response.ResponseDTO;

public interface CustomerFacade {
	ResponseDTO<CustomerDTO> signIn(String username, String password);
	ResponseDTO<CustomerDTO> signUp(CustomerDTO	 user);
	ResponseDTO<CustomerDTO> forgotPassword(ForgotPasswordDTO forgot);
	ResponseDTO<CustomerDTO> forgotPasswordStep2(RecoverAccountDTO forgot);
	ResponseDTO<CustomerDTO> verifyAccount(VerifyAccountDTO verify);
}
