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
import com.bay.common.dto.core.ForgotPasswordDTO;
import com.bay.common.dto.core.RecoverAccountDTO;
import com.bay.common.dto.core.SignInDTO;
import com.bay.common.dto.core.VerifyAccountDTO;
import com.bay.common.dto.response.ResponseDTO;
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
	ResponseDTO<CustomerDTO> sigIn(@Valid @RequestBody SignInDTO user) {
		return customer.signIn(user.getUsername(), user.getPassword());
	}

	@PostMapping("/sign-up")
	@ResponseStatus(HttpStatus.OK)
	ResponseDTO<CustomerDTO> sigUp(@Validated @RequestBody CustomerDTO user) {
		return customer.signUp(user);
	}

	@PostMapping("/forgot-password")
	@ResponseStatus(HttpStatus.OK)
	ResponseDTO<CustomerDTO> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgot) {
		return customer.forgotPassword(forgot);
	}
	
	@PostMapping("/recover-account")
	@ResponseStatus(HttpStatus.OK)
	ResponseDTO<CustomerDTO> forgotPasswordStep2(@Valid @RequestBody RecoverAccountDTO forgot) {
		return customer.forgotPasswordStep2(forgot);
	}


	@PostMapping("/verify-account")
	@ResponseStatus(HttpStatus.OK)
	ResponseDTO<CustomerDTO> verifyAccount(@Validated @RequestBody VerifyAccountDTO verify) {
		return customer.verifyAccount(verify);
	}
}
