/**
 * 
 */
package edu.asu.spring.quadriga.domain.factory.conceptcollection;

import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;

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
