package edu.asu.spring.quadriga.domain.factory.conceptcollection.impl;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionCollaborator;

public class ConceptCollectionCollaboratorFactory implements
		IConceptCollectionCollaboratorFactory {

	@Override
	public IConceptCollectionCollaborator createConceptCollectionCollaboratorObject() {
		return new ConceptCollectionCollaborator();
	}

}
