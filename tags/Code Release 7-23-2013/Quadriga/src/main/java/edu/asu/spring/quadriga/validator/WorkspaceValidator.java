package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.implementation.WorkSpace;
@Service
public class WorkspaceValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(WorkSpace.class);
	}

	@Override
	public void validate(Object obj, Errors err) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "workspace_name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "workspace_description.required");
	}

}