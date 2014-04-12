package edu.asu.spring.quadriga.service.impl.workbench.mapper.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.workbench.mapper.IProjectDeepMapper;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

public class ProjectDeepMapper implements IProjectDeepMapper {

	@Autowired
	private IDBConnectionRetrieveProjectManager dbConnect;

	@Autowired
	private IRetrieveProjectManager projectManager;
	
	@Autowired
	private IProjectFactory projectFactory;

	@Autowired
	private IUserManager userManager;

	/**
	 * {@inheritDoc}
	*/
	@Override
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
}
