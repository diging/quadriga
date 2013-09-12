package edu.asu.spring.quadriga.service.conceptcollection;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyConceptCollectionManager 
{

	public abstract void transferCollectionOwnerRequest(String collectionId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	public abstract void updateCollectionDetails(IConceptCollection collection, String userName)
			throws QuadrigaStorageException;
	

}
