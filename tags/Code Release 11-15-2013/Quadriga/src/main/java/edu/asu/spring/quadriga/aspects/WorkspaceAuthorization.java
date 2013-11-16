package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

@Service("workspaceAuthorization")
public class WorkspaceAuthorization implements IAuthorization 
{
	@Autowired
	private	IListWSManager wsManager;
	
	@Autowired
	private	IRetrieveWSCollabManager wsCollabManager;
	
	@Autowired
	private ICheckWSSecurity wsSecurityManager;
	
	@Override
	public boolean chkAuthorization(String userName,String workspaceId,String[] userRoles) throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		String workspaceOwner;
		String collaboratorName;
		String collaboratorRoleId;
		IWorkSpace workspace;
		List<ICollaborator> collaboratorList;
		List<ICollaboratorRole> collaboratorRoles;
		ArrayList<String> roles;
		
		haveAccess = false;
		
		//fetch the details of the workspace
		workspace = wsManager.getWorkspaceDetails(workspaceId, userName);
		
		//check if the logged in user is workspace owner
		if(workspace.getOwner()!=null)
		{
			workspaceOwner = workspace.getOwner().getUserName();
			
			if(userName.equals(workspaceOwner))
			{
				haveAccess = true;
			}
			
			if(!haveAccess)
			{
				if(userRoles.length>0)
				{
					roles = getAccessRoleList(userRoles);
					collaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceId);
					for(ICollaborator collaborator : collaboratorList)
					{
						//check if he is a collaborator to the project
						collaboratorName = collaborator.getUserObj().getUserName();
						
						if(userName.equals(collaboratorName))
						{
							collaboratorRoles = collaborator.getCollaboratorRoles();
							
								for(ICollaboratorRole collabRole : collaboratorRoles)
								{
									collaboratorRoleId = collabRole.getRoleid();
									if(roles.contains(collaboratorRoleId))
									{
										haveAccess = true;
										return haveAccess;
									}
								}
						}
					}
				}
			}
		}
		return haveAccess;
	}
	
	/**
	 * check if the user as a owner has any workspaces associated
	 * check if the user as the given role has any workspaces associated
	 */
	@Override
	public boolean chkAuthorizationByRole(String userName,String[] userRoles )
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		ArrayList<String> roles;
		haveAccess = false;
		
		//fetch the details of the project
		haveAccess = wsSecurityManager.checkIsWorkspaceAssociated(userName);
		
		//check the user roles if he is not a project owner
		if(!haveAccess)
		{
			if(userRoles.length>0)
			{
				roles = getAccessRoleList(userRoles);
				
				//check if the user associated with the role has any projects
				for(String role : roles)
				{
					haveAccess = wsSecurityManager.chkIsCollaboratorWorkspaceAssociated(userName, role);
					
					if(haveAccess)
						break;
				}
			}
		}
		return haveAccess;
		
	}
	
	public ArrayList<String> getAccessRoleList(String[] userRoles)
	{
		ArrayList<String> rolesList = new ArrayList<String>();
		
		for(String role : userRoles)
		{
			rolesList.add(role);
		}
		
		return rolesList;
	}
}
