package edu.asu.spring.quadriga.validator;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.domain.settings.impl.AboutText;

/**
 * This class acts as validator of form data coming from
 * <code>publicWebsiteEditAbout.jsp</code>
 * 
 * @author Nischal Samji
 *
 */
@Service
public class AboutTextValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return AboutText.class.isAssignableFrom(arg0);
    }

    /**
     * This method validates the entered title, description of a about text for
     * a project. Validates if the values are available or not. If values are
     * not available error is thrown
     * 
     * @param obj
     * @param err
     */
    @Override
    public void validate(Object obj, Errors err) {

        IAboutText abtText = (IAboutText) obj;

        String description = abtText.getDescription();
        String title = abtText.getTitle();

        Whitelist whitelist = Whitelist.basicWithImages();
        Whitelist titleWhitelist = Whitelist.simpleText();

        // validate all the input parameters
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "title", "about_title.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "description", "about_description.required");

        if (!Jsoup.isValid(description, whitelist)) {
            err.rejectValue("description", "about_description.proper");
        }

        if (!Jsoup.isValid(title, titleWhitelist)) {
            err.rejectValue("title", "about_title.proper");
        }
    }
}