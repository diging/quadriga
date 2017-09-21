package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDictionaryManager {

    /**
     * Add dictionary to the workspace
     * 
     * @param workspaceId
     * @param dictionaryId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    public void addWorkspaceDictionary(String workspaceId, String dictionaryId, String userId)
            throws QuadrigaStorageException;

    /**
     * Delete the dictionary in a project for a user - userId
     * 
     * @param workspaceId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    public void deleteWorkspaceDictionary(String workspaceId, String dictioanaryId) throws QuadrigaStorageException;

    public List<IDictionary> getNonAssociatedWorkspaceDictionaries(String workspaceId) throws QuadrigaStorageException;

    public List<IDictionary> getDictionaries(String workspaceId, String userId) throws QuadrigaStorageException;
}
