package edu.asu.spring.quadriga.aspects;

import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;

/**
 * Interface for methods used to get the {@link IAuthorization} object based on
 * type.
 * 
 * @author Kiran Kumar
 *
 */
public interface IAuthorizationManager {

    /**
     * Method used to get the {@link IAuthorization} object based on type. type
     * may include - project, conceptcollection, workspace, dictionary.
     * 
     * @param type
     *            Is enum value of project, conceptcollection, workspace,
     *            dictionary.
     * @return Return a implemented object of type {@link IAuthorization}
     */
    public abstract IAuthorization getAuthorizationObject(CheckedElementType type);

}