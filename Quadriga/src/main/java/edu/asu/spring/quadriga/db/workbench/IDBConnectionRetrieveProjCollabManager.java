package edu.asu.spring.quadriga.db.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveProjCollabManager 
{

	/**
	 * Retrieves all the user who are not associated with the project as collaborators.
	 * @param projectid
	 * @return List<IUser> - list of users who are not associated with specified project
	 *                       as collaborators.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IUser> getProjectNonCollaborators(String projectid) throws QuadrigaStorageException;

	/**
	 * Retrieves all the users associated with project as collaborators.
	 * @param projectId
	 * @return List<ICollaborator> - list of collaborators associated with the specified project.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<ICollaborator> getProjectCollaborators(String projectId)
			throws QuadrigaStorageException;
	
	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;

}
