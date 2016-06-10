package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;

@Service
public class AddTextValidator implements Validator {

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(TextFile.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.validation.Validator#validate(java.lang.Object,
     * org.springframework.validation.Errors) This method validates the input
     * from the Add Text Web page
     * 
     */
    @Override
    public void validate(Object arg0, Errors err) {

        TextFile txtFile = (TextFile) arg0;
        String filename = txtFile.getFileName();

        ValidationUtils.rejectIfEmptyOrWhitespace(err, "fileName", "filename.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "fileContent", "filecontent.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "refId", "reference.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "accessibility", "accessibility.required");

        if (!filename.isEmpty() && !(filename.matches("[a-zA-Z0-9_-]{3,30}$")
                || (filename.matches("[a-zA-Z0-9_-]{3,30}[.][a-zA-Z]{2,4}$")))) {
            err.rejectValue("fileName", "filename.proper");
        }
    }

}
