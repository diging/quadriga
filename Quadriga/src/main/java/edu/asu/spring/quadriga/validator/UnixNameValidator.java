package edu.asu.spring.quadriga.validator;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class UnixNameValidator implements IUnixNameValidator {

    @Autowired
    private IProjectSecurityChecker projectCheckSecurityManager;

    /**
     * This method validates if the Unix name contains letters other than the
     * mentioned value in the regular expression
     * 
     * @param unixName
     * @param err
     * @author Karthikeyan Mohan
     */
    public void validateUnixNameExp(String unixName, Errors err) {
        String regex = "^[a-zA-Z0-9-_.+]*$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(unixName).matches()) {
            err.rejectValue("unixName", "project_UnixName.expression");
        } else if (unixName.trim().equalsIgnoreCase("")) {
            err.rejectValue("unixName", "project_unixname.required" + "");
        }
    }

    /**
     * This method validates if the entered Unix name is duplicate or not
     * 
     * @param unixName
     * @param projectId
     * @param err
     * @throws QuadrigaStorageException
     * @author Karthikeyan Mohan
     */
    public void validateUnixName(String unixName, String projectId, Errors err) throws QuadrigaStorageException {
        // Verifying if the Unix name already exists
        boolean isDuplicate = projectCheckSecurityManager.isUnixnameInUse(unixName, projectId);
        if (isDuplicate) {
            err.rejectValue("unixName", "projectUnixName.unique");
        }
    }

}
