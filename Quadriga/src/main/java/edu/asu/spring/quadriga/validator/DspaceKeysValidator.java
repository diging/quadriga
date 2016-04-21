package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.dspace.service.impl.DspaceKeys;


/**
 * The purpose of this class is to validate the form submission for
 * Dspace keys.
 * 
 * @author Ram Kumar Kumaresan
 */
@Service
public class DspaceKeysValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		 return arg0.isAssignableFrom(DspaceKeys.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "publicKey", "dspace.publickey.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "privateKey", "dspace.privatekey.required");
	}

}
