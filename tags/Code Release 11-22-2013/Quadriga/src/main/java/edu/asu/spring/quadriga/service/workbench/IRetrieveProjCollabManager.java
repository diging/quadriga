package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjCollabManager {

	/**
	 * This methods returns the users who are not collaborators 
	 * to the supplied project
	 * @param projectid
	 * @return List - List of users
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	public abstract List<IUser> getProjectNonCollaborators(String projectid)
			throws QuadrigaStorageException;

	/**
	 * This method returns the collaborators associated with the project.
	 * @param projectId
	 * @return List<ICollaborator>
	 * @throws QuadrigaStorageException
	 */
	public abstract List<ICollaborator> getProjectCollaborators(String projectId)
			throws QuadrigaStorageException;

}
