package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * @description  : interface to implement Collaborator class.
 * 
 * @author       : Kiran Kumar Batna
 *
 */
public interface ICollaborator 
{

	/**
	 * Method to set the User details
	 * @param userObj - {@link IUser} object containing Collaborator details
	 */
	public abstract void setUserObj(IUser userObj);

	/**
	 * Method to retrieve the user details
	 * @return IUser - {@link IUser} object containing Collaborator details
	 */
	public abstract IUser getUserObj();
	
    /**
     * Method to set roles associated with the collaborator
     * @param collaboratorRoles - List of {@link ICollaboratorRole} objects containing
     * the roles associated to the collaborator
     */
	public abstract void setCollaboratorRoles(List<IQuadrigaRole> collaboratorRoles);

    /**
     * Method to retrieve roles associated with the collaborator
     * @return collaboratorRoles - List of {@link ICollaboratorRole} objects containing
     * the roles associated to the collaborator
     */
	public abstract List<IQuadrigaRole> getCollaboratorRoles();

    /**
     * Method to set the collaborator description.
     * @param description - text describing the collaborator.
     */
	public abstract void setDescription(String description);

	/**
	 * Method to retrieve the collaborator description
	 * @return description - text describing the collaborator
	 */
	public abstract String getDescription();
}
