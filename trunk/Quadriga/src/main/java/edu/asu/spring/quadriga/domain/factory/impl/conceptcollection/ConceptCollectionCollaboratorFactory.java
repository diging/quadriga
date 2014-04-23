package edu.asu.spring.quadriga.domain.factory.impl.conceptcollection;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionCollaborator;

@Service
public class ConceptCollectionCollaboratorFactory implements
		IConceptCollectionCollaboratorFactory {

	@Override
	public IConceptCollectionCollaborator createConceptCollectionCollaboratorObject() {
		return new ConceptCollectionCollaborator();
	}

}
