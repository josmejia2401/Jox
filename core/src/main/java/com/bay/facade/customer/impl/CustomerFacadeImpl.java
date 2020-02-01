package com.bay.facade.customer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bay.common.dto.core.CustomerDTO;
import com.bay.facade.customer.CustomerFacade;
import com.bay.services.customer.CustomerService;

@Service
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private CustomerService userService;

	@Override
	public CustomerDTO signIn(String username, String password) {
		return userService.signIn(username, password);
	}

	@Override
	public CustomerDTO signUp(CustomerDTO user) {
		return userService.signUp(user);
	}

}
