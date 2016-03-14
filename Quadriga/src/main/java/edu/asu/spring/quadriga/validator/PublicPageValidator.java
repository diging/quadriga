package edu.asu.spring.quadriga.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class PublicPageValidator implements Validator {

    @Autowired
    private IUnixNameValidator unixNameValidator;

    private static final Logger logger = LoggerFactory.getLogger(PublicPageValidator.class);

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(Project.class);
    }

    /**
     * This method validates the entered ProjectName, Description.
     * Validates if the values are available or not. If
     * values are not available error is thrown
     * 
     * @param obj
     * @param err
     */
    @Override
    public void validate(Object obj, Errors err) {
        // validate all the input parameters
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "projectName", "project_name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "project_description.required");
            }
}