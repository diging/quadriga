package edu.asu.spring.quadriga.validator;

import org.springframework.validation.Errors;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IUnixNameValidator {

    public void validateUnixNameExp(String unixName, Errors err);
    public void validateUnixName(String unixName, String projectId, Errors err) throws QuadrigaStorageException;
    
}
