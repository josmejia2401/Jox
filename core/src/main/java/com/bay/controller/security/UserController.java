package com.bay.controller.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bay.common.dto.SignInDTO;
import com.bay.common.dto.UserDTO;
import com.bay.facade.security.UserFacade;

@RestController
@RequestMapping("temp")
@Validated
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserFacade userFacade;
    
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    UserDTO sigIn(@Valid @RequestBody SignInDTO user) {
        return userFacade.signIn(user.getUsername(), user.getPassword());
    }
    
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    UserDTO sigUp(@Validated @RequestBody UserDTO user) {
        return userFacade.signUp(user);
    }
    
    /*@PostMapping("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    UserDTO forgotPassword(@Validated @RequestBody UserDTO user) {
        return userFacade.signIn();
    }
    
    @PostMapping("/recovery-password")
    @ResponseStatus(HttpStatus.OK)
    UserDTO recoveryPassword(@Validated @RequestBody UserDTO user) {
        return userFacade.signIn();
    }*/
}
