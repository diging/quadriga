package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service
public class RetrieveProjectManager implements IRetrieveProjectManager 
{
	@Autowired
	@Qualifier("DBConnectionRetrieveProjectManagerBean")
	private IDBConnectionRetrieveProjectManager dbConnect;
	
	@Autowired
	@Qualifier("DBConnectionRetrieveProjCollabManagerBean")
	private IDBConnectionRetrieveProjCollabManager databaseConnect;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	/**
	 * This method returns the list of projects associated with
	 * the logged in user.
	 * @param sUserName
	 * @return List - list of projects.
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	@Override
	public List<IProject> getProjectList(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		
		projectList = dbConnect.getProjectList(sUserName);
		
		return projectList;
	}
	
	/**
	 * This method returns the project details for the supplied project.
	 * @param projectId
	 * @return Iproject
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	@Override
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException
	{
		IProject project;
		List<ICollaborator> collaboratorList;
		
		//retrieve project details
		project = dbConnect.getProjectDetails(projectId);
		
		//retrieve the collaborators associated with project
		collaboratorList = databaseConnect.getProjectCollaborators(projectId);
		
		//map the collaborators to UI XML values
		for (ICollaborator collaborator : collaboratorList) 
		{
			for (ICollaboratorRole collaboratorRole : collaborator.getCollaboratorRoles()) {
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
			}
		}
		//assigning the collaborators to the project
		project.setCollaborators(collaboratorList);
		
		return project;
	}

}
