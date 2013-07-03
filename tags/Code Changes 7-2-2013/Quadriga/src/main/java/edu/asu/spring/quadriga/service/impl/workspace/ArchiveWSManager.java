package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionArchiveWSManger;
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
	@Qualifier("DBConnectionArchiveWSMangerBean")
	private IDBConnectionArchiveWSManger dbConnect;
	
	/**
	 * This will archive the requested workspace.[archive = 1 is supplied to database]
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	public String archiveWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		int archive;
		String errmsg;
		
		//assigning the archive parameter 1 species archive a workspace
		archive = 1;
		
		errmsg = dbConnect.archiveWorkspace(workspaceIdList, archive, wsUser);
		 
		return errmsg;
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
	public String unArchiveWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		int archive;
		String errmsg;
		
		//assigning the archive parameter 1 species archive a workspace
		archive = 0;
		
		errmsg = dbConnect.archiveWorkspace(workspaceIdList, archive, wsUser);
		 
		return errmsg;
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
	public String deactivateWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		int deactivate;
		String errmsg;
		
		//assigning the deactivate variable.
		deactivate = 1;
		
		errmsg = dbConnect.deactivateWorkspace(workspaceIdList, deactivate, wsUser);
		return errmsg;
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
	public String activateWorkspace(String workspaceIdList,String wsUser) throws QuadrigaStorageException
	{
		int deactivate;
		String errmsg;
		//assigning the deactivate variable.
		deactivate = 0;
		errmsg = dbConnect.deactivateWorkspace(workspaceIdList, deactivate, wsUser);
		return errmsg;
	}

}
