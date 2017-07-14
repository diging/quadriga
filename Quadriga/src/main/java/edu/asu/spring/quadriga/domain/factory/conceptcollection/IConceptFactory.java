package edu.asu.spring.quadriga.domain.factory.conceptcollection;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.impl.Concept;
/**
 * Creates {@link Concept} objects.
 * @author sboddu1
 *
 */
public interface IConceptFactory {

	public abstract IConcept createConceptObject();
	
	public abstract IConcept cloneConceptObject(IConcept concept);
}
