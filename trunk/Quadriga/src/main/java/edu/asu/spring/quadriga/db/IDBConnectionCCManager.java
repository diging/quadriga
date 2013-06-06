/**
 * 
 */
package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;

/**
 * 
 * @author satyaswaroop
 *
 */
public interface IDBConnectionCCManager {
		/**
		 * Queries the database and builds a list of concept collection objects owned by particular user
		 * 
		 * @return List containing user objects of all collections of the user
		 */
		public abstract List<IConceptCollection> getConceptsOwnedbyUser(String userName);
		/**
		 * Queries the database and builds a list of concept collection objects collaborated by particular user
		 * 
		 * @return List containing user objects of all collections of the user
		 */
		public abstract List<IConceptCollection> getCollaboratedConceptsofUser(String userName);
	}
