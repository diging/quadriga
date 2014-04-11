package edu.asu.spring.quadriga.service.impl.workbench.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.workbench.mapper.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

@Service
public class ProjectShallowMapper implements IProjectShallowMapper {

	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;
	
	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private IUserManager userManager;
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
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
				projectProxy.setOwner(userManager.getUserDetails(projectDTO.getProjectowner().getUsername()));
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
