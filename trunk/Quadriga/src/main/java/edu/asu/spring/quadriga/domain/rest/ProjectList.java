package edu.asu.spring.quadriga.domain.rest;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProject;




public class ProjectList {
	 
	 
	 protected List<IProject> projectList;
	 
	 public List<IProject> getProjectList() {
	     return this.projectList;
	 }
	 public void SetProjectList(List<IProject> projectList) {
	     this.projectList=projectList;
	 }


	
}
