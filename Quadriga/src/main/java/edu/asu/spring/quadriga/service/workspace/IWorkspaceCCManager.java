package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceCCManager {

    /**
     * Add concept collection to the workspace
     * 
     * @param workspaceId
     * @param CCId
     * @param userId
     * @return
     * @throws QuadrigaStorageException
     */
    void addWorkspaceCC(String workspaceId, String CCId, String userId) throws QuadrigaStorageException;

    /**
     * Delete the concept collection in a project for a user - userId
     * 
     * @param workspaceId
     * @param userId
     * @param CCId
     * @return
     * @throws QuadrigaStorageException
     */
    void deleteWorkspaceCC(String workspaceId, String userId, String CCId) throws QuadrigaStorageException;

    List<IConceptCollection> getNotAssociatedConceptCollectins(String workspaceId) throws QuadrigaStorageException;

    List<IConceptCollection> getConceptCollections(String workspaceId) throws QuadrigaStorageException;
}
