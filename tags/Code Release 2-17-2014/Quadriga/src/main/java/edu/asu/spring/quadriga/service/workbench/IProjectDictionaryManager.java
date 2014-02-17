package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectDictionaryManager {

	/**
	 * Add dictionary to the project  
	 * @param projectId
	 * @param dictionaryId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public void addProjectDictionary(String projectId, String dictionaryId, String userId)throws QuadrigaStorageException;
	
	/**
	 * List the dictionary in a project for a user - userId
	 * @param projectId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public List<IDictionary> listProjectDictionary(String projectId,String userId)throws QuadrigaStorageException;

	/**
	 * Delete the dictionary in a project for a user - userId
	 * @param projectId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	public void deleteProjectDictionary(String projectId, String userId,
			String dictioanaryId) throws QuadrigaStorageException;
}
