package com.bay.common.validators;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.bay.common.util.Commons;


public class EmailValidator implements ConstraintValidator<EmailAnotation, String> {
    List<String> authors = Arrays.asList("username", "Marie Kondo", "Martin Fowler", "mkyong");
    private String email;
    public void initialize(EmailAnotation constraint) {
    	email = constraint.email();
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    	return Commons.isValidEmailAddress(value);
    }
}
