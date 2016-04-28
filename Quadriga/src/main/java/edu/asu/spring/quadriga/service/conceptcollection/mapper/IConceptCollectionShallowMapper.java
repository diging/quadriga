package edu.asu.spring.quadriga.service.conceptcollection.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IConceptCollectionShallowMapper {
	public abstract List<IConceptCollection> getConceptCollectionList(String userName) throws QuadrigaStorageException;
	public abstract IConceptCollection getConceptCollectionDetails(ConceptCollectionDTO ccDTO) throws QuadrigaStorageException;
	public abstract List<IConceptCollection> getConceptCollectionListOfCollaborator(String userName) throws QuadrigaStorageException;

}
