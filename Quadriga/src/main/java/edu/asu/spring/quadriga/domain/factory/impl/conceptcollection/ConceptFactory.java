package edu.asu.spring.quadriga.domain.factory.impl.conceptcollection;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionConcepts;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.Concept;

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
		List<IConceptCollectionConcepts> conceptCollectionList = new ArrayList<IConceptCollectionConcepts>();
		for(IConceptCollectionConcepts conceptCollection : concept.getConceptCollectionConcepts())
		{
			conceptCollectionList.add(conceptCollection);
		}
		
		clone.setConceptCollectionConcepts(conceptCollectionList);
		
		return clone;
	}

}
