/**
 * 
 */
package edu.asu.spring.quadriga.db;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

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
		 * @throws QuadrigaStorageException 
		 */
		public abstract List<IConceptCollection> getConceptsOwnedbyUser(String userName) throws QuadrigaStorageException;
		/**
		 * Queries the database and builds a list of concept collection objects collaborated by particular user
		 * 
		 * @return List containing user objects of all collections of the user
		 * @throws QuadrigaStorageException 
		 */
		public abstract List<IConceptCollection> getCollaboratedConceptsofUser(String userName) throws QuadrigaStorageException;
		/**
		 * Queries the database with concept collection objects
		 * 
		 * @return Copies the data into the input object
		 * @throws QuadrigaStorageException 
		 */
		public abstract void getCollectionDetails(IConceptCollection concept) throws QuadrigaStorageException;
		/**
		 * Updates the database by adding additional items to the List
		 * @throws QuadrigaStorageException 
		 * 
		 */
		public abstract void saveItem(String lemma, String id, String pos, String desc,
				int conceptId) throws QuadrigaStorageException;
		/**
		 * Validated the id for the collection
		 * @throws QuadrigaStorageException 
		 */
		public abstract  String validateId(String collectionid) throws QuadrigaStorageException;
		/**
		 * 
		 * @param con ConceptCollection Object
		 * @return string having success or failure message
		 * @throws QuadrigaStorageException 
		 */
		public abstract  String addCollection(IConceptCollection con) throws QuadrigaStorageException;
		
		/**
		 * Method is used to call the delete item sp and delete the item called
		 * @param id
		 * @param collectionId
		 * @return 
		 * @throws QuadrigaStorageException 
		 * 
		 */
		public abstract String deleteItems(String id, int collectionId) throws QuadrigaStorageException;
		
		/**
		 * Method is used to update the fields in the database
		 * @param concept
		 * @param collectionName
		 * @return
		 * @throws QuadrigaStorageException 
		 */
		public abstract String updateItem(IConcept concept, String collectionName) throws QuadrigaStorageException;
		
		/**
		 * Method used to execute a given INSERT, UPDATE and DELETE statement in the database.
		 * 
		 * @return Success - 1
		 * @author satya swaroop boddu
		 * @throws QuadrigaStorageException 
		 */
		public abstract int setupTestEnvironment(String[] sQuery) throws QuadrigaStorageException;
		
		
	}
