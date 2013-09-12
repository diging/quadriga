package edu.asu.spring.quadriga.db.conceptcollection;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyCCManager {

	public abstract void transferCollectionOwnerRequest(String collectionId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract void updateCollectionDetails(IConceptCollection collection, String userName)
			throws QuadrigaStorageException;

}
