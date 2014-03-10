package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.implementation.Concept;

/**
 * Factory class to create concept object
 * @author kiran batna
 *
 */
@Service
public class ConceptFactory implements IConceptFactory {

	@Override
	public IConcept createConceptObject() {
		return new Concept();
	}

}
