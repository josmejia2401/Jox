package com.bay.services.customer;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.core.ForgotPasswordDTO;
import com.bay.common.dto.core.VerifyAccountDTO;

public interface CustomerService {
	CustomerDTO signIn(String username, String password);
	CustomerDTO signUp(CustomerDTO	 user);
	void forgotPassword(ForgotPasswordDTO forgot);
	void verifyAccount(VerifyAccountDTO verify);
}
