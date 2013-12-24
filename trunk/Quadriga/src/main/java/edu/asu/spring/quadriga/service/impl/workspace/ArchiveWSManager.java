package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionArchiveWSManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IArchiveWSManager;

/**
 * Class implements {@link IArchiveWSManager} 
 * for archiving and deactivating workspace associated with project.
 * @implements IArchiveWSManager
 * @author Kiran Kumar Batna
 */
@Service
public class ArchiveWSManager implements IArchiveWSManager 
{

	@Autowired
	@Qualifier("archiveWorkspaceManagerDAO")
	private IDBConnectionArchiveWSManager archiveWorkspaceManager;
	
	/**
	 * This will archive the requested workspace.[archive = 1 is supplied to database]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void archiveWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		boolean archive;
		
		//assigning the archive parameter 1 species archive a workspace
		archive = true;
		archiveWorkspaceManager.archiveWorkspace(workspaceIdList, archive, wsUser);
	}
	
	/**
	 * This will activate the requested archived workspace.[archive = 0 will be supplied to database.]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void unArchiveWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		boolean archive;
		//assigning the archive parameter 0 species not to archive a workspace
		archive = false;
		archiveWorkspaceManager.archiveWorkspace(workspaceIdList, archive, wsUser);
	}
	
	/**
	 * This will deactivate the requested workspace.[deactivate = 1 will be supplied to database]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void deactivateWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		boolean deactivate;
		
		//assigning the deactivate variable.
		deactivate = true;
		archiveWorkspaceManager.deactivateWorkspace(workspaceIdList, deactivate, wsUser);
	}
	
	/**
	 * This will activate the requested deactivated workspace.
	 * [deactivate = 0 will be supplied to database]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	@Transactional
	public void activateWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		boolean deactivate;
		//assigning the deactivate variable.
		deactivate = false;
		archiveWorkspaceManager.deactivateWorkspace(workspaceIdList, deactivate, wsUser);
	}

}
