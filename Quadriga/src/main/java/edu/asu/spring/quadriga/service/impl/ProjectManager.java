package edu.asu.spring.quadriga.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.service.IProjectManager;



@Service
public class ProjectManager implements IProjectManager {
	
	@Autowired
	@Qualifier("DBConnectionManagerBean")
	private IDBConnectionManager dbConnect;

	@Override
	public List<IProject> getProjectsOfUser(String sUserId) throws SQLException {
		
		List<IProject> projectList = new ArrayList<IProject>();  
		
		System.out.println("--------------------in project manager---------------");
		    		    
		projectList = dbConnect.getProjectOfUser(sUserId);
				
		return projectList;
	} 

	@Override
	public String updateProjectDetails(Project existingProject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteProject(String projectId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addNewProject(Project newProject) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IProject getProject(String id) {
		
		IProject project = new Project();
		
		project.setId(id);
		
		return null;
	}
	
	

	

}
