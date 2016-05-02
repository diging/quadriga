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

	@Override
	public IConceptCollectionConcepts cloneConceptCollectionConceptsObject(IConceptCollectionConcepts conceptCollectionConcepts)
	{
		IConceptCollectionConcepts clone = new ConceptCollectionConcepts();
		clone.setConcept(conceptCollectionConcepts.getConcept());
		clone.setConceptCollection(conceptCollectionConcepts.getConceptCollection());
		clone.setCreatedBy(conceptCollectionConcepts.getCreatedBy());
		clone.setCreatedDate(conceptCollectionConcepts.getCreatedDate());
		clone.setUpdatedBy(conceptCollectionConcepts.getUpdatedBy());
		clone.setUpdatedDate(conceptCollectionConcepts.getUpdatedDate());
		return clone;
	}

}
