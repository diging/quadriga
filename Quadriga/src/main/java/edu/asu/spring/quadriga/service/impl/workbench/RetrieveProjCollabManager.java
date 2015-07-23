package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;

//show collaborators for a project
//show non collaborators for a project
@Service
public class RetrieveProjCollabManager implements IRetrieveProjCollabManager 
{

	@Autowired
	private IQuadrigaRoleManager roleManager;

	@Autowired
	private IProjectDeepMapper projectDeepMapper;

	@Autowired
	private IDBConnectionRetrieveProjCollabManager dbConnect;

	/**
	 * This method retrieves the users who are not collaborators to the project.
	 * @param projectid - project id
	 * @return List<IUser> - list of users who are not collaborators to the project.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<IUser> getProjectNonCollaborators(String projectid) throws QuadrigaStorageException
	{
		List<IUser> nonCollaborators;

		nonCollaborators = dbConnect.getProjectNonCollaborators(projectid);

		return nonCollaborators;
	}

	/**
	 * This method retrieves the collaborators associated with the project
	 * @param projectId - project id
	 * @return List<ICollaborator> - list of collaborators associated with the project.
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<IProjectCollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException
	{
		List<IProjectCollaborator> projectCollaboratorList = null;
		//retrieve the collaborators associated with project

		IProject project =  projectDeepMapper.getProjectDetails(projectId);
		if(project != null){
			projectCollaboratorList = project.getProjectCollaborators();
		}

		//map the collaborators to UI XML values
		if(projectCollaboratorList!=null){
			for (IProjectCollaborator projectCollaborator : projectCollaboratorList) 
			{
				if(projectCollaborator.getCollaborator() != null && projectCollaborator.getCollaborator().getCollaboratorRoles() != null){
					for (IQuadrigaRole collaboratorRole : projectCollaborator.getCollaborator().getCollaboratorRoles()) {
						roleManager.fillQuadrigaRole(IQuadrigaRoleManager.PROJECT_ROLES, collaboratorRole);
					}
				}
			}
		}
		return projectCollaboratorList;
	}
}
