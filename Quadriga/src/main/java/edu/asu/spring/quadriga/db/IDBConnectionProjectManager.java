package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;

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
	 * 
	 */
	public abstract IProject getProjectDetails(String projectId);

	public abstract ICollaboratorRole CollaboratorRole(String roles);

	public abstract IUser projectCollaborators(String collaborators);

	public abstract IUser projectOwner();

	/**
	 * fetches list of projects
	 * 
	 * @param sUserId   userid for logged in user
	 * 
	 * @return list of projects
	 * 
	 * @author rohit pendbhaje
	 * 
	 */
	public abstract List<IProject> getProjectOfUser(String sUserId);

	public abstract int setupTestEnvironment(String sQuery);

	public abstract void setDataSource(DataSource dataSource);

	public abstract int addProjectRequest(IProject project);

}
