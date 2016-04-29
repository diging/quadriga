package edu.asu.spring.quadriga.service.conceptcollection;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyConceptCollectionManager 
{

	public abstract void updateCollectionDetails(IConceptCollection collection, String userName)
			throws QuadrigaStorageException;
	

}
