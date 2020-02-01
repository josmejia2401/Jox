package com.bay.controller.customer;

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

import com.bay.common.dto.core.CustomerDTO;
import com.bay.common.dto.core.SignInDTO;
import com.bay.facade.customer.CustomerFacade;

@RestController
@RequestMapping("customer/v1")
@Validated
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private CustomerFacade customer;
    
    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    CustomerDTO sigIn(@Valid @RequestBody SignInDTO user) {
        return customer.signIn(user.getUsername(), user.getPassword());
    }
    
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    CustomerDTO sigUp(@Validated @RequestBody CustomerDTO user) {
        return customer.signUp(user);
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
