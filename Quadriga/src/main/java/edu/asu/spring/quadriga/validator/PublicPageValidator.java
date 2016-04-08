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
     * This method validates the entered Title and Description and the order. Validates if
     * the values are available or not. If values are not available error is
     * thrown
     * 
     * @param obj
     * @param err
     */

    @Override
    public void validate(Object obj, Errors err) {
        // validate all the input parameters
        PublicPage page = (PublicPage) obj;
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "title", "title.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "description.required");
        if (page.getOrder() == 0) {
            err.rejectValue("order", "order.required");
        }
    }
}