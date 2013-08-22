package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
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
	private IDBConnectionModifyWSManager dbConnect;
	
	/**
	 * This inserts a workspace for a project into database.
	 * @param     workspace
	 * @param     projectId
	 * @return    String errmsg - blank on success and error message on failure
	 * @throws    QuadrigaStorageException
	 * @author    kiranbatna
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
	 * @author  kiranbatna
	 */
	@Override
	public String deleteWorkspaceRequest(String workspaceIdList) throws QuadrigaStorageException
	{
		String errmsg;
		
		errmsg = dbConnect.deleteWorkspaceRequest(workspaceIdList);
		return errmsg;
	}
	
	/**
	 * This method updates the workspace
	 * @param workspace
	 * @return String - errmsg blank on success and error message on failure
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public String updateWorkspaceRequest(IWorkSpace workspace ) throws QuadrigaStorageException 
	{
		String errmsg;
		
		errmsg = dbConnect.updateWorkspaceRequest(workspace);
		
		return errmsg;
	}
	
	/**
	 * This method assigns a new owner to the workspace
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public void transferWSOwnerRequest(String workspaceId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		dbConnect.transferWSOwnerRequest(workspaceId, oldOwner, newOwner, collabRole);
	}
	
	/**
	 * Manager for Assigning editor roles to owner for workspace level
	 * @param workspaceId
	 * @param userName
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public String assignEditorRoleToOwner(String workspaceId,String userName) throws QuadrigaStorageException{
		
		String msg =dbConnect.assignWorkspaceOwnerEditor(workspaceId, userName);
		return msg;
	}
	
	/**
	 * Manager for Assigning editor roles to owner for workspace level
	 * @param workspaceId
	 * @param userName
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public String deleteEditorRoleToOwner(String workspaceId,String userName) throws QuadrigaStorageException{
		
		String msg =dbConnect.deleteWorkspaceOwnerEditor(workspaceId, userName);
		return msg;
	}

}
