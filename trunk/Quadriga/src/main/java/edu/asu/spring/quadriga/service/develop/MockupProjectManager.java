package edu.asu.spring.quadriga.service.develop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.service.IProjectManager;

@Service
public class MockupProjectManager implements IProjectManager{

	@Override
	public List<IProject> getProjectsOfUser(String sUserId) throws SQLException {
		
		System.out.println("in mockupprojectmanager");
		
		IProject project = new Project();
		IProject project1 = new Project();
		
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
	public int addNewProject(Project newProject) {
		
		return 0;
	}

	

}
