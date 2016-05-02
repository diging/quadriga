/**
 * 
 */
package edu.asu.spring.quadriga.dao.conceptcollection;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * 
 * @author satyaswaroop
 *
 */
public interface IConceptCollectionDAO extends IBaseDAO<ConceptCollectionDTO> {
    /**
     * Queries the database and builds a list of concept collection objects
     * owned by particular user
     * 
     * @return List containing user objects of all collections of the user
     * @throws QuadrigaStorageException
     */
    public abstract List<ConceptCollectionDTO> getConceptsOwnedbyUser(String userName) throws QuadrigaStorageException;

    /**
     * Queries the database and builds a list of concept collection objects
     * collaborated by particular user
     * 
     * @return List containing user objects of all collections of the user
     * @throws QuadrigaStorageException
     */
    public abstract List<ConceptCollectionDTO> getCollaboratedConceptsofUser(String userName)
            throws QuadrigaStorageException;

    /**
     * Queries the database with concept collection objects
     * 
     * @param username
     * @return TODO
     * 
     * @return Copies the data into the input object
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    public abstract ConceptCollectionDTO getCollectionDetails(String collectionId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException;

    /**
     * Updates the database by adding additional items to the List
     * 
     * @param lemma
     * @param id
     * @param pos
     * @param desc
     * @param conceptId
     * @param username
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    public abstract void saveItem(String lemma, String id, String pos, String desc, String conceptId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException;

    /**
     * Validated the id for the collection
     * 
     * @throws QuadrigaStorageException
     */
    public abstract String validateId(String collectionid) throws QuadrigaStorageException;

    /**
     * 
     * @param con
     *            ConceptCollection Object
     * @return string having success or failure message
     * @throws QuadrigaStorageException
     */
    public abstract void addCollection(IConceptCollection con) throws QuadrigaStorageException;

    /**
     * Method is used to call the delete item sp and delete the item called
     * 
     * @param id
     * @param collectionId
     * @param username
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract void deleteItems(String id, String collectionId, String username) throws QuadrigaStorageException;

    /**
     * Method is used to update the fields in the database
     * 
     * @param concept
     * @param collectionId
     * @param username
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract void updateItem(IConcept concept, String collectionId, String username)
            throws QuadrigaStorageException;

    /**
     * This method retrieves the concept collection id associated with the given
     * concept collection name
     * 
     * @param :
     *            ccName - concept collection name
     * @return : String - concept collection id
     * @throws :
     *             QuadrigaStorageException
     */
    public abstract String getConceptCollectionId(String ccName) throws QuadrigaStorageException;

    public abstract List<IConceptCollection> getNonAssociatedProjectConcepts(String projectId)
            throws QuadrigaStorageException;

}
