package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.web.transformation.TransformFilesBackingBean;

/**
 * 
 * @author Jaya Venkat This class has methods which validates transformation
 *         files data
 *
 */
@Service
public class TransfomationFilesValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(TransformFilesBackingBean.class);
    }

    /**
     * Validates title, patternTitle, mappingTitle fields are empty or just
     * white spaces
     */
    @Override
    public void validate(Object obj, Errors error) {
        ValidationUtils.rejectIfEmptyOrWhitespace(error, "title",
                "title is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(error, "patternTitle",
                "patternTitle is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(error, "mappingTitle",
                "mappingTitle is required");
    }

}
