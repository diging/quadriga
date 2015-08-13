package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.workbench.IProjectCollaboratorManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;

//show collaborators for a project
//show non collaborators for a project
@Service
public class ProjectCollaboratorManager implements IProjectCollaboratorManager 
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

    /**
     * This method deletes the collaborator associated with the project
     * @param userName - collaborator user name
     * @param projectid - project id
     * @throws QuarigaStorageException
     */
    @Override
    @Transactional
    public void deleteCollaboratorRequest(String userName, String projectid) throws QuadrigaStorageException {
        projectCollabDAO.deleteColloratorRequest(userName, projectid);
    }
    
    /**
     * This method updates the roles associated with the collaborator.
     * @param projectid - project id
     * @param collabUser - collaborator user name
     * @param collaboratorRole - roles associated with the collaborator.
     * @param userName - logged in user
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void updateCollaboratorRequest(String projectid,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
    {
//        ProjectDTO project = projectDao.getProjectDTO(projectid);
//        if(project == null) {
//            return;
//        }
//        
//        List<ProjectCollaboratorDTO> collaboratorList = project.getProjectCollaboratorDTOList();
//        List<String> newCollaboratorRoles = Arrays.asList(collaboratorRole.split(","));
//        List<String> existingRoles = new ArrayList<String>();
//        
//        //remove the user roles which are not associated with the input selection
//        Iterator<ProjectCollaboratorDTO> iterator = collaboratorList.iterator();
//        while(iterator.hasNext()) {
//            ProjectCollaboratorDTO projectCollaborator = iterator.next();
//            ProjectCollaboratorDTOPK collaboratorKey = projectCollaborator.getCollaboratorDTOPK();
//            String collaborator = projectCollaborator.getQuadrigaUserDTO().getUsername();
//            String collabRole = collaboratorKey.getCollaboratorrole();
//            if(collaborator.equals(collabUser)) {
//                if(!newCollaboratorRoles.contains(collabRole)) {
//                    iterator.remove();
//                } else {
//                    existingRoles.add(collabRole);
//                }
//            }
//        }
//        
//        //add the new roles to the collaborator
//        QuadrigaUserDTO user = projectDao.getUserDTO(collabUser);
//        
//        for(String role : newCollaboratorRoles) {
//            if(!existingRoles.contains(role)) {
//                Date date = new Date();
//                ProjectCollaboratorDTO projectCollaborator = new ProjectCollaboratorDTO();
//                ProjectCollaboratorDTOPK collaboratorKey = new ProjectCollaboratorDTOPK(projectid,collabUser,role);
//                projectCollaborator.setProjectDTO(project);
//                projectCollaborator.setProjectCollaboratorDTOPK(collaboratorKey);
//                projectCollaborator.setQuadrigaUserDTO(user);
//                projectCollaborator.setCreatedby(username);
//                projectCollaborator.setCreateddate(date);
//                projectCollaborator.setUpdatedby(username);
//                projectCollaborator.setUpdateddate(date);
//                collaboratorList.add(projectCollaborator);
//            }
//        }
//        
//        project.setProjectCollaboratorDTOList(collaboratorList);
//        try {
//            sessionFactory.getCurrentSession().update(project);
//        }
//        catch(Exception ex)
//        {
//            logger.error("Error while updating project collaborators",ex);
//            throw new QuadrigaStorageException();
//        }
        
        //
        projectCollabDAO.updateCollaboratorRequest(projectid, collabUser, collaboratorRole, username);
    }
}
