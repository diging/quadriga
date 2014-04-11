package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.email.IEmailNotificationManager;
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
	private IDBConnectionModifyWSManager dbConnect;

	@Autowired
	private IDBConnectionRetrieveProjectManager dbProjectManager;

	@Autowired
	private IEmailNotificationManager emailManager;

	/**
	 * This inserts a workspace for a project into database.
	 * @param     workspace
	 * @param     projectId
	 * @return    String errmsg - blank on success and error message on failure
	 * @throws    QuadrigaStorageException
	 * @author    kiranbatna
	 */
	@Override
	@Transactional
	public void addWorkSpaceRequest(IWorkSpace workspace,String projectId) throws QuadrigaStorageException
	{
		//dbConnect.addWorkSpaceRequest(workspace,projectId);
		
		dbConnect.addWorkSpaceRequest(workspace, projectId);
		
		//Get project owner
		IProject project = dbProjectManager.getProjectDetails(projectId);
		
		//TODO: Remove email setup
		/*project.getOwner().setEmail("ramkumar007@gmail.com");

		//Set email to owner that a new workspace has been added.
		if(project.getOwner().getEmail() != null && !project.getOwner().getEmail().equals(""))
			emailManager.sendNewWorkspaceAddedToProject(project, workspace);
		else
			logger.info("The project owner <<"+project.getOwner()+">> did not have an email address to the notification for new workspace addition");*/

	}

	/**
	 * This method deletes the requested workspace.
	 * @param   workspaceIdList
	 * @return  String - errmsg blank on success and error message on failure
	 * @throws  QuadrigaStorageException
	 * @author  kiranbatna
	 */
	@Override
	@Transactional
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
	@Transactional
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
	@Transactional
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
	@Transactional
	public void assignEditorRoleToOwner(String workspaceId,String userName) throws QuadrigaStorageException{
		dbConnect.assignWorkspaceOwnerEditor(workspaceId, userName);
	}

	/**
	 * Manager for Assigning editor roles to owner for workspace level
	 * @param workspaceId
	 * @param userName
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String deleteEditorRoleToOwner(String workspaceId,String userName) throws QuadrigaStorageException{

		String msg =dbConnect.deleteWorkspaceOwnerEditor(workspaceId, userName);
		return msg;
	}

}
