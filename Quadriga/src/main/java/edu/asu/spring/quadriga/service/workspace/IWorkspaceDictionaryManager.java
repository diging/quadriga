package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
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
    public abstract void addWorkspaceDictionary(String workspaceId, String dictionaryId, String userId)
            throws QuadrigaStorageException;

    /**
     * List the dictionary in a project for a user - userId
     * 
     * @param workspaceId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract List<IWorkspaceDictionary> listWorkspaceDictionary(IWorkSpace workspace, String userId)
            throws QuadrigaStorageException;

    /**
     * Delete the dictionary in a project for a user - userId
     * 
     * @param workspaceId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract void deleteWorkspaceDictionary(String workspaceId, String dictioanaryId)
            throws QuadrigaStorageException;

    public abstract List<IDictionary> getNonAssociatedWorkspaceDictionaries(String workspaceId)
            throws QuadrigaStorageException;

    public abstract List<IWorkspaceDictionary> listWorkspaceDictionary(String workspaceId, String userId)
            throws QuadrigaStorageException;
}
