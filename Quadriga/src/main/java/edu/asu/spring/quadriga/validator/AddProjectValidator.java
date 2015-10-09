package edu.asu.spring.quadriga.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class AddProjectValidator implements Validator {

    @Autowired
    private IUnixNameValidator unixNameValidator;
    
    private static final Logger logger = LoggerFactory.getLogger(AddProjectValidator.class);
    
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
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "unixName", "project_unixname.required");

        Project project = (Project) obj;

        String projUnixName = project.getUnixName();
        String projectId = project.getProjectId();
        EProjectAccessibility projectAccess = project.getProjectAccess();

        if (err.getFieldError("projectAccess") == null) {
            // validate the selected project accessibility
            validateProjectAccessibility(projectAccess, err);
        }

        if (err.getFieldError("unixName") == null) {
            // validate the regular expression
            unixNameValidator.validateUnixNameExp(projUnixName, err);
        }

        if (err.getFieldError("unixName") == null) {
            try {
                unixNameValidator.validateUnixName(projUnixName, projectId, err);
            } catch (QuadrigaStorageException e) {
                logger.error("Error", e);
            }
        }
    }

    public void validateProjectAccessibility(EProjectAccessibility projectAccess, Errors err) {
        if (projectAccess == null) {
            err.rejectValue("projectAccess", "project_projectAccess_selection.required");
        }

    }
}