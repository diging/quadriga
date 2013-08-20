package edu.asu.spring.quadriga.aspects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service("projectAuthorization")
public class ProjectAuthorization implements IAuthorization
{	
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Override
	public boolean chkAuthorization(String userName,String projectId,String[] userRoles) 
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		String projectOwner;
		String collaboratorName;
		String collaboratorRoleId;
		List<ICollaboratorRole> collaboratorRoles;
		
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
			if(!userRoles[0].equals("null"))
			{
				List<ICollaborator> collaboratorList = project.getCollaborators();
				for(ICollaborator collaborator : collaboratorList)
				{
					//check if he is a collaborator to the project
					collaboratorName = collaborator.getUserObj().getUserName();
				
					if(userName.equals(collaboratorName))
					{
						collaboratorRoles = collaborator.getCollaboratorRoles();
						
						for(String role : userRoles)
						{
							for(ICollaboratorRole collabRole : collaboratorRoles)
							{
								collaboratorRoleId = collabRole.getRoleid();
								if(role.equals(collaboratorRoleId))
								{
									haveAccess = true;
									break;
								}
							}
							if(haveAccess)
								break;
						}
					}
				}
			}
		}
		return haveAccess;
	}
}
