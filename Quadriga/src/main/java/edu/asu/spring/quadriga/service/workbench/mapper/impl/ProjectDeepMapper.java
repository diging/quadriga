package edu.asu.spring.quadriga.service.workbench.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectCollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.dto.ProjectCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectWorkspaceShallowMapper;
@Service
public class ProjectDeepMapper implements IProjectDeepMapper {

	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;

	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private IProjectFactory projectFactory;

	@Autowired
	private IUserDeepMapper userDeepMapper;
	
	@Autowired
	private IProjectCollaboratorFactory projectCollaboratorFactory;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	@Autowired
	private IProjectConceptCollectionShallowMapper projectConceptCollectionShallowMapper;
	
	@Autowired
	private IProjectDictionaryShallowMapper projectDictionaryShallowMapper;
	
	@Autowired
	private IProjectWorkspaceShallowMapper projectWorkspaceShallowMapper;
	
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
			project.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
			project.setCreatedBy(projectDTO.getCreatedby());
			project.setCreatedDate(projectDTO.getCreateddate());
			project.setUpdatedBy(projectDTO.getUpdatedby());
			project.setUpdatedDate(projectDTO.getUpdateddate());
			// Set List of IProjectCollaborators to the project
			project.setProjectCollaborators(getProjectCollaboratorList(projectDTO, project));
			// Set Project Concept Collections 
			project.setProjectConceptCollections(projectConceptCollectionShallowMapper.getProjectConceptCollectionList(project, projectDTO));
			// Set Project Dictionaries
			project.setProjectDictionaries(projectDictionaryShallowMapper.getProjectDictionaryList(project, projectDTO));
			// Set Project Workspaces
			project.setProjectWorkspaces(projectWorkspaceShallowMapper.getProjectWorkspaceList(project, projectDTO)) ;
			
			
		}

		return project;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public IProject getProjectDetails(String projectId,String userId) throws QuadrigaStorageException{
		ProjectDTO projectDTO = dbConnect.getProjectDTO(projectId,userId);
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
			project.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
			project.setCreatedBy(projectDTO.getCreatedby());
			project.setCreatedDate(projectDTO.getCreateddate());
			project.setUpdatedBy(projectDTO.getUpdatedby());
			project.setUpdatedDate(projectDTO.getUpdateddate());
			// Set List of IProjectCollaborators to the project
			project.setProjectCollaborators(getProjectCollaboratorList(projectDTO, project));
			// Set Project Concept Collections 
			project.setProjectConceptCollections(projectConceptCollectionShallowMapper.getProjectConceptCollectionList(project, projectDTO));
			// Set Project Dictionaries
			project.setProjectDictionaries(projectDictionaryShallowMapper.getProjectDictionaryList(project, projectDTO));
			// Set Project Workspaces
			project.setProjectWorkspaces(projectWorkspaceShallowMapper.getProjectWorkspaceList(project, projectDTO)) ;
			
			
		}

		return project;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public IProject getProjectDetailsByUnixName(String unixName) throws QuadrigaStorageException{
		ProjectDTO projectDTO = dbConnect.getProjectDTOByUnixName(unixName);
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
			project.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
			project.setCreatedBy(projectDTO.getCreatedby());
			project.setCreatedDate(projectDTO.getCreateddate());
			project.setUpdatedBy(projectDTO.getUpdatedby());
			project.setUpdatedDate(projectDTO.getUpdateddate());
			// Set List of IProjectCollaborators to the project
			project.setProjectCollaborators(getProjectCollaboratorList(projectDTO, project));
			// Set Project Concept Collections 
			project.setProjectConceptCollections(projectConceptCollectionShallowMapper.getProjectConceptCollectionList(project, projectDTO));
			// Set Project Dictionaries
			project.setProjectDictionaries(projectDictionaryShallowMapper.getProjectDictionaryList(project, projectDTO));
			// Set Project Workspaces
			project.setProjectWorkspaces(projectWorkspaceShallowMapper.getProjectWorkspaceList(project, projectDTO)) ;
			
			
		}

		return project;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IProjectCollaborator> getCollaboratorsOfProject(String projectId) throws QuadrigaStorageException{
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
			project.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
			project.setCreatedBy(projectDTO.getCreatedby());
			project.setCreatedDate(projectDTO.getCreateddate());
			project.setUpdatedBy(projectDTO.getUpdatedby());
			project.setUpdatedDate(projectDTO.getUpdateddate());
			// Set List of IProjectCollaborators to the project
			project.setProjectCollaborators(getProjectCollaboratorList(projectDTO, project));		
			
		}

		return project.getProjectCollaborators();
	}
	

	/**
	 * This class would help in getting {@link List} of {@link IProjectCollaborator} object mapping for a particular {@link IProject} and {@link ProjectDTO} 
	 * @param projectDTO											{@link ProjectDTO} object used for mapping collaborators 
	 * @param project												{@link IProject} object of domain class type {@link Project}
	 * @return														Returns {@link List} of {@link IProjectCollaborator} object
	 * @throws QuadrigaStorageException								Throws a storage issue when this method is having issues to access database.
	 */
	public List<IProjectCollaborator> getProjectCollaboratorList(ProjectDTO projectDTO,IProject project) throws QuadrigaStorageException
	{
		List<IProjectCollaborator> projectCollaboratorList = null;
		if(projectDTO.getProjectCollaboratorDTOList() != null && projectDTO.getProjectCollaboratorDTOList().size() > 0)
		{
			HashMap<String,IProjectCollaborator> userProjectCollaboratorMap = mapUserProjectCollaborator(projectDTO,project);
			for(String userID:userProjectCollaboratorMap.keySet())
			{
				if(projectCollaboratorList == null){
					projectCollaboratorList = new ArrayList<IProjectCollaborator>();
				}
				projectCollaboratorList.add(userProjectCollaboratorMap.get(userID));
			}
		}
		return projectCollaboratorList;
	}
	
	public HashMap<String,IProjectCollaborator> mapUserProjectCollaborator(ProjectDTO projectDTO,IProject project) throws QuadrigaStorageException
	{		
		
		HashMap<String, IProjectCollaborator> userProjectCollaboratorMap = new HashMap<String, IProjectCollaborator>();
		
		for(ProjectCollaboratorDTO projectCollaboratorDTO : projectDTO.getProjectCollaboratorDTOList())
		{
			String userName = projectCollaboratorDTO.getQuadrigaUserDTO().getUsername();
			
			if(userProjectCollaboratorMap.containsKey(userName))
			{
				String roleName = projectCollaboratorDTO.getProjectCollaboratorDTOPK().getCollaboratorrole();
				
				ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
				collaboratorRole.setRoleDBid(roleName);
				collaboratorRole.setDisplayName(collaboratorRoleManager.getProjectCollaboratorRoleByDBId(roleName));
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
				
				IProjectCollaborator projectCollaborator =userProjectCollaboratorMap.get(userName);
				
				ICollaborator collaborator = projectCollaborator.getCollaborator();
				collaborator.getCollaboratorRoles().add(collaboratorRole);
				
				// Checking if there is a update latest then previous update date 
				if(projectCollaboratorDTO.getUpdateddate().compareTo(projectCollaborator.getUpdatedDate()) > 0 ){
					projectCollaborator.setUpdatedBy(projectCollaboratorDTO.getUpdatedby());
					projectCollaborator.setUpdateDate(projectCollaboratorDTO.getUpdateddate());
				}
				
			}
			else
			{
				String roleName = projectCollaboratorDTO.getProjectCollaboratorDTOPK().getCollaboratorrole();
				// Prepare collaborator roles
				ICollaboratorRole collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
				collaboratorRole.setRoleDBid(roleName);
				collaboratorRole.setDisplayName(collaboratorRoleManager.getProjectCollaboratorRoleByDBId(roleName));
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
				// Create a Collaborator Role list
				List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
				// Add collaborator role to the list
				collaboratorRoleList.add(collaboratorRole);
				// Create a Collaborator
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				// Set Collaborator Role List to the Collaborator
				collaborator.setCollaboratorRoles(collaboratorRoleList);
				collaborator.setUserObj(userDeepMapper.getUser(userName));
				
				
				// Create ProjectCollaborator object
				IProjectCollaborator projectCollaborator = projectCollaboratorFactory.createProjectCollaboratorObject();
				projectCollaborator.setCollaborator(collaborator);
				projectCollaborator.setCreatedBy(projectCollaboratorDTO.getCreatedby());
				projectCollaborator.setCreatedDate(projectCollaboratorDTO.getCreateddate());
				projectCollaborator.setUpdatedBy(projectCollaboratorDTO.getUpdatedby());
				projectCollaborator.setUpdateDate(projectCollaboratorDTO.getUpdateddate());
				projectCollaborator.setProject(project);
				
				userProjectCollaboratorMap.put(userName, projectCollaborator);

			}
		}
		return userProjectCollaboratorMap;
	}
	

}
