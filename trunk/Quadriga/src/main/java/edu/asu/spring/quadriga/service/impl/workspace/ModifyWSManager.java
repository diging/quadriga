package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManger;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;

/**
 * Class implements {@link IModifyWSManager} to
 * add/update/delete workspace associated with project.
 * @implements IListWSManager
 * @author Kiran Kumar Batna
 */
@Service
public class ModifyWSManager implements IModifyWSManager 
{
	@Autowired
	@Qualifier("DBConnectionModifyWSManagerBean")
	private IDBConnectionModifyWSManger dbConnect;
	
	/**
	 * This inserts a workspace for a project into database.
	 * @param     workspace
	 * @param     projectId
	 * @return    String errmsg - blank on success and error message on failure
	 * @throws    QuadrigaStorageException
	 */
	@Override
	public String addWorkSpaceRequest(IWorkSpace workspace,String projectId) throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.addWorkSpaceRequest(workspace,projectId);
		
		return errmsg;
	}
	
	/**
	 * This method deletes the requested workspace.
	 * @param   workspaceIdList
	 * @return  String - errmsg blank on success and error message on failure
	 * @throws  QuadrigaStorageException
	 */
	@Override
	public String deleteWorkspaceRequest(String workspaceIdList) throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.deleteWorkspaceRequest(workspaceIdList);
		return errmsg;
	}
	

}
