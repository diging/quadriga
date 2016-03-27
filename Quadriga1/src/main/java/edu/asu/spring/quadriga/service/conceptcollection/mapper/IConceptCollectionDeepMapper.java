package edu.asu.spring.quadriga.service.conceptcollection.mapper;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IConceptCollectionDeepMapper {
	public abstract IConceptCollection getConceptCollectionDetails(String ccId)
			throws QuadrigaStorageException;


}
