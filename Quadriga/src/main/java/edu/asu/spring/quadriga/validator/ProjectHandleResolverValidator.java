package edu.asu.spring.quadriga.validator;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.xml.bind.helpers.ValidationEventLocatorImpl;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.resolver.IProjectHandleResolver;
import edu.asu.spring.quadriga.domain.resolver.impl.ProjectHandleResolver;

@Component
public class ProjectHandleResolverValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ProjectHandleResolver.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        IProjectHandleResolver resolver = (IProjectHandleResolver) target;
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName", "resolver.project_name.required", "Project name required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resolvedHandlePattern", "resolver.resolved_handler_pattern.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "handlePattern", "resolver.handler_pattern.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "handleExample", "resolver.handler_example.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "resolvedHandleExample", "resolver.resolved_handle_example.required");
        
        if (resolver.getHandlePattern() != null && resolver.getHandleExample() != null) {
            try {
                Pattern pattern = Pattern.compile(resolver.getHandlePattern());
            } catch (PatternSyntaxException ex) {
                errors.rejectValue("handlePattern", "resolver.handle_pattern.does_not_compile");
            }
        }
    }

}
