package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.workbench.Project;

@Service
public class ProjectValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(Project.class);
    }

    @Override
    public void validate(Object obj, Errors err) {
        // validate all the input parameters
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "projectName", "project_name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "project_description.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "projectAccess", "project_projectAccess.required");
    }
}
