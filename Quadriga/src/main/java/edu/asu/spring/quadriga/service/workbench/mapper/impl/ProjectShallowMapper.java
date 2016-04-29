package edu.asu.spring.quadriga.service.workbench.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;

@Service
public class ProjectShallowMapper implements IProjectShallowMapper {

	@Autowired
	private IRetrieveProjectDAO dbConnect;
	
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private IUserDeepMapper userDeepMapper;
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IProject> getProjectList(String userName) throws QuadrigaStorageException{
		List<ProjectDTO> projectDTOList = dbConnect.getProjectDTOList(userName);
		List<IProject> projectList = null;
		if(projectDTOList!=null){
			for(ProjectDTO projectDTO : projectDTOList){
				if(projectList == null){
					projectList = new ArrayList<IProject>();
				}
				IProject projectProxy = new ProjectProxy(projectManager);
				projectProxy.setProjectId(projectDTO.getProjectid());
				projectProxy.setProjectName(projectDTO.getProjectname());
				projectProxy.setDescription(projectDTO.getDescription());
				projectProxy.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
				projectProxy.setUnixName(projectDTO.getUnixname());
				projectProxy.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
				projectProxy.setCreatedBy(projectDTO.getCreatedby());
				projectProxy.setCreatedDate(projectDTO.getCreateddate());
				projectProxy.setUpdatedBy(projectDTO.getUpdatedby());
				projectProxy.setUpdatedDate(projectDTO.getUpdateddate());
				projectList.add(projectProxy);
			}
		}
		
		return projectList;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public IProject getProjectDetails(String projectId) throws QuadrigaStorageException{
		ProjectDTO projectDTO = dbConnect.getProjectDTO(projectId);
		IProject projectProxy = null;
		
		if(projectDTO != null){
			projectProxy = new ProjectProxy(projectManager);
			projectProxy.setProjectId(projectDTO.getProjectid());
			projectProxy.setProjectName(projectDTO.getProjectname());
			projectProxy.setDescription(projectDTO.getDescription());
			projectProxy.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
			projectProxy.setUnixName(projectDTO.getUnixname());
			projectProxy.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
			projectProxy.setCreatedBy(projectDTO.getCreatedby());
			projectProxy.setCreatedDate(projectDTO.getCreateddate());
			projectProxy.setUpdatedBy(projectDTO.getUpdatedby());
			projectProxy.setUpdatedDate(projectDTO.getUpdateddate());
		}
		
		return projectProxy;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public IProject getProjectDetails(ProjectDTO projectDTO) throws QuadrigaStorageException{
		IProject projectProxy = null;
		
		if(projectDTO != null){
			projectProxy = new ProjectProxy(projectManager);
			projectProxy.setProjectId(projectDTO.getProjectid());
			projectProxy.setProjectName(projectDTO.getProjectname());
			projectProxy.setDescription(projectDTO.getDescription());
			projectProxy.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
			projectProxy.setUnixName(projectDTO.getUnixname());
			projectProxy.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
			projectProxy.setCreatedBy(projectDTO.getCreatedby());
			projectProxy.setCreatedDate(projectDTO.getCreateddate());
			projectProxy.setUpdatedBy(projectDTO.getUpdatedby());
			projectProxy.setUpdatedDate(projectDTO.getUpdateddate());
		}
		
		return projectProxy;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IProject> getCollaboratorProjectListOfUser(String userName) throws QuadrigaStorageException{
		List<ProjectDTO> projectDTOList = dbConnect.getCollaboratorProjectDTOListOfUser(userName);
		List<IProject> projectList = null;
		if(projectDTOList!=null){
			for(ProjectDTO projectDTO : projectDTOList){
				if(projectList == null){
					projectList = new ArrayList<IProject>();
				}
				IProject projectProxy = new ProjectProxy(projectManager);
				projectProxy.setProjectId(projectDTO.getProjectid());
				projectProxy.setProjectName(projectDTO.getProjectname());
				projectProxy.setDescription(projectDTO.getDescription());
				projectProxy.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
				projectProxy.setUnixName(projectDTO.getUnixname());
				projectProxy.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
				projectProxy.setCreatedBy(projectDTO.getCreatedby());
				projectProxy.setCreatedDate(projectDTO.getCreateddate());
				projectProxy.setUpdatedBy(projectDTO.getUpdatedby());
				projectProxy.setUpdatedDate(projectDTO.getUpdateddate());
				projectList.add(projectProxy);
			}
		}
		
		return projectList;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IProject> getProjectListAsWorkspaceOwner(String userName)
			throws QuadrigaStorageException{
		List<ProjectDTO> projectDTOList = dbConnect.getProjectDTOListAsWorkspaceOwner(userName);
		List<IProject> projectList = null;
		if(projectDTOList!=null){
			for(ProjectDTO projectDTO : projectDTOList){
				if(projectList == null){
					projectList = new ArrayList<IProject>();
				}
				IProject projectProxy = new ProjectProxy(projectManager);
				projectProxy.setProjectId(projectDTO.getProjectid());
				projectProxy.setProjectName(projectDTO.getProjectname());
				projectProxy.setDescription(projectDTO.getDescription());
				projectProxy.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
				projectProxy.setUnixName(projectDTO.getUnixname());
				projectProxy.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
				projectProxy.setCreatedBy(projectDTO.getCreatedby());
				projectProxy.setCreatedDate(projectDTO.getCreateddate());
				projectProxy.setUpdatedBy(projectDTO.getUpdatedby());
				projectProxy.setUpdatedDate(projectDTO.getUpdateddate());
				projectList.add(projectProxy);
			}
		}
		
		return projectList;
	}
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IProject> getProjectListAsWorkspaceCollaborator(String userName)
			throws QuadrigaStorageException{
		List<ProjectDTO> projectDTOList = dbConnect.getProjectDTOListAsWorkspaceCollaborator(userName);
		List<IProject> projectList = null;
		if(projectDTOList!=null){
			for(ProjectDTO projectDTO : projectDTOList){
				if(projectList == null){
					projectList = new ArrayList<IProject>();
				}
				IProject projectProxy = new ProjectProxy(projectManager);
				projectProxy.setProjectId(projectDTO.getProjectid());
				projectProxy.setProjectName(projectDTO.getProjectname());
				projectProxy.setDescription(projectDTO.getDescription());
				projectProxy.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
				projectProxy.setUnixName(projectDTO.getUnixname());
				projectProxy.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
				projectProxy.setCreatedBy(projectDTO.getCreatedby());
				projectProxy.setCreatedDate(projectDTO.getCreateddate());
				projectProxy.setUpdatedBy(projectDTO.getUpdatedby());
				projectProxy.setUpdatedDate(projectDTO.getUpdateddate());
				projectList.add(projectProxy);
			}
		}
		
		return projectList;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IProject> getProjectListByCollaboratorRole(String userName,
			String collaboratorRole) throws QuadrigaStorageException{
		List<ProjectDTO> projectDTOList = dbConnect.getProjectDTOListByCollaboratorRole(userName,collaboratorRole);
		List<IProject> projectList = null;
		if(projectDTOList!=null){
			for(ProjectDTO projectDTO : projectDTOList){
				if(projectList == null){
					projectList = new ArrayList<IProject>();
				}
				IProject projectProxy = new ProjectProxy(projectManager);
				projectProxy.setProjectId(projectDTO.getProjectid());
				projectProxy.setProjectName(projectDTO.getProjectname());
				projectProxy.setDescription(projectDTO.getDescription());
				projectProxy.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
				projectProxy.setUnixName(projectDTO.getUnixname());
				projectProxy.setOwner(userDeepMapper.getUser(projectDTO.getProjectowner().getUsername()));
				projectProxy.setCreatedBy(projectDTO.getCreatedby());
				projectProxy.setCreatedDate(projectDTO.getCreateddate());
				projectProxy.setUpdatedBy(projectDTO.getUpdatedby());
				projectProxy.setUpdatedDate(projectDTO.getUpdateddate());
				projectList.add(projectProxy);
			}
		}
		
		return projectList;
	}
}

