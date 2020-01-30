package com.bay.facade.security;

import com.bay.common.dto.UserDTO;

public interface UserFacade {
	UserDTO signIn(String username, String password);
	UserDTO signUp(UserDTO user);
}
