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

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class ProjectCollaboratorDTOMapper extends DAOConnectionManager {

	@Autowired
    private IUserManager userManager;
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
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
				List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				collaborator.setUserObj(userManager.getUserDetails(userID));
				for(String roleName: collabMap.get(userID))
				{
					ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
					collaboratorRole.setRoleDBid(roleName);
					collaboratorRole.setDisplayName(collaboratorRoleManager.getProjectCollaboratorRoleByDBId(roleName));
					roleMapper.fillProjectCollaboratorRole(collaboratorRole);
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
				collabMap.get(userName).add(projectCollaboratorDTO.getProjectCollaboratorDTOPK().getCollaboratorrole());
			}
			else
			{
				roleList = new ArrayList<String>();
				roleList.add(projectCollaboratorDTO.getProjectCollaboratorDTOPK().getCollaboratorrole());
				collabMap.put(userName,roleList);
			}
		}
		return collabMap;
	}
	
	/**
	 * This method assigns a collaborator to the given project
	 * @param project
	 * @param collaborator
	 * @param loggedInUser
	 * @throws QuadrigaStorageException
	 */
	public void getProjectCollaboratorDAO(ProjectDTO project,ICollaborator collaborator,String loggedInUser) throws QuadrigaStorageException
	{
		try
		{
			String projectId = project.getProjectid();
			List<ProjectCollaboratorDTO> projectCollaborator;
			String collabUser = collaborator.getUserObj().getUserName();
			List<ICollaboratorRole> collaboratorRoles = collaborator.getCollaboratorRoles();
			QuadrigaUserDTO userDTO = getUserDTO(collabUser);
			
			projectCollaborator = project.getProjectCollaboratorDTOList();
			
			for(ICollaboratorRole role : collaboratorRoles)
			{
				ProjectCollaboratorDTO collaboratorDTO = new ProjectCollaboratorDTO();
				ProjectCollaboratorDTOPK collaboratorKey = new ProjectCollaboratorDTOPK(projectId,collabUser,role.getRoleDBid());
				collaboratorDTO.setProjectDTO(project);
				collaboratorDTO.setQuadrigaUserDTO(userDTO);
				collaboratorDTO.setProjectCollaboratorDTOPK(collaboratorKey);
				collaboratorDTO.setCreatedby(loggedInUser);
				collaboratorDTO.setCreateddate(new Date());
				collaboratorDTO.setUpdatedby(loggedInUser);
				collaboratorDTO.setUpdateddate(new Date());
				projectCollaborator.add(collaboratorDTO);
			}
			project.setProjectCollaboratorDTOList(projectCollaborator);
		}
		catch(QuadrigaStorageException ex)
		{
			logger.error("Retrieving project collaborators :",ex);
			throw new QuadrigaStorageException();
		}
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
            collaborator.setProjectCollaboratorDTOPK(new ProjectCollaboratorDTOPK(project.getProjectid(),userName,collaboratorRole));
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
