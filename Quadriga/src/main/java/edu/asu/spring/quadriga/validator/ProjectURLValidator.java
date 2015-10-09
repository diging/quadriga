package edu.asu.spring.quadriga.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class ProjectURLValidator implements Validator {

    @Autowired
    private IUnixNameValidator unixNameValidator;

    private static final Logger logger = LoggerFactory.getLogger(ProjectURLValidator.class);

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(Project.class);
    }

    @Override
    public void validate(Object obj, Errors err) {
        // validate all the input parameters
        Project project = (Project) obj;

        String projUnixName = project.getUnixName();
        String projectId = project.getProjectId();

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
}