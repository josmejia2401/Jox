package com.bay.facade.customer;

import com.bay.common.dto.core.CustomerDTO;

public interface CustomerFacade {
	CustomerDTO signIn(String username, String password);
	CustomerDTO signUp(CustomerDTO	 user);
}
