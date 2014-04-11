package edu.asu.spring.quadriga.db.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProjectDictionary {

    /**
     * Adds given dictionary to the specified project
     * @param projectId
     * @param dictionaryId
     * @param userId
     * @throws QuadrigaStorageException
     */
	public abstract void addProjectDictionary(String projectId,
			String dictionaryId, String userId) throws QuadrigaStorageException;

	/**
	 * Retrieves the dictionaries associated with the specified project.
	 * @param projectId
	 * @param userId
	 * @return List<IDictionary> - list of dictionaries associated with the specified project.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IDictionary> listProjectDictionary(String projectId, String userId)
			throws QuadrigaStorageException;

	/**
	 * Removes the association of the given dictionary from the specified project.
	 * @param projectId
	 * @param userId
	 * @param dictioanaryId
	 * @throws QuadrigaStorageException
	 */
	public abstract  void deleteProjectDictionary(String projectId, String userId,
			String dictioanaryId) throws QuadrigaStorageException;
	
	public abstract List<IProject> getprojectsByDictId(String dictionaryId) throws QuadrigaStorageException;

}