package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.implementation.Dictionary;

@Service
public class DictionaryValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(Dictionary.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "dictionary_name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "dictionary_description.required");
	}

}
