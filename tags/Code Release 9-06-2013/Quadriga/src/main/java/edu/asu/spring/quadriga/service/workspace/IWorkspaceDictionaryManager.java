package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDictionaryManager {

	/**
	 * Add dictionary to the workspace  
	 * @param workspaceId
	 * @param dictionaryId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addWorkspaceDictionary(String workspaceId, String dictionaryId, String userId)throws QuadrigaStorageException;
	
	/**
	 * List the dictionary in a project for a user - userId
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionary> listWorkspaceDictionary(String workspaceId,String userId)throws QuadrigaStorageException;

	/**
	 * Delete the dictionary in a project for a user - userId
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteWorkspaceDictionary(String workspaceId, String userId,
			String dictioanaryId) throws QuadrigaStorageException;
}
