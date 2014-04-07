package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;

@Service
public class ProjectDictionaryManager implements IProjectDictionaryManager {

	@Autowired
	private IDBConnectionProjectDictionary dbConnect;
	
	/**
	 * Add dictionary to the project  
	 * @param projectId
	 * @param dictionaryId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void addProjectDictionary(String projectId, String dictionaryId,
			String userId) throws QuadrigaStorageException {
		dbConnect.addProjectDictionary(projectId, dictionaryId, userId);
	}

	/**
	 * List the dictionary in a project for a user - userId
	 * @param projectId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<IDictionary> listProjectDictionary(String projectId,
			String userId) throws QuadrigaStorageException {
		List<IDictionary> dictionaryList = dbConnect.listProjectDictionary(projectId, userId);
		return dictionaryList;
	}

	/**
	 * Delete the dictionary in a project for a user - userId
	 * @param projectId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void deleteProjectDictionary(String projectId,String userId,String dictioanaryId)throws QuadrigaStorageException{
		dbConnect.deleteProjectDictionary(projectId, userId, dictioanaryId);
	}
}