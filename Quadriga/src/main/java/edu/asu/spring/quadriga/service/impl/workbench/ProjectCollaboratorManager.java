package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.impl.workbench.ProjectDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectCollaboratorDAO;
import edu.asu.spring.quadriga.dao.workbench.IProjectDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;

//show collaborators for a project
//show non collaborators for a project
@Service
public class ProjectCollaboratorManager extends CollaboratorManager<ProjectCollaboratorDTO, ProjectCollaboratorDTOPK, ProjectDTO, ProjectDAO> implements IProjectCollaboratorManager 
{

	@Autowired
	private IQuadrigaRoleManager roleManager;

	@Autowired
	private IProjectDeepMapper projectDeepMapper;

	@Autowired
    private IProjectCollaboratorDAO projectCollabDAO;
   
	@Autowired
	private IProjectDAO projectDao;

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

		nonCollaborators = projectCollabDAO.getProjectNonCollaborators(projectid);

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
	
	/**
     * This method adds a collaborator for the project supplied.
     * @param collaborator
     * @param projectid
     * @return String - error message blank on success and error on failure.
     * @throws QuadrigaStorageException
     * @author rohit pendbhaje
     */
    @Override
    @Transactional
    public void addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName) throws QuadrigaStorageException
    {
        
        projectCollabDAO.addCollaboratorRequest(collaborator, projectid, userName);
        
    }

    @Override
    public ProjectCollaboratorDTO createNewDTO() {
        return new ProjectCollaboratorDTO();
    }

    @Override
    public ProjectCollaboratorDTOPK createNewDTOPK(String id, String collabUser, String role) {
        return new ProjectCollaboratorDTOPK(id, collabUser, role);
    }

    @Override
    public IBaseDAO<ProjectDTO> getDao() {
        return projectDao;
    }

    @Override
    public IBaseDAO<ProjectCollaboratorDTO> getCollaboratorDao() {
        return projectCollabDAO;
    }
}
