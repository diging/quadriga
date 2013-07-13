package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class CheckProjectSecurity implements ICheckProjectSecurity 
{
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	@Qualifier("DBConnectionProjectAccessManagerBean")
	private IDBConnectionProjectAccessManager dbConnect;
	
	@Autowired
	private IRetrieveProjCollabManager projectManager;
	
	/**
	 * This method checks if the user is Quadriga Admin
	 * @param userName
	 * @return boolean - TRUE if the user is quadriga Admin else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public boolean checkQudrigaAdmin(String userName) throws QuadrigaStorageException
	{
		boolean chkAccess;
		IUser user;
		List<IQuadrigaRole> quadrigaRoles;
		
		//initialize chkAccess variable
		chkAccess = false;
		
		user = userManager.getUserDetails(userName);
		quadrigaRoles = user.getQuadrigaRoles();
		for(IQuadrigaRole quadRole : quadrigaRoles)
		{
			if(quadRole.getId().equals(RoleNames.ROLE_QUADRIGA_ADMIN))
			{
				chkAccess = true;
				break;
			}
		}
		
		return chkAccess;
	}
	
	/**
	 * This method checks if the user is project owner
	 * @param userName
	 * @return boolean - TRUE if the user is project owner else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	
	@Override
	public boolean checkProjectOwner(String userName,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;
		
		//initialize check Access variable
		chkAccess = false;
		
		//check if the user is project owner
		chkAccess = dbConnect.chkProjectOwner(userName,projectId);
		
		return chkAccess;
		
	}
	
	/**
	 * This method checks if the project collaborator has access to perform operations.
	 * @param userName
	 * @param projectId
	 * @param collaboratorRole
	 * @return boolean - TRUE if he has access else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public boolean checkCollabProjectAccess(String userName,String projectId,String collaboratorRole) throws QuadrigaStorageException
	{
		List<ICollaborator> collaboratorList;
		List<ICollaboratorRole> collaboratorRoles;
		boolean chkAccess;
		
		//initialize the local variable
		chkAccess = false;
		
		//fetch the collaborators associated with the project
		collaboratorList = projectManager.getProjectCollaborators(projectId);
		
		//loop through each collaborator
		for(ICollaborator collaborator : collaboratorList)
		{
			//check if the user is one of the collaborators
			if(collaborator.getUserObj().getUserName() == userName)
			{
				collaboratorRoles = collaborator.getCollaboratorRoles();
				
				//check if the collaborator is Project Admin or Contributor
				for(ICollaboratorRole role : collaboratorRoles)
				{
					if(role.getRoleid() == collaboratorRole)
					{
						chkAccess = true;
						break;
					}
				}
				// break through the outer loop
				break;
			}
		}
		return chkAccess;
	}
	
	/**
	 * This method checks if the user is either a project owner or a quadriga admin
	 * @param userName
	 * @return boolean - TRUE if the user is either a project owner or a quadriga admin else FALSE
	 * @throws QuadrigaStorageException 
	 * @author kiranbatna
	 */
	@Override
	public boolean checkProjectAccess(String userName,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;
		
		//initialize chkAccess variable
		chkAccess = false;
		
		//check if the user is quadriga admin
		chkAccess = this.checkQudrigaAdmin(userName);
		
		if(!chkAccess)
		{
			//check if the user is project owner
			chkAccess = this.checkProjectOwner(userName,projectId);
		}
		
		return chkAccess;
		
	}

}
