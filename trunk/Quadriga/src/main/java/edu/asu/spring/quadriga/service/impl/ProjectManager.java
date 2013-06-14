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

	/**
	 *  @description : This calls dbConnectionProjectManger to 
	 *                 delete the project records form the tables.
	 *  @param       : projectIdList - String of comma(,) separated 
	 *                 project Ids.
	 *  @return      : errmsg - blank on success and null on failure
	 *  @author      : Kiran Kumar Batna
	 */
	@Override
	public String deleteProject(String projectIdList) 
	{
		String errmsg;
		
		errmsg = dbConnect.deleteProjectRequest(projectIdList);
		
		return null;
		
	}

	/**
	 * This is used to add the project details
	 * @param Project object
	 * @return Error message on error else a blank string
	 * @author Kiran Kumar Batna
	 * 
	 */
	@Override
	public String addNewProject(IProject newProject) 
	{
		String errmsg;
		
		errmsg = dbConnect.addProjectRequest(newProject);
		
		return errmsg;
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
	public IProject getProject(int projectid) {
	    
		IProject project = null;
		//List<ICollaboratorRole> collaboratorRolesList = new ArrayList<ICollaboratorRole>();
		
		try {
			project = dbConnect.getProjectDetails(projectid);
			
			for (ICollaborator collaborator : project.getCollaborators()) {
				
				for(ICollaboratorRole collaboratorRole : collaborator.getCollaboratorRoles())
				{
					roleMapper.getCollaboratorRoles(collaboratorRole);	
				}
			}	
		}
		
		catch (SQLException e) {
			
			logger.error("sqlException thrown",e);
			}
			
		return project;
	}


	@Override
	public int addCollaborators(ICollaborator collaborator) {
		
	   int success = dbConnect.addCollaboratorRequest(collaborator);
				
	   return success;
	}

	@Override
	public IProject showNonExistingCollaborator(int projectid) {
				
		IProject project = dbConnect.showCollaboratorsRequest(projectid);
		return project;
	}
	
	@Override
	public IProject showExistingCollaborator(int projectid) {
				
		IProject project = null;
		try {
			project = dbConnect.getProjectDetails(projectid);
			
			for (ICollaborator collaborator : project.getCollaborators()) {
				
				for(ICollaboratorRole collaboratorRole : collaborator.getCollaboratorRoles())
				{
					roleMapper.getCollaboratorRoles(collaboratorRole);	
				}
			}	
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return project;
	}
	
	
	
	

}
