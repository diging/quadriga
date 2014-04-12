package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;

@Service
public class WorkspaceDictionaryManager implements IWorkspaceDictionaryManager {

	@Autowired
	private IDBConnectionWorkspaceDictionary dbConnect;
	
	@Autowired
	private DataSource dataSource;
	/**
	 * Assigns the data source
	 *  
	 *  @param : dataSource
	 */
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * Add dictionary to the workspace  
	 * @param workspaceId
	 * @param dictionaryId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void addWorkspaceDictionary(String workspaceId,
			String dictionaryId, String userId) throws QuadrigaStorageException {
		dbConnect.addWorkspaceDictionary(workspaceId, dictionaryId, userId);
	}

	/**
	 * List the dictionary in a project for a user - userId
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<IDictionary> listWorkspaceDictionary(String workspaceId,
			String userId) throws QuadrigaStorageException {
		List<IDictionary> dictionaryList = dbConnect.listWorkspaceDictionary(workspaceId, userId);
		return dictionaryList;
	}
	
	/**
	 * List the dictionaries which are not associated to a workspace for a user -UserId
	 * @throws QuadrigaStorageException 
	 * 
	 */
	@Override
	@Transactional
	public List<IDictionary> getNonAssociatedWorkspaceDictionaries(String workspaceId,String userId) throws QuadrigaStorageException
	{
		List<IDictionary> dictionaryList = dbConnect.getNonAssociatedWorkspaceDictionaries(workspaceId,userId);
		return dictionaryList;
	}

	/**
	 * Delete the dictionary in a project for a user - userId
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void deleteWorkspaceDictionary(String workspaceId, String userId,
			String dictioanaryId) throws QuadrigaStorageException {
	     dbConnect.deleteWorkspaceDictionary(workspaceId, userId, dictioanaryId);
	}

}
