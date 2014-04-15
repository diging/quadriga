package edu.asu.spring.quadriga.service.workbench.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;

public class ProjectDeepMapper implements IProjectDeepMapper {

	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;

	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private IProjectFactory projectFactory;

	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;

	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException{
		ProjectDTO projectDTO = dbConnect.getProjectDTO(projectId);
		IProject project = null;
		if(projectDTO != null){
			if(project == null){
				project = projectFactory.createProjectObject();
			}
			project.setProjectId(projectDTO.getProjectid());
			project.setProjectName(projectDTO.getProjectname());
			project.setDescription(projectDTO.getDescription());
			project.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
			project.setUnixName(projectDTO.getUnixname());
			project.setOwner(userManager.getUserDetails(projectDTO.getProjectowner().getUsername()));
			project.setCreatedBy(projectDTO.getCreatedby());
			project.setCreatedDate(projectDTO.getCreateddate());
			project.setUpdatedBy(projectDTO.getUpdatedby());
			project.setUpdatedDate(projectDTO.getUpdateddate());
		}

		return project;
	}
	
	
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
}
