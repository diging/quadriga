package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class ProjectCollaboratorDTOMapper extends BaseMapper {

	@Autowired
    private IUserManager userManager;
	@Autowired
	private IQuadrigaRoleFactory roleFactory;
	@Autowired
	private IQuadrigaRoleManager roleManager;
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectCollaboratorDTOMapper.class);
	
	public List<ICollaborator> getProjectCollaboratorList(ProjectDTO projectDTO) throws QuadrigaStorageException
	{
		List<ICollaborator> projectCollaboratorList = new ArrayList<ICollaborator>();
		if(projectDTO.getProjectCollaboratorDTOList() != null && projectDTO.getProjectCollaboratorDTOList().size() > 0)
		{
			HashMap<String,List<String>> collabMap = mapUserRoles(projectDTO);
			for(String userID:collabMap.keySet())
			{
				List<IQuadrigaRole> collaboratorRoleList = new ArrayList<IQuadrigaRole>();
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				collaborator.setUserObj(userManager.getUser(userID));
				for(String roleName: collabMap.get(userID))
				{
				    IQuadrigaRole collaboratorRole = roleFactory.createQuadrigaRoleObject();
					collaboratorRole.setDBid(roleName);
					collaboratorRole.setDisplayName(roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.PROJECT_ROLES, roleName).getDisplayName());
					roleManager.fillQuadrigaRole(IQuadrigaRoleManager.PROJECT_ROLES, collaboratorRole);
					collaboratorRoleList.add(collaboratorRole);
				}
				collaborator.setCollaboratorRoles(collaboratorRoleList);
				projectCollaboratorList.add(collaborator);
			}
		}
		return projectCollaboratorList;
	}
	
	public HashMap<String,List<String>> mapUserRoles(ProjectDTO projectDTO)
	{
		HashMap<String,List<String>> collabMap = new HashMap<String, List<String>>();
		List<String> roleList = null;
		
		for(ProjectCollaboratorDTO projectCollaboratorDTO : projectDTO.getProjectCollaboratorDTOList())
		{
			String userName = projectCollaboratorDTO.getQuadrigaUserDTO().getUsername();
			if(collabMap.containsKey(userName))
			{
				collabMap.get(userName).add(projectCollaboratorDTO.getCollaboratorDTOPK().getCollaboratorrole());
			}
			else
			{
				roleList = new ArrayList<String>();
				roleList.add(projectCollaboratorDTO.getCollaboratorDTOPK().getCollaboratorrole());
				collabMap.put(userName,roleList);
			}
		}
		return collabMap;
	}
	
	/**
	 * This method adds a collaborator to the given project
	 * @param project
	 * @param collaborator
	 * @param loggedInUser
	 * @throws QuadrigaStorageException
	 */
	public void addCollaboratorToProjectDTO(ProjectDTO project,ICollaborator collaborator,String loggedInUser) {
		String projectId = project.getProjectid();
		String collabUser = collaborator.getUserObj().getUserName();
		List<IQuadrigaRole> collaboratorRoles = collaborator.getCollaboratorRoles();
		QuadrigaUserDTO userDTO = getUserDTO(collabUser);
		
		List<ProjectCollaboratorDTO> projectCollaborators = project.getProjectCollaboratorDTOList();
		
		for(IQuadrigaRole role : collaboratorRoles)
		{
			ProjectCollaboratorDTO collaboratorDTO = new ProjectCollaboratorDTO();
			ProjectCollaboratorDTOPK collaboratorKey = new ProjectCollaboratorDTOPK(projectId,collabUser,role.getDBid());
			collaboratorDTO.setProjectDTO(project);
			collaboratorDTO.setQuadrigaUserDTO(userDTO);
			collaboratorDTO.setCollaboratorDTOPK(collaboratorKey);
			collaboratorDTO.setCreatedby(loggedInUser);
			collaboratorDTO.setCreateddate(new Date());
			collaboratorDTO.setUpdatedby(loggedInUser);
			collaboratorDTO.setUpdateddate(new Date());
			projectCollaborators.add(collaboratorDTO);
		}
		project.setProjectCollaboratorDTOList(projectCollaborators);
	}
	
	/**
	 * This method returns new collaborator object associated for the
	 * given project
	 * @param project
	 * @param userName
	 * @param collaboratorRole
	 * @return ProjectCollaboratorDTO object
	 * @throws QuadrigaStorageException
	 */
	public ProjectCollaboratorDTO getProjectCollaborator(ProjectDTO project,String userName,String collaboratorRole) throws QuadrigaStorageException
	{
		ProjectCollaboratorDTO collaborator = null;
		try
		{
			Date date = new Date();
			collaborator = new ProjectCollaboratorDTO();
            collaborator.setProjectDTO(project);
            collaborator.setCollaboratorDTOPK(new ProjectCollaboratorDTOPK(project.getProjectid(),userName,collaboratorRole));
            collaborator.setQuadrigaUserDTO(getUserDTO(userName));
            collaborator.setCreatedby(userName);
            collaborator.setCreateddate(date);
            collaborator.setUpdatedby(userName);
            collaborator.setUpdateddate(date);
		}
		catch(HibernateException ex)
		{
			logger.error("Retieving project collaborator :",ex);
			throw new QuadrigaStorageException();
		}
		return collaborator;
	}
}
