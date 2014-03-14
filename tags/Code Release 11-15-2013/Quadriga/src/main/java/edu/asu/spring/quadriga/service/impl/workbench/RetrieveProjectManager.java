package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectManagerDAO;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workbench.ICheckProjectSecurity;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service
public class RetrieveProjectManager implements IRetrieveProjectManager 
{
	@Autowired
	@Qualifier("DBConnectionRetrieveProjectManagerBean")
	private IDBConnectionRetrieveProjectManager dbConnect;
	
	@Autowired
	private IRetrieveProjectManagerDAO retrieveProjectManagerDAO;
	
	@Autowired
	@Qualifier("DBConnectionRetrieveProjCollabManagerBean")
	private IDBConnectionRetrieveProjCollabManager databaseConnect;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	@Autowired
	private ICheckProjectSecurity projectSecurity;
	
	@Autowired
	private IRetrieveProjCollabManager projectManager;
	
	/**
	 * This method returns the list of projects associated with
	 * the logged in user.
	 * @param sUserName
	 * @return List - list of projects.
	 * @throws QuadrigaStorageException
	 * @author Karthik Jayaraman
	 */
	@Override
	@Transactional
	public List<IProject> getProjectList(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		
		projectList = retrieveProjectManagerDAO.getProjectList(sUserName);
		
		return projectList;
	}
	
	@Override
	@Transactional
	public List<IProject> getCollaboratorProjectList(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		
		projectList = retrieveProjectManagerDAO.getCollaboratorProjectList(sUserName);
		
		return projectList;
	}
	
	@Override
	@Transactional
	public List<IProject> getProjectListAsWorkspaceOwner(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		projectList = retrieveProjectManagerDAO.getProjectListAsWorkspaceOwner(sUserName);
		return projectList;
	}
	
	@Override
	@Transactional
	public List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		
		//projectList = dbConnect.getProjectList(sUserName,DBConstants.PROJECT_LIST_AS_WORKSPACE_COLLABORATOR);
		projectList = retrieveProjectManagerDAO.getProjectListAsWorkspaceCollaborator(sUserName);
		return projectList;
	}
	
	@Override
	public List<IProject> getProjectListByCollaboratorRole(String sUserName,String role) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		
		projectList = dbConnect.getCollaboratorProjectList(sUserName,role);
		
		return projectList;
	}
	
	/*@Override
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException
	{
		IProject project;
		List<ICollaborator> collaboratorList;
		
		//retrieve project details
		project = dbConnect.getProjectDetails(projectId);
		
		//retrieve the collaborators associated with project
		collaboratorList = projectManager.getProjectCollaborators(projectId); 
				
		//assigning the collaborators to the project
		project.setCollaborators(collaboratorList);
		
		return project;
	}*/
	
	/**
	 * This method returns the project details for the supplied project.
	 * @param projectId
	 * @return Iproject
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	@Override
	@Transactional
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException
	{
		return retrieveProjectManagerDAO.getProjectDetails(projectId);
	}

	@Override
	public List<ICollaborator> getCollaboratingUsers(String projectId)throws QuadrigaStorageException {
		
		List<ICollaborator> collaboratingUsersList = databaseConnect.getProjectCollaboratorsRequest(projectId);
		
		return collaboratingUsersList;
	}


}