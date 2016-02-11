package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.impl.workspace.TextFile;

@Service
public class AddTextValidator implements Validator{

    @Override
    public boolean supports(Class<?> arg0) {
        return arg0.isAssignableFrom(TextFile.class);
    }

    @Override
    public void validate(Object arg0, Errors err) {
        
        TextFile txtFile = (TextFile) arg0;
        String filename = txtFile.getFileName();
        
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "fileName", "filename.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "fileContent", "filecontent.required");
        if(!filename.isEmpty() && !filename.matches("^[a-z0-9_-]$")){
            err.rejectValue("fileName", "filename.proper");
        }
        
        
    }

}
