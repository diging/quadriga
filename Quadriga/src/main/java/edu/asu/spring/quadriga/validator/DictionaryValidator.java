package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.dictionary.impl.Dictionary;

/**
 * This class validates if the dictionary name and description are empty.
 * @author kiran batna
 *
 */
@Service
public class DictionaryValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(Dictionary.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "dictionaryName", "dictionary_name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "dictionary_description.required");
	}

}
