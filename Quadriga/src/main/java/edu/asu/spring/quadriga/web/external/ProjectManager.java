package edu.asu.spring.quadriga.web.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;

@Service
public class ProjectManager {
	
	@Autowired
	private ProjectDBConnectionManager connection;
	
	@Autowired 
	private IProjectFactory projectfactory; 
	
	public IProject getProjectDetails(){
		//IProject project = projectfactory.createProjectObject();
		
		IProject project = connection.getDetails();
		
		return project;
	}
}
