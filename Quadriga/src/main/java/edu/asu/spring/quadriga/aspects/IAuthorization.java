package edu.asu.spring.quadriga.aspects;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface which defines the basic methods of Authorization for any module.
 * @author Kiran kumar
 *
 */
public interface IAuthorization {

    /**
     * Method checks authorization based on access object, object id and array of {@link QuadrigaRole} of {@link IUser}.
     * @param userName                      User name or id of the {@link IUser}
     * @param accessObjectId                Id of any object type we are dealing with
     * @param userRoles                     Array of {@link IUser}'s {@link QuadrigaRole}
     * @return                              returns true or false based on authorization of user
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    public abstract boolean chkAuthorization(String userName, String accessObjectId,
            String[] userRoles) throws QuadrigaStorageException, QuadrigaAccessException;
    
    
    /**
     * Method checks authorization based on access object, list of object ids and array of {@link QuadrigaRole} of {@link IUser}.
     * @param userName                      User name or id of the {@link IUser}
     * @param List<String>accessObjectId    List of Ids of any object type we are dealing with
     * @param userRoles                     Array of {@link IUser}'s {@link QuadrigaRole}
     * @return                              returns true or false based on authorization of user
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    public abstract boolean chkAuthorization(String userName, List<String> accessObjectId,
            String[] userRoles) throws QuadrigaStorageException, QuadrigaAccessException;
    
    /**
     * Method checks authorization based on access object and array of {@link QuadrigaRole} of {@link IUser}.
     * @param userName                      User name or id of the {@link IUser}
     * @param userRoles                     Array of {@link IUser}'s {@link QuadrigaRole}
     * @return                              returns true or false based on authorization of user
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    public abstract boolean chkAuthorizationByRole(String userName,String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException;
}
