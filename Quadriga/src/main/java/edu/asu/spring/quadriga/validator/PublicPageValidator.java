package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;

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
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "title", "title.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "description.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "order", "order.required");

	}
}