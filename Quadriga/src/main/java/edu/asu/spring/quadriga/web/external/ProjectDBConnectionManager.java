package edu.asu.spring.quadriga.web.external;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;

@Service
public class ProjectDBConnectionManager {
	
	@Autowired 
	private IProjectFactory projectfactory; 
	
	public IProject getDetails(){
		
		IProject project = projectfactory.createProjectObject();
		project.setName("sample");
		project.setDescription("it is a sample project");
		IUser owner = null;
		project.setOwner(owner);
		List<ICollaborator> collaborators = null;
		project.setCollaborators(collaborators);
		
		return project;
		
		
		
	}

}
