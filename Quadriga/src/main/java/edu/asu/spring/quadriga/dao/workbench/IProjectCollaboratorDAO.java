package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectCollaboratorDAO extends IBaseDAO<ProjectCollaboratorDTO>
{

	/**
	 * This method adds a collaborator  for a project
	 * @param collaborator
	 * @param projectid
	 * @param userName
	 * @throws QuadrigaStorageException
	 */
	public abstract void addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName)
			throws QuadrigaStorageException;
	
	/**
	 * This method deletes the collaborator for a project
	 * @param userName
	 * @param projectid
	 * @throws QuadrigaStorageException
	 */
	public abstract void deleteColloratorRequest(String userName, String projectid) throws QuadrigaStorageException;

	/**
	 * This method updates the roles of collaborator for a project
	 * @param projectid
	 * @param collabUser
	 * @param collaboratorRole
	 * @param username
	 * @throws QuadrigaStorageException
	 */
	public abstract void updateCollaboratorRequest(String projectid, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

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
    

}
