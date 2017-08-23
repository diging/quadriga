package edu.asu.spring.quadriga.mapper.conceptcollection;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IConceptCollectionDeepMapper {
	public abstract IConceptCollection getConceptCollectionDetails(String ccId)
			throws QuadrigaStorageException;

    public abstract void fillConceptCollection(IConceptCollection conceptCollection, ConceptCollectionDTO ccDTO)
            throws QuadrigaStorageException;


}
