package edu.asu.spring.quadriga.db.conceptcollection;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyCCManager {

	/**
	 * This method transfers the ownership of the concept collection
	 * to other user and assigns the current user as a collaborator.
	 * @param : collectionId - Concept Collection id
	 * @param : oldOwner - current owner of the Concept Collection
	 * @param : newOwner - new owner to be associated with the Concept Collection
	 * @param : collabRole - the collaborator role to be associated to the current owner
	 * @throws : QuadrigaStorageException
	 */
	public abstract void transferCollectionOwnerRequest(String collectionId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	/**
	 * This method updates the concept collection details
	 * @param : collection - Concept Collection object
	 * @param : userName - logged in user
	 * @throws : QuadrigaStorageException
	 */
	public abstract void updateCollectionDetails(IConceptCollection collection, String userName)
			throws QuadrigaStorageException;

}
