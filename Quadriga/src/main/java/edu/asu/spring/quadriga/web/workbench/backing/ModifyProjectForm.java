package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.List;

/**
 * This class holds the list of projects to act as a Model attribute
 * to display in the UI pages
 */
public class ModifyProjectForm 
{
	private List<ModifyProject> projectList;
	
	public List<ModifyProject> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<ModifyProject> projectList) {
		this.projectList = projectList;
	}
	
}
