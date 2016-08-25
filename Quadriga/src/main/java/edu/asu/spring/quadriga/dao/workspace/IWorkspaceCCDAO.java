package edu.asu.spring.quadriga.dao.workspace;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceCCDAO {

    /**
     * Method add a Concept collection to a workspace
     * 
     * @returns path of list workspace Concept collection page
     * @throws SQLException
     * @author Lohith Dwaraka
     */
    void addWorkspaceCC(String workspaceId, String CCId, String userId) throws QuadrigaStorageException;

    /**
     * Method to delete the concept collection from workspace
     * 
     * @param workspaceId
     * @param userId
     * @param dictioanaryId
     * @return
     * @throws QuadrigaStorageException
     */
    void deleteWorkspaceCC(String workspaceId, String userId, String CCId) throws QuadrigaStorageException;

    /**
     * Retrieve the concept collection in the system that are not associated
     * with the given workspace
     * 
     * @param workspaceId
     * @param userId
     * @return List<IConceptCollection>
     * @throws QuadrigaStorageException
     */
    List<IConceptCollection> getNonAssociatedWorkspaceConcepts(String workspaceId) throws QuadrigaStorageException;

}