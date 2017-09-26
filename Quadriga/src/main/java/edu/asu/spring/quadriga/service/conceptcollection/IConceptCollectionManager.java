/**
 * 
 */
package edu.asu.spring.quadriga.service.conceptcollection;

import java.util.List;

import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * 
 * 
 * Interface that places restraints on the ConceptCollectionManager class to
 * implement the required behaviors.
 * 
 *
 * @author satyaswaroop
 *
 */
public interface IConceptCollectionManager {

    /**
     * Method is used to get list of concepts owned by the user. Input: UserId
     * Output: List of conceptcollections
     * 
     * @param sUserId
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract List<IConceptCollection> getCollectionsOwnedbyUser(
            String sUserId) throws QuadrigaStorageException;

    /**
     * Method is used to get list of concepts collaborated by the user. Input:
     * UserId Output: List of conceptcollections
     * 
     * @param sUserId
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract List<IConceptCollection> getUserCollaborations(
            String sUserId) throws QuadrigaStorageException;

    /**
     * Method is used to search the concept power rest api Input: item and pos
     * of the word Output: ConceptpowerReply which contains a list of items
     * 
     * @param item
     * @param pos
     * @return
     */
    public ConceptpowerReply search(String item, String pos);

    /**
     * Method is used to add new items to items list of a conceptcollection
     * 
     * Result: We just update the backend
     * 
     * @param lemmma
     * @param id
     * @param pos
     * @param desc
     * @param conceptcollectionId
     * @param string
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    void addItems(String lemmma, String id, String pos, String desc,
            String conceptcollectionId, String string)
            throws QuadrigaStorageException, QuadrigaAccessException;

    /**
     * Method is used to add new conceptcollection Result: We just update the
     * backend and return the success/failure
     * 
     * @param collection
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract void addConceptCollection(IConceptCollection collection)
            throws QuadrigaStorageException;

    /**
     * Method is used to delete items from the collection list of a particular
     * user. Input: collectionname and id of the concept to be deleted
     * 
     * @param id
     * @param collectionid
     * @param username
     * @throws QuadrigaStorageException
     */
    public abstract void deleteItem(String id, String collectionid,
            String username) throws QuadrigaStorageException;

    /**
     * Method is used to update the items list with the latest values from the
     * conceptpower
     * 
     * @param values
     * @param concept
     * @throws QuadrigaStorageException
     */
    public abstract void update(String[] values, IConceptCollection concept,
            String username) throws QuadrigaStorageException;

    public abstract List<IConceptCollectionCollaborator> showCollaboratingUsers(
            String collectionid) throws QuadrigaStorageException;

    public abstract void getCollaborators(IConceptCollection collection)
            throws QuadrigaStorageException;

    public abstract String getConceptLemmaFromConceptId(String id);

    public abstract String getConceptDescriptionFromConceptId(String id);

    public abstract String getConceptCollectionId(String ccName)
            throws QuadrigaStorageException;

    public abstract List<IConceptCollection> getNonAssociatedProjectConcepts(
            String projectId) throws QuadrigaStorageException;

    public abstract IConceptCollection getConceptCollection(String id)
            throws QuadrigaStorageException;

    public abstract void fillConceptCollection(
            IConceptCollection conceptCollection)
            throws QuadrigaStorageException;

}