package edu.asu.spring.quadriga.service.impl;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.service.IProjectManager;

/**
 * @description	 	this class manages the projects and acts as a bridge between 
 * 					controller and database 
 * 
 * @author 			rohit pendbhaje
 *
 */
@Service
public class ProjectManager implements IProjectManager {
	
	@Autowired
	@Qualifier("DBConnectionManagerBean")
	private IDBConnectionManager dbConnect;

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

	@Override
	public int addNewProject(Project newProject) {
		throw new NotImplementedException("addNewProject() is not yet implemented");
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
	    
		IProject project = dbConnect.getProjectDetails(projectid);
			
		return project;
	}
	
}
