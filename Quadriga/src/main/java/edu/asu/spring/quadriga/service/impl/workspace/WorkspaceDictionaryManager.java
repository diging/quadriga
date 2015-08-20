package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workspace.IDBConnectionWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.impl.WorkspaceDictionaryShallowMapper;

@Service
public class WorkspaceDictionaryManager implements IWorkspaceDictionaryManager {

	@Autowired
	private IDBConnectionWorkspaceDictionary dbConnect;
	
	@Autowired
	private WorkspaceDictionaryShallowMapper wsDictShallowMapper;
	
	@Autowired
	private IWorkspaceDeepMapper wsDeepMapper;
	
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
	public List<IWorkspaceDictionary> listWorkspaceDictionary(IWorkSpace workspace,
			String userId) throws QuadrigaStorageException {
		
		List<IWorkspaceDictionary> wsDictionaryList = null;
		WorkspaceDTO workspaceDTO = dbConnect.listWorkspaceDictionary(workspace.getWorkspaceId(), userId);
		wsDictionaryList = wsDictShallowMapper.getWorkspaceDictionaryList(workspace, workspaceDTO);
		return wsDictionaryList;
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
	public List<IWorkspaceDictionary> listWorkspaceDictionary(String workspaceId,
			String userId) throws QuadrigaStorageException {
		
		List<IWorkspaceDictionary> wsDictionaryList = null;
		IWorkSpace workspace = wsDeepMapper.getWorkSpaceDetails(workspaceId);
		WorkspaceDTO workspaceDTO = dbConnect.listWorkspaceDictionary(workspaceId, userId);
		
		wsDictionaryList = wsDictShallowMapper.getWorkspaceDictionaryList(workspace, workspaceDTO);
		return wsDictionaryList;
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
