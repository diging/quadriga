/**
 * 
 */
package edu.asu.spring.quadriga.service;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;


/**
 * 
 * 
 * Interface that places restraints on the ConceptCollectionManager class to implement
 * the required behaviors.
 * 
 *
 * @author satyaswaroop
 *
 */
public interface IConceptCollectionManager {

	public abstract List<IConceptCollection> getCollectionsOfUser(String sUserId) throws SQLException;
	public abstract String updateConceptCollection(ConceptCollection conceptCollection);	
	public abstract int deleteConceptCollection(String id);	
	public abstract int addConceptCollection(ConceptCollection newConcept);	
	public abstract IConceptCollection getConceptCollection(String id);
}
