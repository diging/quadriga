package edu.asu.spring.quadriga.domain.factory.conceptcollection.impl;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.Concept;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;

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

	@Override
	public IConcept cloneConceptObject(IConcept concept) 
	{
		IConcept clone = createConceptObject();
		clone.setConceptId(concept.getConceptId());
		clone.setDescription(concept.getDescription());
		clone.setLemma(concept.getLemma());
		clone.setPos(concept.getPos());
		clone.setCreatedBy(concept.getCreatedBy());
		clone.setCreatedDate(concept.getCreatedDate());
		clone.setUpdatedBy(concept.getUpdatedBy());
		clone.setUpdatedDate(concept.getUpdatedDate());
		clone.setConceptCollections(new ArrayList<>());
		concept.getConceptCollections().forEach(cc -> clone.getConceptCollections().add(cc));
		return clone;
	}

}
