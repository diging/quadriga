package edu.asu.spring.quadriga.validator;

import org.springframework.validation.Errors;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IUnixNameValidator {

    /**
     * This method validates if the Unix name contains letters other than the
     * mentioned value in the regular expression
     * 
     * @param unixName
     * @param err
     * @author Karthikeyan Mohan
     */
    public void validateUnixNameExp(String unixName, Errors err);

    /**
     * This method validates if the entered Unix name is duplicate or not
     * 
     * @param unixName
     * @param projectId
     * @param err
     * @throws QuadrigaStorageException
     * @author Karthikeyan Mohan
     */
    public void validateUnixName(String unixName, String projectId, Errors err) throws QuadrigaStorageException;

}
