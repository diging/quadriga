package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProjectAccessManager 
{

	/**
	 * This method checks if the user has editor role for the given project
	 * @param userName
	 * @param projectId
	 * @return true - if the user has editor role for the given project.
	 *         false - if the user does not have editor role for the given project.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkProjectOwnerEditorRole(String userName, String projectId) throws QuadrigaStorageException;

	/**
	 * This method checks if the Unix name for a project already exists in the database
	 * @param unixName
	 * @param projectId
	 * @return true - if the unix name is already associated to the any project.
	 *         false - if the unix name is not associated to any project.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkDuplicateProjUnixName(String unixName, String projectId) throws QuadrigaStorageException;

	/**
	 * This method checks if the given user is a collaborator for a project and has a particular collaborator role.
	 * @param userName
	 * @param collaboratorRole
	 * @param projectId
	 * @return true - if the given user is a collaborator with the specified role for the given project.
	 *         false - if the give user is not a collaborator with the specified role for the given project.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkProjectCollaborator(String userName, String collaboratorRole,
			String projectId) throws QuadrigaStorageException;

	/**
	 * This method checks if the user is associated to any project
	 * @param userName
	 * @return true - if the given user is associated with any project.
	 *         false - if the given user is not associated with any project.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkIsProjectAssociated(String userName) throws QuadrigaStorageException;

	/**
	 * This method checks if the given user is owner for the given project
	 * @param userName
	 * @param projectId
	 * @return true - if the given user is owner of the specified project.
	 *         false - if the given user is not owner of the specified project.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkProjectOwner(String userName, String projectId) throws QuadrigaStorageException;

	/**
	 * This method checks if the given user is a collaborator associated to any project
	 * @param userName
	 * @param collaboratorRole
	 * @return true - if the given user is associated with any project with specified collaborator role.
	 *         false - if the given user is not associated with any project with specified collaborator role.
	 * @throws QuadrigaStorageException
	 */
	public abstract boolean chkIsCollaboratorProjectAssociated(String userName,
			String collaboratorRole) throws QuadrigaStorageException;

}
