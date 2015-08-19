package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveWSCollabManager 
{

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
	public abstract List<IQuadrigaRole> getCollaboratorDBRoleIdList(String collabRoles);

}
