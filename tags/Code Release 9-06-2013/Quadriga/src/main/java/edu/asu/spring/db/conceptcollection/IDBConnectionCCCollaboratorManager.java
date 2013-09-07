package edu.asu.spring.db.conceptcollection;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionCCCollaboratorManager {

	public abstract void addCollaboratorRequest(ICollaborator collaborator, String collectionid,
			String userName) throws QuadrigaStorageException;

}
