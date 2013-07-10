package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectDictionaryManager;

@Service
public class ProjectDictionaryManager implements IProjectDictionaryManager {

	@Autowired
	@Qualifier("DBConnectionProjectDictionary")
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
	public String addProjectDictionary(String projectId, String dictionaryId,
			String userId) throws QuadrigaStorageException {
		String msg = dbConnect.addProjectDictionary(projectId, dictionaryId, userId);
		return msg;
	}

	/**
	 * List the dictionary in a project for a user - userId
	 * @param projectId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
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
	public String deleteProjectDictionary(String projectId,String userId,String dictioanaryId)throws QuadrigaStorageException{
		
		String msg=dbConnect.deleteProjectDictionary(projectId, userId, dictioanaryId);
		return msg;
	}
}
