package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.web.settings.AboutTextBackingBean;

@Service
/**
 * This class acts as validator of form data coming from
 * <code>publicWebsiteEditAbout.jsp.jsp</code>
 * 
 * @author PawanMahalle
 *
 */
public class AboutTextValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(AboutTextBackingBean.class);
    }

    /**
     * This method validates the entered title, description of blog entry.
     * Validates if the values are available or not. If values are not available
     * error is thrown
     * 
     * @param obj
     * @param err
     */
    @Override
    public void validate(Object obj, Errors err) {
        // validate all the input parameters
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "title", "blog_title.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "blog_description.required");
    }
}