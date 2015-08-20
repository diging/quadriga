package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorManager;

public interface IProjectCollaboratorManager extends ICollaboratorManager {

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
	 * @return List<IProjectCollaborator>
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IProjectCollaborator> getProjectCollaborators(String projectId)
			throws QuadrigaStorageException;

}
