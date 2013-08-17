package edu.asu.spring.quadriga.aspects;

import java.security.Principal;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.NoAuthorizationCheck;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSAccessManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Aspect
@Component
public class ProjectAccessAspect 
{
	
	@Autowired
	@Qualifier("DBConnectionProjectAccessManagerBean")
	private IDBConnectionProjectAccessManager dbProjConnect;
	
	
	@Autowired
	@Qualifier("DBConnectionWSAccessManagerBean")
	private IDBConnectionWSAccessManager dbWSConnect;
	
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	
	//local class variable to find the user name
	private String userName="";
	
	//create point cut methods
	@Pointcut("within(edu.asu.spring.quadriga.web..*)")
	public void chkProjectOwner()
	{}
	
	@Pointcut("within(edu.asu.spring.quadriga.web..*)")
	public void chkProjectCollaborator()
	{}
	
	
	@Around("within(edu.asu.spring.quadriga.web..*) && @annotation(noCheck)")
	public Object chkProjectAuthorization(ProceedingJoinPoint pjp, NoAuthorizationCheck noCheck) throws Throwable 
	{
		return pjp.proceed();
	}
	

	@Around("within(edu.asu.spring.quadriga.web..*) && @annotation(checks)")
	public Object chkAuthorization(ProceedingJoinPoint pjp, AccessPolicies checks) throws Throwable 
	{
		boolean haveAccess;
		//retrieve the logged in user name
		for(Object obj : pjp.getArgs())
		{
			if(obj.getClass().toString().equals("class org.springframework.security.authentication.UsernamePasswordAuthenticationToken"))
			{
				Principal principal = (Principal)obj;
				userName = principal.getName();
			}
		}
		
		//loop through all the access policies
		ElementAccessPolicy[] policies = checks.value();
		
		for(ElementAccessPolicy policy : policies)
		{
			if(policy.paramIndex()>0)
			{
			//retrieve the type
			 haveAccess = chkAccessById(userName,policy.type().toString(),pjp.getArgs()[policy.paramIndex()-1].toString(),policy.userRole());
			
			if(!haveAccess)
			{
				throw new QuadrigaAccessException();
			}
			}
		}
		Object retVal = pjp.proceed();
		return retVal;
	}
	
	public boolean chkAccessById(String user,String type,String requestedAccessId,String[] roles) throws QuadrigaStorageException
	{
		boolean haveAccess;
		List<ICollaboratorRole> collaboratorRoles;
		
		haveAccess = false;
		
		//retrieve project id
		if(type.equals("PROJECT"))
		{
			
			//fetch the details of the project
			IProject project = projectManager.getProjectDetails(requestedAccessId);
			
			//check if the logged in user is project manager
			if( user.equals(project.getOwner().getUserName()))
				{
				   haveAccess = true;
				}
			
			if(!haveAccess)
			{
				//check if the logger in user is a collaborator and have some rolls
				List<ICollaborator> collaboratorList = project.getCollaborators();
				
				for(ICollaborator collaborator : collaboratorList)
				{
					if(collaborator.getUserObj().getUserName().equals(user))
					{
						collaboratorRoles = collaborator.getCollaboratorRoles();
						for(String role : roles)
						{
							System.out.println("Permission role :"+role);
							for(ICollaboratorRole collabRole : collaboratorRoles)
							{
								System.out.println("Collaborator role :"+collabRole.getRoleid());
								if(collabRole.getRoleid().equals(role.trim()))
								{
									haveAccess = true;
									break;
								}
							}
						}
					}
				}
			}
			return haveAccess;
				
		}
		if(type.equals("WORKSPACE"))
		{
			//check if the logged in user is workspace owner
			haveAccess = dbWSConnect.chkWorkspaceOwner(user, requestedAccessId);
		}
		return haveAccess;
	}
}
