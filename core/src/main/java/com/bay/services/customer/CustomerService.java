package com.bay.services.customer;

import com.bay.common.dto.core.CustomerDTO;

public interface CustomerService {
	CustomerDTO signIn(String username, String password);
	CustomerDTO signUp(CustomerDTO	 user);
}
