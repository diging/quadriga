package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjCollabManager 
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
	 * This method executes the query utilized in setting up test environment
	 * @param sQuery
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;

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

}
