/**
 * 
 */
package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;

/**
 * @Description : Creates {@link ConceptCollectiont} objects.
 * @author satyaswaroop boddu
 *
 */
public interface IConceptCollectionFactory {

	public abstract IConceptCollection createConceptCollectionObject();

	public abstract IConcept createConcept();
}
