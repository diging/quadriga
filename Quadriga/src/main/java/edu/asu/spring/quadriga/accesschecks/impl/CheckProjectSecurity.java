package edu.asu.spring.quadriga.accesschecks.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.ICheckProjectSecurity;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Service
public class CheckProjectSecurity implements ICheckProjectSecurity 
{
	@Autowired
	private IUserManager userManager;

	@Autowired
	private IRetrieveProjCollabManager projectManager;

	@Autowired
	private IDBConnectionProjectAccessManager dbConnect;

	/**
	 * This method checks if the user is Quadriga Admin
	 * @param userName
	 * @return boolean - TRUE if the user is quadriga Admin else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
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
	@Transactional
	public boolean checkProjectOwner(String userName,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize check Access variable
		chkAccess = false;

		//check if the user is project owner
		chkAccess = dbConnect.chkProjectOwner(userName, projectId);

		return chkAccess;

	}

	@Override
	@Transactional
	public boolean chkIsProjectAssociated(String userName) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize check access variable
		chkAccess = false;

		//check if the use is associated with any project
		chkAccess = dbConnect.chkIsProjectAssociated(userName);

		return chkAccess;
	}

	@Override
	@Transactional
	public boolean chkIsCollaboratorProjectAssociated(String userName,String collaboratorRole) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize check access variable
		chkAccess = false;

		//check if the collaborator is associated with any project
		chkAccess = dbConnect.chkIsCollaboratorProjectAssociated(userName, collaboratorRole);


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
	@Transactional
	public boolean checkProjectCollaborator(String userName,String collaboratorRole,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize check Access variable
		chkAccess = false;

		//check if the user is project owner
		chkAccess = dbConnect.chkProjectCollaborator(userName, collaboratorRole, projectId);

		return chkAccess;

	}

//	/**
//	 * This method checks if the project collaborator has access to perform operations.
//	 * @param userName
//	 * @param projectId
//	 * @param collaboratorRole
//	 * @return boolean - TRUE if he has access else FALSE
//	 * @throws QuadrigaStorageException
//	 * @author kiranbatna
//	 */
//	@Override
//	@Transactional
//	public boolean checkCollabProjectAccess(String userName,String projectId,String collaboratorRole) throws QuadrigaStorageException
//	{
//		List<ICollaborator> collaboratorList;
//		List<ICollaboratorRole> collaboratorRoles;
//		boolean chkAccess;
//		
//		//initialize the local variable
//		chkAccess = false;
//		
//		//fetch the collaborators associated with the project
//		collaboratorList = projectManager.getProjectCollaborators(projectId);
//		
//		//loop through each collaborator
//		for(ICollaborator collaborator : collaboratorList)
//		{
//			//check if the user is one of the collaborators
//			if(collaborator.getUserObj().getUserName() == userName)
//			{
//				collaboratorRoles = collaborator.getCollaboratorRoles();
//				
//				//check if the collaborator is Project Admin or Contributor
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
	 * This method checks if the project collaborator has access to perform operations.
	 * @param userName
	 * @param projectId
	 * @param collaboratorRole
	 * @return boolean - TRUE if he has access else FALSE
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean checkCollabProjectAccess(String userName,String projectId,String collaboratorRole) throws QuadrigaStorageException
	{
		List<IProjectCollaborator> projectCollaboratorList = null;
		List<ICollaboratorRole> collaboratorRoles = null;

		boolean chkAccess;

		//initialize the local variable
		chkAccess = false;

		//fetch the collaborators associated with the project

		projectCollaboratorList = projectManager.getProjectCollaborators(projectId);

		if(projectCollaboratorList != null){
			//loop through each collaborator
			for(IProjectCollaborator projectCollaborator : projectCollaboratorList)
			{
				if(projectCollaborator.getCollaborator() != null){
					//check if the user is one of the collaborators
					if(projectCollaborator.getCollaborator().getUserObj().getUserName() == userName)
					{
						collaboratorRoles = projectCollaborator.getCollaborator().getCollaboratorRoles();

						if(collaboratorRoles != null){
							//check if the collaborator is Project Admin or Contributor
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
	 * This method checks if the user is either a project owner or a quadriga admin
	 * @param userName
	 * @return boolean - TRUE if the user is either a project owner or a quadriga admin else FALSE
	 * @throws QuadrigaStorageException 
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean checkProjectAccess(String userName,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize chkAccess variable
		chkAccess = false;

		//check if the user is project owner
		chkAccess = this.checkProjectOwner(userName,projectId);

		if(!chkAccess)
		{
			chkAccess = this.checkCollabProjectAccess(userName, projectId, RoleNames.ROLE_QUADRIGA_ADMIN);
		}
		return chkAccess;
	}

	/**
	 * This method checks if the user is Project owner and has editor role to this Project
	 * @param userName
	 * @return boolean - TRUE if the user is either a project owner or a quadriga admin else FALSE
	 * @throws QuadrigaStorageException 
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public boolean checkProjectOwnerEditorAccess(String userName,String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize chkAccess variable
		chkAccess = false;

		//check if the user is project owner
		chkAccess = dbConnect.chkProjectOwnerEditorRole(userName, projectId);
		return chkAccess;
	}

	@Override
	@Transactional
	public boolean chkDuplicateProjUnixName(String unixName, String projectId) throws QuadrigaStorageException
	{
		boolean chkAccess;

		//initialize chkAccess variable
		chkAccess = false;

		chkAccess = dbConnect.chkDuplicateProjUnixName(unixName, projectId);

		return chkAccess;
	}
}
