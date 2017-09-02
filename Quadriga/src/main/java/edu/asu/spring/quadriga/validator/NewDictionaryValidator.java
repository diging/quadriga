package edu.asu.spring.quadriga.validator;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.dictionary.impl.Dictionary;

@Service
public class NewDictionaryValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(Dictionary.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dictionaryName", "dictionary.name.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "dictionary.description.required");
    }

}
