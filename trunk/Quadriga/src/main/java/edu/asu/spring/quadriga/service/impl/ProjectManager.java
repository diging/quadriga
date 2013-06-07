package edu.asu.spring.quadriga.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.service.ICollaboratorRoleMapper;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.web.WorkbenchController;

/**
 * @description	 	this class manages the projects and acts as a bridge between 
 * 					controller and database 
 * 
 * @author 			rohit pendbhaje
 *
 */
@Service
public class ProjectManager implements IProjectManager {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkbenchController.class);

	@Autowired
	@Qualifier("DBConnectionProjectManagerBean")
	private IDBConnectionProjectManager dbConnect;
	
	@Autowired
	private ICollaboratorRoleMapper roleMapper ;

	/**
	 * @description: this method takes up userid as an argument of the logged in user and returns 
	 * 				 list of projects for the user
	 *
	 * @throws		 SQLException
	 * 
	 * @author 		 rohit pendbhaje
	 * 
	 */
	
	@Override
	public List<IProject> getProjectsOfUser(String userid) throws SQLException {
		
		List<IProject> projectList = new ArrayList<IProject>();  
		
		projectList = dbConnect.getProjectOfUser(userid);
				
		return projectList;
	} 
	
	

	@Override
	public String updateProjectDetails(Project existingProject) {
		throw new NotImplementedException("updateProjectDetails() is not yet implemented");
		
	}

	@Override
	public int deleteProject(String projectId) {
		throw new NotImplementedException("deleteProject() is not yet implemented");
		
	}

	/**
	 * This is used to add the project details
	 * @param Projecy object
	 * @return 1 on success and 0 on failure
	 * @author Kiran Kumar Batna
	 * 
	 */
	@Override
	public int addNewProject(IProject newProject) 
	{
		int success;
		
		success = dbConnect.addProjectRequest(newProject);
		
		if(success == 1)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * @description: this method takes up userid as an argument of the logged in user and returns 
	 * 				 project object containing all project details
	 *
	 * @throws		 SQLException
	 * 
	 * @author 		 rohit pendbhaje
	 * 
	 */
	@Override
	public IProject getProject(String projectid) {
	    
		IProject project = null;
		List<ICollaboratorRole> collaboratorDBRolesList;
		ICollaborator collaborator;
		ICollaboratorRole collaboratorRole;
		List<ICollaboratorRole> collaboratorRolesList = new ArrayList<ICollaboratorRole>();
		
		try {
			project = dbConnect.getProjectDetails(projectid);
								
			for(int j=0;j<project.getCollaborators().size();j++)
			{
				collaborator = project.getCollaborators().get(j);
				collaboratorDBRolesList = collaborator.getCollaboratorRoles();
				for(int i=0; i<collaboratorDBRolesList.size();i++)
				{
					collaboratorRole = roleMapper.getCollaboratorRoleId(collaboratorDBRolesList.get(i).getRoleid());
					collaboratorRolesList.add(collaboratorRole);
				}
			}
			//collaboratorDBRolesList =  project.getCollaboratorRoles();
	
			/*for(int i=0; i<collaboratorDBRolesList.size();i++)
			{
				collaboratorRole = roleMapper.getCollaboratorRoleId(collaboratorDBRolesList.get(i).getRoleid());
				collaboratorRolesList.add(collaboratorRole);
			} */
		
			project.setCollaboratorRoles(collaboratorRolesList);
		}
		
		catch (SQLException e) {
			
			logger.error("sqlException thrown",e);
			}
			
		return project;
	}

}
