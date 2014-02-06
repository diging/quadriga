package edu.asu.spring.quadriga.db.conceptcollection;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionCCCollaboratorManager {

	/**
	 * This method adds the collaborator for given concept collection
	 * @param : collaborator - ICollaborator object
	 * @param : collectionid - Concept Collection id
	 * @param : userName - logged in user
	 * @throws : QuadrigaStorageException
	 */
	public abstract void addCollaboratorRequest(ICollaborator collaborator, String collectionid,
			String userName) throws QuadrigaStorageException;

	/**
	 * This method deletes the collaborator associated with the 
	 * given concept collection
	 * @param : userName - logged in user
	 * @param : collectionid - concept collection id
	 * @throws : QuadrigaStorageException
	 */
	public abstract void deleteCollaboratorRequest(String userName, String collectionid)
			throws QuadrigaStorageException;
	/**
	 * This method updates the collaborator roles for the given 
	 * collaborator associated with the specified concept collection
	 * @param : collectionId - concept collection id
	 * @param : collabUser - collaborator user name
	 * @param : collaboratorRole - selected roles for the collaborator
	 * @param : username - logged in  user
	 * @throws : QuadrigaStorageException
	 */
	public abstract void updateCollaboratorRequest(String collectionId, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

}
