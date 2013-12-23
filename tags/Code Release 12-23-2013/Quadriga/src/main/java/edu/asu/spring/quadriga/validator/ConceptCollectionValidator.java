package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
@Service
public class ConceptCollectionValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		 return arg0.isAssignableFrom(ConceptCollection.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "collection_name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "collection_description.required");
	}

}
