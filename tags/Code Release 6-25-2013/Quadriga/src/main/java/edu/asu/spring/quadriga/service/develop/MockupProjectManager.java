package edu.asu.spring.quadriga.service.develop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
	public List<IProject> getProjectsOfUser(String sUserId) throws QuadrigaStorageException {
		
			
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
	public String updateProjectDetails(Project existingProject,String userName) {
		
		throw new RuntimeException("Mockup manger update project not implemented");
	}

	@Override
	public String deleteProject(String projectId) {
		
		throw new RuntimeException("Mockup manger delete project not implemented");
	}

	@Override
	public String addNewProject(IProject newProject) {
		
		throw new RuntimeException("Mockup manger add project not implemented");
	}

	@Override
	public IProject getProject(int id) {
		IProject project = new Project();
		
		project.setInternalid(id);
		
		project.setName("quadriga" + id);
		
		return project;
	}


	
	@Override
	public List<IUser> getNotCollaboratingUsers(int projectid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IProject showExistingCollaborator(int projectid) {
		return null;
	}

	@Override
	public List<ICollaborator> getProjectCollaborator(int id) {
		return null;
	}


	@Override
	public String addCollaborators(ICollaborator collaborator, int projectid)
			throws QuadrigaStorageException {
		throw new RuntimeException("addCollaborators is not yet implemented");		
	}


}
