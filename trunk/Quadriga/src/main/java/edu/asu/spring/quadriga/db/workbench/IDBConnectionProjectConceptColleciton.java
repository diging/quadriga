package edu.asu.spring.quadriga.db.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProjectConceptColleciton {

    /**
     * This method adds a concept collection to a project.
     * @param projectId
     * @param conceptCollectionId
     * @param userId
     * @throws QuadrigaStorageException
     */
	public abstract void addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId) throws QuadrigaStorageException;

	/**
	 * Lists all concept collections associated with the given user for the specified project.
	 * @param projectId
	 * @param userId
	 * @return List<IConceptCollection> - list of concept collections associated with the user for the 
	 *         specified project.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IConceptCollection> listProjectConceptCollection(String projectId, String userId)
			throws QuadrigaStorageException;

	/**
	 * Removes the association of the specified concept collection from the specified project.
	 * @param projectId
	 * @param userId
	 * @param conceptCollectionId
	 * @throws QuadrigaStorageException
	 */
	public abstract  void deleteProjectConceptCollection(String projectId, String userId,
			String conceptCollectionId) throws QuadrigaStorageException;

}