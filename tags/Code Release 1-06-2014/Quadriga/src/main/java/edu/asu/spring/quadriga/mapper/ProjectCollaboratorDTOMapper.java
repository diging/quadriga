package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
//@Repository
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
	
	public void getProjectCollaboratorDAO(List<ProjectCollaboratorDTO> collaboratorList,ICollaborator collaborator,String projectId,String loggedInUser) throws QuadrigaStorageException
	{
		try
		{
			String collabUser = collaborator.getUserObj().getUserName();
			List<ICollaboratorRole> collaboratorRoles = collaborator.getCollaboratorRoles();
			QuadrigaUserDTO userDTO = getUserDTO(collabUser);
			
			ProjectDTO projectDTO = (ProjectDTO) sessionFactory.getCurrentSession().get(ProjectDTO.class, projectId);
			for(ICollaboratorRole role : collaboratorRoles)
			{
				ProjectCollaboratorDTO collaboratorDTO = new ProjectCollaboratorDTO();
				ProjectCollaboratorDTOPK collaboratorKey = new ProjectCollaboratorDTOPK(projectId,collabUser,role.getRoleDBid());
				collaboratorDTO.setProjectDTO(projectDTO);
				collaboratorDTO.setQuadrigaUserDTO(userDTO);
				collaboratorDTO.setProjectCollaboratorDTOPK(collaboratorKey);
				collaboratorDTO.setCreatedby(loggedInUser);
				collaboratorDTO.setCreateddate(new Date());
				collaboratorDTO.setUpdatedby(loggedInUser);
				collaboratorDTO.setUpdateddate(new Date());
				collaboratorList.add(collaboratorDTO);
			}
		}
		catch(QuadrigaStorageException ex)
		{
			throw new QuadrigaStorageException();
		}
	
		
	}
}
