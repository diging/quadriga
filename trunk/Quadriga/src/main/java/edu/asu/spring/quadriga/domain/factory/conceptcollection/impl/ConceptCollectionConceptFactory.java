package edu.asu.spring.quadriga.domain.factory.conceptcollection.impl;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionConcepts;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionConceptFactory;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionConcepts;

public class ConceptCollectionConceptFactory implements
		IConceptCollectionConceptFactory {

	@Override
	public IConceptCollectionConcepts createConceptCollectionConceptsObject() {
		return new ConceptCollectionConcepts();
	}

}
