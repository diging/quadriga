package edu.asu.spring.quadriga.accesschecks.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.ICheckProjectSecurity;
import edu.asu.spring.quadriga.accesschecks.ICheckWSSecurity;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSAccessManager;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class CheckWSSecurity implements ICheckWSSecurity 
{
	@Autowired
	private ICheckProjectSecurity projectSecurity;

	@Autowired
	private IRetrieveWSCollabManager workspaceManager;

	@Autowired
	private IDBConnectionWSAccessManager dbConnect;

	/**
	 * This method checks if the user has access to create a worksapce.
	 * @param userName
	 * @param projectId
	 * @return boolean - TRUE if user has access else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean chkCreateWSAccess(String userName,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//check if the user is a project owner
		chkAccess = projectSecurity.checkProjectOwner(userName,projectId);

		//check if the user is a project collaborator and has a ADMIN role
		if(!chkAccess)
		{
			chkAccess = projectSecurity.checkCollabProjectAccess(userName, projectId, RoleNames.ROLE_COLLABORATOR_ADMIN);
		}

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
	@Transactional
	public boolean chkWorkspaceAccess(String userName,String projectId,String workspaceId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize the variable
		chkAccess = false;

		//check if the user is a project owner
		chkAccess = projectSecurity.checkProjectOwner(userName,projectId);

		//check if the user is workspace owner
		if(!chkAccess)
		{
			chkAccess = dbConnect.chkWorkspaceOwner(userName, workspaceId);
		}

		//check if the user is a project collaborator having ADMIN role
		if(!chkAccess)
		{
			chkAccess = projectSecurity.checkCollabProjectAccess(userName, projectId, RoleNames.ROLE_QUADRIGA_ADMIN);
		}

		//check if user is project collaborator having PROJECT_ADMIN role
		if(!chkAccess)
		{
			chkAccess = projectSecurity.checkCollabProjectAccess(userName, projectId, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN);
		}

		return chkAccess;
	}

//	/**
//	 * This checks if the user has the specified collaborator role
//	 * @param userName
//	 * @param workspaceId
//	 * @param collaboratorRole
//	 * @return boolean - TRUE if the user role is same as supplied else FALSE
//	 * @throws QuadrigaStorageException
//	 * @author kiranbatna
//	 */
//	@Override
//	@Transactional
//	public boolean chkCollabWorkspaceAccess(String userName,String workspaceId,String collaboratorRole) throws QuadrigaStorageException
//	{
//		List<ICollaborator> collaboratorList;
//		List<ICollaboratorRole> collaboratorRoles;
//		boolean chkAccess;
//		
//		//initialize the local variable
//		chkAccess = false;
//		
//		//fetch the collaborators associated with the workspace
//		collaboratorList = workspaceManager.getWorkspaceCollaborators(workspaceId);
//		
//		for(ICollaborator collaborator : collaboratorList)
//		{
//			//check if the user is one of the collaborators
//			if(collaborator.getUserObj().getUserName() == userName)
//			{
//				collaboratorRoles = collaborator.getCollaboratorRoles();
//				
//				//check if the collaborator is the supplied collaborator role
//				for(ICollaboratorRole role : collaboratorRoles)
//				{
//					if(role.getRoleid() == collaboratorRole)
//					{
//						chkAccess = true;
//						break;
//					}
//				}
//				// break through the outer loop
//				break;
//			}
//		}
//		return chkAccess;
//	}
	
	/**
	 * This checks if the user has the specified collaborator role
	 * @param userName
	 * @param workspaceId
	 * @param collaboratorRole
	 * @return boolean - TRUE if the user role is same as supplied else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean chkCollabWorkspaceAccess(String userName,String workspaceId,String collaboratorRole) throws QuadrigaStorageException
	{
		List<IWorkspaceCollaborator> workspaceCollaboratorList = null;
		List<ICollaboratorRole> collaboratorRoles = null;
		boolean chkAccess;

		//initialize the local variable
		chkAccess = false;

		//fetch the collaborators associated with the workspace
		workspaceCollaboratorList = workspaceManager.getWorkspaceCollaborators(workspaceId);

		if(workspaceCollaboratorList != null){
			for(IWorkspaceCollaborator workspaceCollaborator : workspaceCollaboratorList)
			{
				//check if the user is one of the collaborators
				if(workspaceCollaborator.getCollaborator() != null){
					if(workspaceCollaborator.getCollaborator().getUserObj().getUserName() == userName)
					{
						collaboratorRoles = workspaceCollaborator.getCollaborator().getCollaboratorRoles();

						if(collaboratorRoles != null){
							//check if the collaborator is the supplied collaborator role
							for(ICollaboratorRole role : collaboratorRoles)
							{
								if(role.getRoleid() == collaboratorRole)
								{
									chkAccess = true;
									break;
								}
							}
						}
						// break through the outer loop
						break;
					}
				}
			}
		}
		return chkAccess;
	}

	/**
	 * This method is used to check if the user has access to modify workspace
	 * @param userName
	 * @param workspaceId
	 * @return boolean - TRUE if user has access else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean chkModifyWorkspaceAccess(String userName,String workspaceId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize the variable
		chkAccess = false;

		//check if the user is Workspace owner
		chkAccess = dbConnect.chkWorkspaceOwner(userName, workspaceId);

		if(!chkAccess)
		{
			//check if the user has collaborator role SINGLE WORKSPACE ADMIN
			chkAccess = this.chkCollabWorkspaceAccess(userName, workspaceId, RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN);
		}

		return chkAccess;
	}

	@Override
	@Transactional
	public boolean checkWorkspaceOwner(String userName,String workspaceId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize check Access variable
		chkAccess = false;

		//check if the user is project owner
		chkAccess = dbConnect.chkWorkspaceOwner(userName,workspaceId);

		return chkAccess;

	}

	@Override
	@Transactional
	public boolean checkIsWorkspaceAssociated(String userName) throws QuadrigaStorageException
	{
		boolean isAssociated;
		isAssociated = false;

		isAssociated = dbConnect.chkIsWorkspaceAssocaited(userName);
		return isAssociated;
	}

	@Override
	@Transactional
	public boolean chkIsCollaboratorWorkspaceAssociated(String userName,String role) throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean isAssociated;
		isAssociated = false;

		isAssociated = dbConnect.chkIsCollaboratorWorkspaceAssociated(userName, role);
		return isAssociated;
	}

	/**
	 * This method checks if the user is workspace owner and has editor role to this workspace
	 * @param userName
	 * @return boolean - TRUE if the user is either a project owner or a quadriga admin else FALSE
	 * @throws QuadrigaStorageException 
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean checkWorkspaceOwnerEditorAccess(String userName,String workspaceId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize chkAccess variable
		chkAccess = false;

		//check if the user is project owner
		chkAccess = dbConnect.chkWorkspaceOwnerEditorRole(userName, workspaceId);
		return chkAccess;
	}

	/**
	 * This method checks if the user is project owner and has editor roles. If this editor is inherit to workspace access
	 * @param userName
	 * @return boolean - TRUE if the user is either a project owner or a quadriga admin else FALSE
	 * @throws QuadrigaStorageException 
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean checkWorkspaceProjectInheritOwnerEditorAccess(String userName,String workspaceId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize chkAccess variable
		chkAccess = false;

		//check if the user is project owner
		chkAccess = dbConnect.chkWorkspaceProjectInheritOwnerEditorRole(userName, workspaceId);
		return chkAccess;
	}

	@Override
	@Transactional
	public boolean checkIsWorkspaceExists(String workspaceId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize the chkAccess variable
		chkAccess = false;

		//check if the workspace exists
		chkAccess = dbConnect.chkWorkspaceExists(workspaceId);
		return chkAccess;
	}
}
