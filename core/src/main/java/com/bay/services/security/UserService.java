package com.bay.services.security;

import com.bay.common.dto.UserDTO;

public interface UserService {
	UserDTO signIn(String username, String password);
	UserDTO signUp(UserDTO user);
}
