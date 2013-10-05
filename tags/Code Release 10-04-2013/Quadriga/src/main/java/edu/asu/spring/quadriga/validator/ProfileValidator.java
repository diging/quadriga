package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.implementation.Profile;

@Service
public class ProfileValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		
		return arg0.isAssignableFrom(Profile.class);
	}

	@Override
	public void validate(Object arg0, Errors err) {
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "serviceName", "profile_servicename.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "uri", "profile_uri.required");
		
	}

}
