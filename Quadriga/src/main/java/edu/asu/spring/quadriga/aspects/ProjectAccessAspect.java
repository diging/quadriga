package edu.asu.spring.quadriga.aspects;

import java.security.Principal;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.NoAuthorizationCheck;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

@Aspect
@Component
public class ProjectAccessAspect 
{
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private	IListWSManager wsManager;
	
	@Autowired
	private	IRetrieveWSCollabManager wsCollabManager;
	
	
	//local class variable to find the user name
	private String userName="";
	
	@Around("within(edu.asu.spring.quadriga.web..*) && @annotation(noCheck)")
	public Object chkProjectAuthorization(ProceedingJoinPoint pjp, NoAuthorizationCheck noCheck) throws Throwable 
	{
		return pjp.proceed();
	}
	

	@Around("within(edu.asu.spring.quadriga.web..*) && @annotation(checks)")
	public Object chkAuthorization(ProceedingJoinPoint pjp, AccessPolicies checks) throws Throwable  
	{
		boolean haveAccess;
		String policyType;
		
		haveAccess = true;
		
		//retrieve the logged in User name
		for(Object obj : pjp.getArgs())
		{
			if(obj.getClass().toString().equals("class org.springframework.security.authentication.UsernamePasswordAuthenticationToken"))
			{
				Principal principal = (Principal)obj;
				userName = principal.getName();
			}
		}
		
		//Loop through all the access policies specified
		ElementAccessPolicy[] policies = checks.value();
		
		for(ElementAccessPolicy policy : policies)
		{
			//retrieve the type of access policy to be checked
			policyType = policy.type().toString();
			
			
			//check all the access policies associated to a project
			if(policyType.equals("PROJECT"))
			{
				if(policy.paramIndex() > 0)
				{
					String projectId = pjp.getArgs()[policy.paramIndex()-1].toString();
					System.out.println("Project id :"+projectId);
					haveAccess = chkProjectAccessById(userName,projectId,policy.userRole());
					System.out.println("After exeution :"+haveAccess);
				}
				
			}
			
			//check all the access policies associated to a workspace
			if(policyType.equals("WORKSPACE"))
			{
				if(policy.paramIndex() > 0)
				{
					String workspaceId = pjp.getArgs()[policy.paramIndex()-1].toString();
					haveAccess = chkWorkspaceAccessById(userName,workspaceId,policy.userRole());
		
				}
			}
			
		}
		
//		if(!haveAccess)
//		{
//			System.out.println("Count error :"+1);
//			 throw new QuadrigaAccessException();
//		}
		
		Object retVal = pjp.proceed();
		return retVal;
	}
	
	public boolean chkProjectAccessById(String userName,String projectId,String[] userRoles) throws QuadrigaStorageException
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
							System.out.println("User roles :"+userRoles);
							for(ICollaboratorRole collabRole : collaboratorRoles)
							{
								collaboratorRoleId = collabRole.getRoleid();
								System.out.println("Collaborator role :"+collaboratorRoleId);
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
	
	public boolean chkWorkspaceAccessById(String userName,String workspaceId,String[] userRoles) throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		String workspaceOwner;
		String collaboratorName;
		String collaboratorRoleId;
		IWorkSpace workspace;
		List<ICollaborator> collaboratorList;
		List<ICollaboratorRole> collaboratorRoles;
		
		haveAccess = false;
		
		//fetch the details of the workspace
		workspace = wsManager.getWorkspaceDetails(workspaceId, userName);
		
		System.out.println("workspace :"+workspace);
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
				if(!userRoles[0].equals("null"))
				{
					collaboratorList = wsCollabManager.getWorkspaceCollaborators(workspaceId);
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
		}
		
		
		return haveAccess;
	}
}
