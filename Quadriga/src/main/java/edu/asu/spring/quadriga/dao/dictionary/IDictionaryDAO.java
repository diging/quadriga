package edu.asu.spring.quadriga.dao.dictionary;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface for the DBConnectionDictionaryManager Class.
 * 
 * @author Lohith Dwaraka
 * 
 */

public interface IDictionaryDAO extends IBaseDAO<DictionaryDTO> {

    /**
     * Adds the dictionary in the database
     * 
     * @return Status message
     * @throws QuadrigaStorageException
     */
    public abstract void addDictionary(IDictionary dictionary) throws QuadrigaStorageException;

    /**
     * Adds the dictionary items into dictionary in the database
     * 
     * @return Status message
     * @throws QuadrigaStorageException
     */
    public void addDictionaryItems(String dictionaryId, String item, String id, String pos, String owner)
            throws QuadrigaStorageException;

    /**
     * Deletes the dictionary items from dictionary in the database
     * 
     * @return Status message
     * @throws QuadrigaStorageException
     */
    public abstract void deleteDictionaryItems(String dictinaryId, String itemid, String ownerName)
            throws QuadrigaStorageException;

    /**
     * Updates the dictionary items in the dictionary in the database
     * 
     * @return Status message
     * @throws QuadrigaStorageException
     */
    public abstract void updateDictionaryItems(String dictinaryId, String termid, String term, String pos)
            throws QuadrigaStorageException;

    /**
     * Deletes the dictionary
     * 
     * @param dictionaryId
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract void deleteDictionary(String dictionaryId) throws QuadrigaStorageException;

    /**
     * Checks if user has permission to dictionary
     * 
     * @param dictionaryId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract boolean userDictionaryPerm(String userId, String dictionaryId) throws QuadrigaStorageException;

    /**
     * Get user dictionary of the user with the collaborator role
     * 
     * @param user
     *            id
     * @return List of DictionaryDTO objects
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    public abstract List<DictionaryDTO> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException;

    /**
     * Delete Dictionary Items corresponding to a dictionary ID and Term id
     * 
     * @param dictionary
     *            id and term id
     * @return error messages if any
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    public abstract void deleteDictionaryItemsCollab(String dictinaryId, String itemid) throws QuadrigaStorageException;

    public abstract List<DictionaryDTO> getDictionaryDTOList(String userName) throws QuadrigaStorageException;

    /**
     * Get Dictionary ID name corresponding to a dictionary ID
     * 
     * @param dictionary
     *            id
     * @return Owner username
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    public abstract String getDictionaryId(String dictName) throws QuadrigaStorageException;

    public void updateDictionary(IDictionary dictionary, String userName) throws QuadrigaStorageException;

    public abstract List<DictionaryDTO> getNonAssociatedProjectDictionaries(String projectId)
            throws QuadrigaStorageException;
}
