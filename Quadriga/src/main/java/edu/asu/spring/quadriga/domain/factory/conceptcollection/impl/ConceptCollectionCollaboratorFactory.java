package edu.asu.spring.quadriga.domain.factory.conceptcollection.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.ConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionCollaboratorFactory;

@Service
public class ConceptCollectionCollaboratorFactory implements
		IConceptCollectionCollaboratorFactory {

	@Override
	public IConceptCollectionCollaborator createConceptCollectionCollaboratorObject() {
		return new ConceptCollectionCollaborator();
	}

	@Override
	public IConceptCollectionCollaborator cloneConceptCollectionCollaboratorObject(
			IConceptCollectionCollaborator conceptCollectionCollaborator) 
	{
		IConceptCollectionCollaborator clone = new ConceptCollectionCollaborator();
		clone.setCollaborator(conceptCollectionCollaborator.getCollaborator());
		clone.setConceptCollection(conceptCollectionCollaborator.getConceptCollection());
		clone.setCreatedBy(conceptCollectionCollaborator.getCreatedBy());
		clone.setCreatedDate(conceptCollectionCollaborator.getCreatedDate());
		clone.setUpdatedBy(conceptCollectionCollaborator.getUpdatedBy());
		clone.setUpdatedDate(conceptCollectionCollaborator.getUpdatedDate());
		return clone;
	}

}
