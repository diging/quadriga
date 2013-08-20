package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectConceptCollectionManager {

	/**
	 * Add dictionary to the project
	 * 
	 * @param projectId
	 * @param dictionaryId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String addProjectConceptCollection(String projectId,
			String dictionaryId, String userId) throws QuadrigaStorageException;

	/**
	 * List the dictionary in a project for a user - userId
	 * 
	 * @param projectId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */

	public abstract List<IConceptCollection> listProjectConceptCollection(
			String projectId, String userId) throws QuadrigaStorageException;

	/**
	 * Delete the dictionary in a project for a user - userId
	 * 
	 * @param projectId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public abstract String deleteProjectConceptCollection(String projectId,
			String userId, String conceptCollectionId)
			throws QuadrigaStorageException;

}