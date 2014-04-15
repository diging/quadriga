package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service("projectAuthorization")
public class ProjectAuthorization implements IAuthorization
{	
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private ICheckProjectSecurity projectSecurityManager;
	
	@Override
	public boolean chkAuthorization(String userName,String projectId,String[] userRoles) 
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		String projectOwner;
		String collaboratorName;
		String collaboratorRoleId;
		List<ICollaboratorRole> collaboratorRoles;
		ArrayList<String> roles;
		haveAccess = false;
		
		//fetch the details of the project
		IProject project = projectManager.getProjectDetails(projectId);
		
		projectOwner = project.getOwner().getUserName();
		if(userName.equals(projectOwner))
		{
			haveAccess = true;
		}
		
		//check the user roles if he is not a project owner
		if(!haveAccess)
		{
			if(userRoles.length>0)
			{
				roles = getAccessRoleList(userRoles);
				List<ICollaborator> collaboratorList = project.getCollaborators();
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
		return haveAccess;
	}
	
	@Override
	public boolean chkAuthorizationByRole(String userName,String[] userRoles )
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		ArrayList<String> roles;
		haveAccess = false;
		
		//fetch the details of the project
		haveAccess = projectSecurityManager.chkIsProjectAssociated(userName);
		
		//check the user roles if he is not a project owner
		if(!haveAccess)
		{
			if(userRoles.length>0)
			{
				roles = getAccessRoleList(userRoles);
				
				//check if the user associated with the role has any projects
				for(String role : roles)
				{
					haveAccess = projectSecurityManager.chkIsCollaboratorProjectAssociated(userName, role);
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