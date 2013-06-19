package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface class that places restraints on the ProductManager class to implement
 * the required behaviors.
 * 
 * @author : Kiran Kumar Batna
 * @author : Rohit Pendbhaje
 */
public interface IProjectManager {
	
	public abstract List<IProject> getProjectsOfUser(String sUserId) throws QuadrigaStorageException;
	
	public abstract String updateProjectDetails(Project existingProject,String userName) throws QuadrigaStorageException;
	
	/**
	 * @description : Interface to delete the project rows from the 
	 *                database.
	 * @param       : projectIdList - String of comma(,) separated 
	 *                project Ids. 
	 * @return      : errmsg - blank on success and null on failure
	 * @author      : Kiran Kumar Batna
	 */
	public abstract String deleteProject(String projectIdList) throws QuadrigaStorageException;
	
	/**
	 * Calls DBconnection manager to add the given project
	 * @param newProject -- Project class object
	 * @return Error message on any error else a blank string
	 * @author Kiran Kumar Batna
	 */
	public abstract String addNewProject(IProject newProject) throws QuadrigaStorageException;
	
	public abstract IProject getProject(int id) throws QuadrigaStorageException;

	public abstract String addCollaborators(ICollaborator collaborator,int projectid) throws QuadrigaStorageException;
	
	public abstract List<IUser> getNotCollaboratingUsers(int projectid) throws QuadrigaStorageException;
	
	public abstract IProject showExistingCollaborator(int projectid) throws QuadrigaStorageException;
	
	public abstract List<ICollaborator> getProjectCollaborator(int id) throws QuadrigaStorageException;


}
