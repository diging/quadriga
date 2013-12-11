/**
 * 
 */
package edu.asu.spring.quadriga.db.conceptcollection;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
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
		 * @param username 
		 * 
		 * @return Copies the data into the input object
		 * @throws QuadrigaStorageException 
		 * @throws QuadrigaAccessException 
		 */
		public abstract void getCollectionDetails(IConceptCollection collection, String username) throws QuadrigaStorageException, QuadrigaAccessException;
		
		/**
		 * Updates the database by adding additional items to the List
		 * @param lemma
		 * @param id
		 * @param pos
		 * @param desc
		 * @param conceptId
		 * @param username
		 * @throws QuadrigaStorageException
		 * @throws QuadrigaAccessException 
		 */
		public abstract void saveItem(String lemma, String id, String pos, String desc,
				String conceptId, String username) throws QuadrigaStorageException, QuadrigaAccessException;
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
		public abstract  void addCollection(IConceptCollection con) throws QuadrigaStorageException;
		
		
		/**
		 * Method is used to call the delete item sp and delete the item called
		 * @param id
		 * @param collectionId
		 * @param username
		 * @return
		 * @throws QuadrigaStorageException
		 */
		public abstract String deleteItems(String id, String collectionId, String username) throws QuadrigaStorageException;
		
		
		/**
		 * Method is used to update the fields in the database
		 * @param concept
		 * @param collectionId
		 * @param username
		 * @return
		 * @throws QuadrigaStorageException
		 */
		public abstract String updateItem(IConcept concept, String collectionId, String username) throws QuadrigaStorageException;
		
		/**
		 * Method used to execute a given INSERT, UPDATE and DELETE statement in the database.
		 * 
		 * @return Success - 1
		 * @author satya swaroop boddu
		 * @throws QuadrigaStorageException 
		 */
		public abstract int setupTestEnvironment(String[] sQuery) throws QuadrigaStorageException;
		
		/**
		 * 
		 * 
		 * @author rohit
		 * @throws QuadrigaStorageException 
		 * 
		 */
		public abstract List<ICollaborator> showCollaboratorRequest(String collectionid) throws QuadrigaStorageException;
		
		/**
		 * 
		 * 
		 * @return 
		 * @author rohit
		 * @throws QuadrigaStorageException 
		 * 
		 */
		public abstract List<IUser> showNonCollaboratorRequest(String collectionid) throws QuadrigaStorageException;
		
		/**
		 * Method used to  get colloborators of a particular collection.
		 * 
		 * 
		 * @author Rohit
		 * 
		 */
		public abstract void getCollaborators(IConceptCollection collection) throws QuadrigaStorageException;
		public abstract  String getConceptCollectionId(String ccName)
				throws QuadrigaStorageException;
		
	}
