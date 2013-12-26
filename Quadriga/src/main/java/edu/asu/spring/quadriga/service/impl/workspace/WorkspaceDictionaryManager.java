package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;

@Service
public class WorkspaceDictionaryManager implements IWorkspaceDictionaryManager {

	@Autowired
	@Qualifier("DBConnectionWorkspaceDictionaryBean")
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
	public List<IDictionary> listWorkspaceDictionary(String workspaceId,
			String userId) throws QuadrigaStorageException {
		List<IDictionary> dictionaryList = dbConnect.listWorkspaceDictionary(workspaceId, userId);
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
	public void deleteWorkspaceDictionary(String workspaceId, String userId,
			String dictioanaryId) throws QuadrigaStorageException {
	     dbConnect.deleteWorkspaceDictionary(workspaceId, userId, dictioanaryId);
	}

}
