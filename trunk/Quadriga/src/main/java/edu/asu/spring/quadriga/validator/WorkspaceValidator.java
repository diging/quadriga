package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;

/**
 * This method validates if the workspace name and description
 * is empty
 * @author kbatna
 *
 */
@Service
public class WorkspaceValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(WorkSpace.class);
	}

	@Override
	public void validate(Object obj, Errors err) {
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "name", "workspace_name.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "workspace_description.required");
	}

}