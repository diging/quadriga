package edu.asu.spring.quadriga.db.workspace;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionWorkspaceCC {

	/**
	 *  Method add a Concept collection to a workspace                   
	 * @returns         path of list workspace Concept collection page
	 * @throws			SQLException
	 * @author          Lohith Dwaraka
	 */
	public abstract String addWorkspaceCC(String workspaceId,
			String CCId, String userId) throws QuadrigaStorageException;

	/**
	 * Method to list the Concept collection in workspace
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract WorkspaceDTO listWorkspaceCC(String workspaceId, String userId)
			throws QuadrigaStorageException;

	/**
	 * Method to delete the concept collection from workspace
	 * @param workspaceId
	 * @param userId
	 * @param dictioanaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract  void deleteWorkspaceCC(String workspaceId, String userId,
			String CCId) throws QuadrigaStorageException;

	/**
	 * Retrieve the concept collection in the system that are not associated 
	 * with the given workspace
	 * @param workspaceId
	 * @param userId
	 * @return List<IConceptCollection>
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IConceptCollection> getNonAssociatedWorkspaceConcepts(String workspaceId,
			String userId) throws QuadrigaStorageException;

}