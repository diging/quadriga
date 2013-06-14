/**
 * 
 */
package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConcept;
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
		/**
		 * Queries the database with concept collection objects
		 * 
		 * @return Copies the data into the input object
		 */
		public abstract void getCollectionDetails(IConceptCollection concept);
		/**
		 * Updates the database by adding additional items to the List
		 * 
		 */
		public abstract void saveItem(String lemmma, String id, String pos,
				String desc, String conceptId);
		/**
		 * Validated the id for the collection
		 */
		public abstract  String validateId(String collectionid);
		/**
		 * 
		 * @param con ConceptCollection Object
		 * @return string having success or failure message
		 */
		public abstract  String addCollection(IConceptCollection con);
		
		/**
		 * Method is used to call the delete item sp and delete the item called
		 * @param id
		 * @param collectionName
		 * @return 
		 * 
		 */
		public abstract String deleteItems(String id, String collectionName);
		
		/**
		 * Method is used to update the fields in the database
		 * @param concept
		 * @param collectionName
		 * @return
		 */
		public abstract String updateItem(IConcept concept, String collectionName);
		
		/**
		 * Method used to execute a given INSERT, UPDATE and DELETE statement in the database.
		 * 
		 * @return Success - 1
		 * @author satya swaroop boddu
		 */
		public abstract int setupTestEnvironment(String sQuery);
		
	}
