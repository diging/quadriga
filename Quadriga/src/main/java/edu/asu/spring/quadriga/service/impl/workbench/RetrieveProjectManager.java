package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.accesschecks.IProjectSecurityChecker;
import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;

@Service
public class RetrieveProjectManager implements IRetrieveProjectManager 
{
	@Autowired
	private IRetrieveProjectDAO dbConnect;

	@Autowired
	private IProjectShallowMapper projectShallowMapper;	

	@Autowired
	private IProjectDeepMapper projectDeepMapper;	

	@Autowired
	private IProjectSecurityChecker projectSecurity;

	@Autowired
	private IProjectCollaboratorManager projectManager;

	/**
	 * This method returns the list of projects associated with
	 * the logged in user. It uses the Project shallow mapper to give a {@link List} of {@link IProject} of domain type {@link ProjectProxy}.
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
		projectList =  projectShallowMapper.getProjectList(sUserName);
		return projectList;
	}
	
	/**
     * This method retrieves the list of projects associated with the accessibility of the project. 
     * It uses the Project shallow mapper to give a {@link List} of {@link IProject} of domain type {@link ProjectProxy}.
     * @param accessibility - accessibility of the project.
     * @return List<IProject> - list of projects associated with the accessibility of the project.
     * @throws QuadrigaStorageException
     */
	@Override
	@Transactional
	public List<IProject> getProjectListByAccessibility(String accessibility) throws QuadrigaStorageException {
	    List<ProjectDTO> projectDTOList = dbConnect.getAllProjectsDTOByAccessibility(accessibility);
	    List<IProject> projectList = new ArrayList<IProject>();
        if(projectDTOList!=null) {
            for(ProjectDTO projectDTO : projectDTOList){
               projectList.add(projectShallowMapper.getProjectDetails(projectDTO));
            }
        }       
        return projectList;
	}
	
	/**
	 * This method retrieves the list of projects associated with the logged in user as a collaborator. 
	 * It uses the Project shallow mapper to give a {@link List} of {@link IProject} of domain type {@link ProjectProxy}.
	 * @param sUserName - logged in user name.
	 * @return List<IProject> - list of projects associated with the logged in user as collaborator.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<IProject> getCollaboratorProjectList(String sUserName) throws QuadrigaStorageException
	{
		List<IProject> projectList;
		projectList = projectShallowMapper.getCollaboratorProjectListOfUser(sUserName);
		return projectList;
	}

	/**
	 * This method retrieves the list of projects associated with the logged in user as a owner
	 * of associated workspaces.
	 * It uses the Project shallow mapper to give a {@link List} of {@link IProject} of domain type {@link ProjectProxy}.
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
		projectList = projectShallowMapper.getProjectListAsWorkspaceOwner(sUserName);
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
		projectList = projectShallowMapper.getProjectListAsWorkspaceCollaborator(sUserName);
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
		projectList = projectShallowMapper.getProjectListByCollaboratorRole(sUserName,role);
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
		return projectDeepMapper.getProjectDetails(projectId);
	}

	/**
	 * This method retrieves the collaborators associated with the project
	 * @param projectId - project id.
	 * @return List<IProjectCollaborator> list of project collaborators associated with the project.
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public List<IProjectCollaborator> getCollaboratingUsers(String projectId)throws QuadrigaStorageException {
		List<IProjectCollaborator> projectCollaboratingUsersList = projectDeepMapper.getCollaboratorsOfProject(projectId);
		return projectCollaboratingUsersList;
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
		return projectDeepMapper.getProjectDetailsByUnixName(unixName);

	}

	@Override
	@Transactional
	public boolean getPublicProjectWebsiteAccessibility(String unixName) throws QuadrigaStorageException{
		IProject project = projectDeepMapper.getProjectDetailsByUnixName(unixName);
		String access = project.getProjectAccess().toString();
		if(access.equals("PUBLIC")){
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public boolean getPrivateProjectWebsiteAccessibility(String unixName, String user) throws QuadrigaStorageException{
		IProject project = projectDeepMapper.getProjectDetailsByUnixName(unixName);

		List<IProjectCollaborator> projectCollaborators = project.getProjectCollaborators();
		List<String> collaboratorNames = new ArrayList<String>();
		if(projectCollaborators != null){
			Iterator<IProjectCollaborator> projectCollabIterator = projectCollaborators.iterator();
			while(projectCollabIterator.hasNext()){
				String collab = projectCollabIterator.next().getCollaborator().getUserObj().getName();
				collaboratorNames.add(collab);
			}
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
