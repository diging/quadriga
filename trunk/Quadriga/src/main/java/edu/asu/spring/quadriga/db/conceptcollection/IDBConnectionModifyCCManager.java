package edu.asu.spring.quadriga.db.conceptcollection;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyCCManager {

	public abstract void transferCollectionOwnerRequest(String collectionId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

}
