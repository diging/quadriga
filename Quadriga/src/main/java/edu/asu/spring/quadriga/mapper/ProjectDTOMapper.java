package edu.asu.spring.quadriga.mapper;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class ProjectDTOMapper{

	@Autowired
    private IUserManager userManager;
	
	public IProject getProject(ProjectDTO projectDTO)  throws QuadrigaStorageException
	{
		IProject project = new Project();
		project.setName(projectDTO.getProjectname());
		project.setDescription(projectDTO.getDescription());
		project.setUnixName(projectDTO.getUnixname());
		project.setInternalid(projectDTO.getProjectid());
		project.setOwner(userManager.getUserDetails(projectDTO.getProjectowner().getUsername()));
		project.setProjectAccess(EProjectAccessibility.valueOf(projectDTO.getAccessibility()));
		return project;
	}
	
	public ProjectDTO getProjectDTO(IProject project)  throws QuadrigaStorageException
	{
		ProjectDTO projectDTO = new ProjectDTO();
		projectDTO.setProjectname(project.getName());
		projectDTO.setDescription(project.getDescription());
		projectDTO.setUnixname(project.getUnixName());
		projectDTO.setProjectid(project.getInternalid());
		projectDTO.setProjectowner(new QuadrigaUserDTO(project.getOwner().getUserName()));
		projectDTO.setCreatedby(project.getOwner().getUserName());
		projectDTO.setCreateddate(new Date());
		projectDTO.setUpdatedby(project.getOwner().getUserName());
		projectDTO.setUpdateddate(new Date());
		if(project.getProjectAccess() != null)
		{
			projectDTO.setAccessibility(project.getProjectAccess().name());
		}
		return projectDTO;
	}
}
