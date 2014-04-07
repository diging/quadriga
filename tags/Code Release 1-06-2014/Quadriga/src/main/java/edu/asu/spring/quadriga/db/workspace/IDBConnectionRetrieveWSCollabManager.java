package edu.asu.spring.quadriga.db.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveWSCollabManager 
{

	/**
	 * Retrieves collaborators for associated workspace
	 * @param workspaceId
	 * @return List<ICollaborator> - list of collaborators for associated workspace.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<ICollaborator> getWorkspaceCollaborators(String workspaceId)
			throws QuadrigaStorageException;

	/**
	 * Retrieves list of users who are not yet collaborators for given workspace
	 * @param workspaceId
	 * @return List<IUser> - list of users who are not yet collaborators for given workspace.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IUser> getWorkspaceNonCollaborators(String workspaceId)
			throws QuadrigaStorageException;

	/**
	 * This method returns the list of collaborator roles for the supplied string of 
	 * collaborator roles.
	 * @param collabRoles
	 * @return List<ICollaboratorRole> - list of collaborator roles.
	 */
	public abstract List<ICollaboratorRole> getCollaboratorDBRoleIdList(String collabRoles);

}