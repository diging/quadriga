package edu.asu.spring.quadriga.db.conceptcollection;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionCCCollaboratorManager {

	public abstract void addCollaboratorRequest(ICollaborator collaborator, String collectionid,
			String userName) throws QuadrigaStorageException;

	public abstract void deleteCollaboratorRequest(String userName, String collectionid)
			throws QuadrigaStorageException;

	public abstract void updateCollaboratorRequest(String collectionId, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

}
