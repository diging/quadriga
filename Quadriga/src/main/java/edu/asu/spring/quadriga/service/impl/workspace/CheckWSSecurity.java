package edu.asu.spring.quadriga.service.impl.workspace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSAccessManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class CheckWSSecurity implements ICheckWSSecurity 
{
	@Autowired
	private ICheckProjectSecurity projectSecurity;
	
	@Autowired
	@Qualifier("DBConnectionWSAccessManagerBean")
	private IDBConnectionWSAccessManager workspaceManager;
	
	/**
	 * This method checks if the user has access to create a worksapce.
	 * @param userName
	 * @param projectId
	 * @return boolean - TRUE if user has access else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public boolean chkCreateWSAccess(String userName,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;
		
		//check if the user is quadriga Admin or project owner
		chkAccess = projectSecurity.checkProjectAccess(userName, projectId);
		
		//check if the user is a project collaborator and has PROJECT_ADMIN role
		if(!chkAccess)
		{
			chkAccess = projectSecurity.checkCollabProjectAccess(userName, projectId, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN);
		}
		
		//check if the user is a project collaborator and has CONTRIBUTOR role
		if(!chkAccess)
		{
			chkAccess = projectSecurity.checkCollabProjectAccess(userName, projectId, RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR);
		}
		
		return chkAccess;
	}
	
	/**
	 * This method checks if the user has access to Archive/Deactivate/Delete workspace
	 * @param userName
	 * @param projectId
	 * @param workspaceId
	 * @return boolean - TRUE if the user has access else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public boolean chkWorkspaceAccess(String userName,String projectId,String workspaceId) throws QuadrigaStorageException
	{
	    boolean chkAccess;
	    
	    //initialize the variable
	    chkAccess = false;
	    
	    //check if the user is quadriga admin or project owner
	    chkAccess  = projectSecurity.checkProjectAccess(userName, projectId);
	    
	    //check if the user is workspace owner
	    if(!chkAccess)
	    {
	    	chkAccess = workspaceManager.chkWorkspaceOwner(userName, workspaceId);
	    }
	    
	    //check if user is project collaborator having PROJECT_ADMIN role
	    if(!chkAccess)
	    {
	    	chkAccess = projectSecurity.checkCollabProjectAccess(userName, projectId, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN);
	    }
	    
		return chkAccess;
	}
	
	

}
