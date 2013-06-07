package edu.asu.spring.quadriga.service.develop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.service.IProjectManager;

/**
 *   @Description : This class acts as a dummy project manager which adds list of projects
 *   				and their descriptions on the workbench.
 *   
 *   @implements  : IProjectManager Interface
 *   
 *   @Called By   : WorkbenchController.java
 *   
 *   @author      : Rohit Pendbhaje
 * 
 * 
 */
//@Service
public class MockupProjectManager implements IProjectManager{

/**   
 * @Description : creates list of objects consisting project names and their descriptions
 * 
 * @param 		: userID
 * 
 * @return 		: list of projects
 * 
 * @throws		: SQLException 
 * 
 * @author		: Rohit Pendbhaje
 * 
 */
	
	@Override
	public List<IProject> getProjectsOfUser(String sUserId) throws SQLException {
		
			
		IProject project = new Project();
		IProject project1 = new Project();
		
		project.setId("id1");
		project1.setId("id2");
		
		project.setName("quadriga 1");
		project1.setName("quadriga 2");
		
		project.setDescription("Lifecycle of Amphibians");
		project1.setDescription("Lifecycle of Mammals ");
		
		List<IProject> projectlist = new ArrayList<IProject>();
		
		projectlist.add(project);
		projectlist.add(project1);
				
		return projectlist;
	}

	@Override
	public String updateProjectDetails(Project existingProject) {
		
		return null;
	}

	@Override
	public int deleteProject(String projectId) {
		
		return 0;
	}

	@Override
	public int addNewProject(IProject newProject) {
		
		return 0;
	}

	@Override
	public IProject getProject(String id) {
		IProject project = new Project();
		
		project.setId(id);
		
		project.setName("quadriga" + id);
		
		return project;
	}

	
}
