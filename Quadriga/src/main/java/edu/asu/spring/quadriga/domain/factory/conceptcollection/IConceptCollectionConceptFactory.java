package edu.asu.spring.quadriga.domain.factory.conceptcollection;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionConcepts;

public interface IConceptCollectionConceptFactory {

	public abstract IConceptCollectionConcepts createConceptCollectionConceptsObject();
	
	public abstract IConceptCollectionConcepts cloneConceptCollectionConceptsObject(
			IConceptCollectionConcepts conceptCollectionConcepts);
}
