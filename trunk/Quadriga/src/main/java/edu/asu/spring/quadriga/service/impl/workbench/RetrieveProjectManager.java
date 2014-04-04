package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private IDBConnectionRetrieveProjectManager dbConnect;
	
	@Autowired
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
		projectList = dbConnect.getProjectList(sUserName);
		return projectList;
	}
	
	/**
	 * This method retrieves the list of projects associated with the logged in user as a collaborator.
	 * @param sUserName - logged in user name.
	 * @return List<IProject> - list of projects associated with the logged in user as collaborator.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<IProject> getCollaboratorProjectList(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		projectList = dbConnect.getCollaboratorProjectList(sUserName);
		return projectList;
	}
	
	/**
	 * This method retrieves the list of projects associated with the logged in user as a owner
	 * of associated workspaces.
	 * @param sUserName - logged in user name.
	 * @return List<IProject> list of projects associated with the logged in user as one of its workspaces.
	 * @throws QuadrigaStorageException
	 * 
	 */
	@Override
	@Transactional
	public List<IProject> getProjectListAsWorkspaceOwner(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		projectList = dbConnect.getProjectListAsWorkspaceOwner(sUserName);
		return projectList;
	}
	
	/**
	 * This method retrieves the list of projects associated with the logged in user as a collaborator
	 * of associated workspaces.
	 * @param sUserName - logged in user name.
	 * @return List<IProject> list of projects associated with the logged in user as one of its workspaces.
	 * @throws QuadrigaStorageException
	 * 
	 */
	@Override
	@Transactional
	public List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		projectList = dbConnect.getProjectListAsWorkspaceCollaborator(sUserName);
		return projectList;
	}
	
	/**
	 * This method retrieves the list of projects associated with the logged in user as a collaborator
	 * of associated workspaces.
	 * @param sUserName - logged in user name.
	 * @return List<IProject> list of projects associated with the logged in user as one of its workspaces.
	 * @throws QuadrigaStorageException
	 * 
	 */
	@Override
	@Transactional
	public List<IProject> getProjectListByCollaboratorRole(String sUserName,String role) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		projectList = dbConnect.getProjectListByCollaboratorRole(sUserName,role);
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
	@Transactional
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException
	{
		return dbConnect.getProjectDetails(projectId);
	}

	/**
	 * This method retrieves the collaborators associated with the project
	 * @param projectId - project id.
	 * @return List<ICollaborator> list of collaborators associated with the project.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<ICollaborator> getCollaboratingUsers(String projectId)throws QuadrigaStorageException {
		List<ICollaborator> collaboratingUsersList = databaseConnect.getProjectCollaborators(projectId);
		return collaboratingUsersList;
	}

	/**
	 * Retrieves the project details by its unix name
	 * @param unixName - unix name associated with the project.
	 * @return IProject - project object containing the details.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public IProject getProjectDetailsByUnixName(String unixName) throws QuadrigaStorageException {
		return dbConnect.getProjectDetailsByUnixName(unixName);
		
	}
	
	@Override
	@Transactional
	public boolean getPublicProjectWebsiteAccessibility(String unixName) throws QuadrigaStorageException{
		IProject project = dbConnect.getProjectDetailsByUnixName(unixName);
		String access = project.getProjectAccess().toString();
		if(access.equals("PUBLIC")){
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean getPrivateProjectWebsiteAccessibility(String unixName, String user) throws QuadrigaStorageException{
		IProject project = dbConnect.getProjectDetailsByUnixName(unixName);
		
		List<ICollaborator> collaborators = project.getCollaborators();
		List<String> collaboratorNames = new ArrayList<String>();
		Iterator<ICollaborator> collabIterator = collaborators.iterator();
		while(collabIterator.hasNext()){
			String collab = collabIterator.next().getUserObj().getName();
			collaboratorNames.add(collab);
		}
		String access = project.getProjectAccess().toString();
		
		if(access.equals("PRIVATE")){
			if(project.getOwner().getName().equals(user) || collaboratorNames.contains(user)){
				return true;
			}
		}
		return false;
	}
}
