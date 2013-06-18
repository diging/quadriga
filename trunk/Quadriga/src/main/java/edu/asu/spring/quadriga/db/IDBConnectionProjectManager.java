package edu.asu.spring.quadriga.db;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.Project;

/**
 * Interface class for methods relating database connections to
 * workbench component
 * @author Kiran Kumar Batna
 * @author Rohit Pendbhaje
 *
 */
public interface IDBConnectionProjectManager 
{

	/**
	 * fetches details of projects
	 * 
	 * @param sUserId   userid for logged in user
	 * 
	 * @return list of projects
	 * 
	 * @author rohit pendbhaje
	 * @throws SQLException 
	 * 
	 */
	public abstract IProject getProjectDetails(int projectId) throws SQLException;

	/**
	 * interface to split the comma seperated collaborator roles and 
	 * add them in a list
	 * @param roles - comma seperated Collaborator roles
	 * @return list of 'ICollaboratorRole' objects
	 * @author rohit pendbhaje
	 */
	public abstract List<ICollaboratorRole> splitAndCreateCollaboratorRoles(String roles);

	/**
	 * Interface to fetches list of projects
	 * 
	 * @param sUserId   userid for logged in user
	 * 
	 * @return list of projects
	 * 
	 * @author rohit pendbhaje
	 * 
	 */
	public abstract List<IProject> getProjectOfUser(String sUserId);

	/**
	 * Interface to execute the supplied query to use for the test cases.
	 * @param sQuery - database query to be executed.
	 * @return 1 on success.
	 */
	public abstract int setupTestEnvironment(String sQuery);

	/**
	 * Interface to set the datasource connection to the database
	 * @param dataSource
	 * @author Kiran Kumar Batna
	 */
	public abstract void setDataSource(DataSource dataSource);
	
	/**
	 * Interface to add a project 
	 * @param project object
	 * @return Error message if any error occurs else a blank string
	 * @author Kiran Kumar Batna
	 */
	public abstract String addProjectRequest(IProject project);
	
	/**
	 * Interface to show the Collaborator request.
	 * @param projectid.
	 * @return Iproject - object containing the collaborators
	 *         for the supplied project id.
	 * @author rohit pendbhaje        
	 */
	public abstract IProject showCollaboratorsRequest(int projectid);
	
	public abstract List<IUser> nonCollaboratoringUsersRequest(int projectid);
	
	/**
	 * Interface to add a Collaborator.
	 * @param collaborator
	 * @return 1 on success and 0 on failure
	 * @author rohit pendbhaje
	 */
	public abstract String addCollaboratorRequest(IProject project);
	
	/**
	 * @description    :Interface to delete projects.
	 * @param          :projectIdList - Project internal id's in a comma(,) 
	 *                  separated string.
	 * @return         :errmsg - blank on success and null on failure.
	 * @author         :Kiran Kumar Batna
	 */
	public abstract String deleteProjectRequest(String projectIdList);
}
