package edu.asu.spring.quadriga.db.workspace;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionWorkspaceDictionary {

	/**
	 *  Method add a dictionary to a workspace                   
	 * @returns         path of list project dicitonary page
	 * @throws			SQLException
	 * @author          Lohith Dwaraka
	 */
	public abstract void addWorkspaceDictionary(String workspaceId,
			String dictionaryId, String userId) throws QuadrigaStorageException;

	/**
	 * Method to list dictionaries in a workspace
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionary> listWorkspaceDictionary(String workspaceId, String userId)
			throws QuadrigaStorageException;

	/**
	 * Method to delete dictionaries from a workspace
	 * @param workspaceId
	 * @param userId
	 * @param dictioanaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract  void deleteWorkspaceDictionary(String workspaceId, String userId,
			String dictioanaryId) throws QuadrigaStorageException;

	/**
	 * Retrieve the dictionaries in the system that are not associated 
	 * with the given workspace
	 * @param workspaceId
	 * @param userId
	 * @return List<IDictionary>
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionary> getNonAssociatedWorkspaceDictionaries(String workspaceId,
			String userId) throws QuadrigaStorageException;
	
	public abstract List<IWorkSpace> getWorkspaceByDictId(String dictionaryId) throws QuadrigaStorageException;

}