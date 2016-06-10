package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * Service level Implementation of {@link IAuthorization} for {@link IProject}.
 * This class specifically works on authorization check of user for {@link IProject} access. 
 * @author Kiran kumar
 *
 */
@Service("projectAuthorization")
public class ProjectAuthorization implements IAuthorization
{	
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private IPassThroughProjectManager passThroughManager;
	
	@Autowired
	private IProjectSecurityChecker projectSecurityManager;
	
	/**
	 * This checks the access permissions for the logged in user for the 
	 * given project id
	 * @param : userName - logged in user
	 * @param : userRoles - set of roles for which the user should be checked 
	 * for access.
	 * @param : projectId 
	 * @throws : QuadrigaStorageException, QuadrigaAccessException
	 * @return : hasAccess - true
	 *           no Access - false
	 */
	@Override
	public boolean chkAuthorization(String userName,String projectId,String[] userRoles) 
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		String projectOwner;
		String collaboratorName;
		String collaboratorRoleId;
		List<IQuadrigaRole> collaboratorRoles;
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
				List<IProjectCollaborator> projectCollaborators = project.getProjectCollaborators();
				
				if (projectCollaborators != null) {
    				for(IProjectCollaborator projectCollaborator : projectCollaborators) {
    					ICollaborator collaborator = projectCollaborator.getCollaborator();
    					//check if he is a collaborator to the project
    					collaboratorName = collaborator.getUserObj().getUserName();
    				
    					if(userName.equals(collaboratorName)) {
    						collaboratorRoles = collaborator.getCollaboratorRoles();
    						
							for(IQuadrigaRole collabRole : collaboratorRoles) {
								collaboratorRoleId = collabRole.getId();
								if(roles.contains(collaboratorRoleId)) {
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
     * This checks the access permissions for the logged in user for the 
     * given list of project ids
     * @param : userName - logged in user
     * @param : userRoles - set of roles for which the user should be checked 
     * for access.
     * @param : projectIds - list of projectId 
     * @throws : QuadrigaStorageException, QuadrigaAccessException
     * @return : hasAccess - true
     *           no Access - false
     */
    @Override
    public boolean chkAuthorization(String userName, List<String> projectIds,String[] userRoles) 
            throws QuadrigaStorageException, QuadrigaAccessException
    {
        boolean haveAccess;
        haveAccess = false;
        for(String projectId : projectIds)
        {
            haveAccess = chkAuthorization(userName, projectId, userRoles);
            if(!haveAccess)
            {
                break;
            }
        }
        return haveAccess;
    }

		
	/**
	 * This checks if the logged in user is associated with any project and possess
	 * any of the specified roles.
	 * @param : userName - logged in user
	 * @param : userRoles - set of roles for which the user should be checked 
	 * for access.
	 * @throws : QuadrigaStorageException, QuadrigaAccessException
	 * @return : hasAccess - true
	 *           no Access - false
	 */
	@Override
	public boolean chkAuthorizationByRole(String userName,String[] userRoles )
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		boolean haveAccess;
		ArrayList<String> roles;
		haveAccess = false;
		
		//fetch the details of the project
		haveAccess = projectSecurityManager.ownsAtLeastOneProject(userName);
		
		//check the user roles if he is not a project owner
		if(!haveAccess)
		{
			if(userRoles.length>0)
			{
				roles = getAccessRoleList(userRoles);
				
				//check if the user associated with the role has any projects
				for(String role : roles)
				{
					haveAccess = projectSecurityManager.collaboratesOnAtLeastOneProject(userName, role);
					if(haveAccess)
						break;
				}
			}
		}
		return haveAccess;
	}
	
	/**
	 * This method converts the the string array into a list
	 * @param userRoles
	 * @return ArrayList<String>
	 */
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