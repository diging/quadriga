/**
 * 
 */
package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IConceptCollection;

/**
 * @Description : factory interface to produce concept collection objects
 * @author satyaswaroop
 *
 */
public interface IConceptCollectionFactory {

	public abstract IConceptCollection createConceptCollectionObject();
}
