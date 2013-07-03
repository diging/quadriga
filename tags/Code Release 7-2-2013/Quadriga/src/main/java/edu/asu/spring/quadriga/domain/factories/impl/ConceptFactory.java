package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.implementation.Concept;

@Service
public class ConceptFactory implements IConceptFactory {

	@Override
	public IConcept createConceptObject() {
		// TODO Auto-generated method stub
		return new Concept();
	}

}
