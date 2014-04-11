package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.Concept;
/**
 * Creates {@link Concept} objects.
 * @author sboddu1
 *
 */
public interface IConceptFactory {

	public abstract IConcept createConceptObject();
}
