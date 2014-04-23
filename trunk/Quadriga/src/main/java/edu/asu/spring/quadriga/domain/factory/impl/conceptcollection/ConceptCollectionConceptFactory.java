package edu.asu.spring.quadriga.domain.factory.impl.conceptcollection;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionConcepts;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionConceptFactory;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollectionConcepts;

@Service
public class ConceptCollectionConceptFactory implements
		IConceptCollectionConceptFactory {

	@Override
	public IConceptCollectionConcepts createConceptCollectionConceptsObject() {
		return new ConceptCollectionConcepts();
	}

}
