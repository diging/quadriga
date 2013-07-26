package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.implementation.Concept;
/**
 * Creates {@link Concept} objects.
 * @author sboddu1
 *
 */
public interface IConceptFactory {

	public abstract IConcept createConceptObject();
}
