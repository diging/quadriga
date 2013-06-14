package edu.asu.spring.quadriga.service;

import java.sql.SQLException;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.implementation.Project;

/**
 * Interface class that places restraints on the ProductManager class to implement
 * the required behaviors.
 * 
 * @author : Kiran Kumar Batna
 * @author : Rohit Pendbhaje
 */
public interface IProjectManager {
	
	public abstract List<IProject> getProjectsOfUser(String sUserId) throws SQLException;
	
	public abstract String updateProjectDetails(Project existingProject);
	
	/**
	 * @description : Interface to delete the project rows from the 
	 *                database.
	 * @param       : projectIdList - String of comma(,) separated 
	 *                project Ids. 
	 * @return      : errmsg - blank on success and null on failure
	 * @author      : Kiran Kumar Batna
	 */
	public abstract String deleteProject(String projectIdList);
	
	/**
	 * Calls DBconnection manager to add the given project
	 * @param newProject -- Project class object
	 * @return Error message on any error else a blank string
	 * @author Kiran Kumar Batna
	 */
	public abstract String addNewProject(IProject newProject);
	
	public abstract IProject getProject(int id);

	public abstract int addCollaborators(ICollaborator collaborator);
	
	public abstract IProject showNonExistingCollaborator(int projectid);
	
	public abstract IProject showExistingCollaborator(int projectid);


}
