package edu.asu.spring.quadriga.domain.factory.conceptcollection.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.ConceptCollection;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;


/**
 * Factory class for creating {@link ConceptCollection}.
 * 
 * @author satyaswaroop
 *
 */
@Service
public class ConceptCollectionFactory implements IConceptCollectionFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IConceptCollection createConceptCollectionObject() {
		return new ConceptCollection();
	}

	@Override
	public IConceptCollection cloneConceptCollectionObject(IConceptCollection conceptCollection) {
		IConceptCollection clone = createConceptCollectionObject();
		clone.setConceptCollectionId(conceptCollection.getConceptCollectionId());
		clone.setConceptCollectionName(conceptCollection.getConceptCollectionName());
		clone.setDescription(conceptCollection.getDescription());
		clone.setOwner(conceptCollection.getOwner());
		clone.setConceptCollectionCollaborators(conceptCollection.getConceptCollectionCollaborators());
		clone.setConcepts(conceptCollection.getConcepts());
		clone.setProjects(conceptCollection.getProjects());
		clone.setWorkspaces(conceptCollection.getWorkspaces());
		clone.setCreatedBy(conceptCollection.getCreatedBy());
		clone.setCreatedDate(conceptCollection.getCreatedDate());
		clone.setUpdatedBy(conceptCollection.getUpdatedBy());
		clone.setUpdatedDate(conceptCollection.getUpdatedDate());
		return clone;
	}
	

}
