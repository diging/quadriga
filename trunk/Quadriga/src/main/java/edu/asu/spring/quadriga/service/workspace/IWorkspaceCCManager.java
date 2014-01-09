package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceCCManager {

	/**
	 * Add concept collection to the workspace  
	 * @param workspaceId
	 * @param CCId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addWorkspaceCC(String workspaceId, String CCId, String userId)throws QuadrigaStorageException;
	
	/**
	 * List the concept collection in a project for a user - userId
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IConceptCollection> listWorkspaceCC(String workspaceId,String userId)throws QuadrigaStorageException;

	/**
	 * Delete the concept collection in a project for a user - userId
	 * @param workspaceId
	 * @param userId
	 * @param CCId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract void deleteWorkspaceCC(String workspaceId, String userId,
			String CCId) throws QuadrigaStorageException;

	public abstract List<IConceptCollection> getNonAssociatedWorkspaceConcepts(String workspaceId,
			String userId) throws QuadrigaStorageException;
}
