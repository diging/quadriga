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

	/**
	 * Create {@link IConceptCollection} for any developer who wants to create concept collection
	 * @return			returns {@link IConceptCollection} 
	 */
	public abstract IConceptCollection createConceptCollectionObject();

	/**
	 * Creates {@link IConcept} for any developer who wants to create concepts
	 * @return			returns {@link IConceptCollection} 
	 */
	public abstract IConcept createConcept();
}
