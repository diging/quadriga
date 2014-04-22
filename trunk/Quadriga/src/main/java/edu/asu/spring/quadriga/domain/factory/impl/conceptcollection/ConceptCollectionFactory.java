package edu.asu.spring.quadriga.domain.factory.impl.conceptcollection;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.Concept;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;


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
		// TODO Auto-generated method stub
		return new ConceptCollection();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IConcept createConcept()
	{
		return new Concept();
	}

}
