package edu.asu.spring.quadriga.sevice.conceptcollection;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyConceptCollectionManager 
{

	public abstract void transferCollectionOwnerRequest(String collectionId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;
	

}
