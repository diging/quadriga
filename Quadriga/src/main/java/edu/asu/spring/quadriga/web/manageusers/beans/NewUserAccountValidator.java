package edu.asu.spring.quadriga.web.manageusers.beans;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NewUserAccountValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AccountRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object arg0, Errors errors) {
        AccountRequest request = (AccountRequest) arg0;
        
        ValidationUtils.rejectIfEmpty(errors, "username", "username.required", "Username is required.");
        ValidationUtils.rejectIfEmpty(errors, "name", "name.required", "We'd like to know who you are.");
        ValidationUtils.rejectIfEmpty(errors, "password", "password.required", "Please specify a password.");
        ValidationUtils.rejectIfEmpty(errors, "email", "email.required", "Please tell us how to reach you.");
        
        if (!request.getPassword().equals(request.getRepeatedPassword())) {
            errors.rejectValue("password", "password.notmatch", "Passwords do not match.");
        }
    }

}
