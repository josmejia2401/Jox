package com.bay.facade.security.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bay.common.dto.UserDTO;
import com.bay.facade.security.UserFacade;
import com.bay.services.security.UserService;

@Service
public class UserFacadeImpl implements UserFacade {

	@Autowired
	private UserService userService;

	@Override
	public UserDTO signIn(String username, String password) {
		return userService.signIn(username, password);
	}

	@Override
	public UserDTO signUp(UserDTO user) {
		return userService.signUp(user);
	}

}
