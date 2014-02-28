package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.implementation.Concept;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;


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
