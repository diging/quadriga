package edu.asu.spring.quadriga.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class PublicPageValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(PublicPage.class);
	}

	/**
	 * This method validates the entered Title and Descriptions. Validates if
	 * the values are available or not. If values are not available error is
	 * thrown
	 * 
	 * @param obj
	 * @param err
	 */
	@Override
	public void validate(Object obj, Errors err) {
		// validate all the input parameters
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "title1", "title1.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description1", "description1.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "title2", "title2.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description2", "description2.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "title3", "title3.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description3", "description3.required");
	}
}