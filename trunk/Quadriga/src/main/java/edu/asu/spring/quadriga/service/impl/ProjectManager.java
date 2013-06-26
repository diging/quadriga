package edu.asu.spring.quadriga.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionProjectManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.web.WorkbenchController;

/**
 * @description this class manages the projects and acts as a bridge between
 *              controller and database
 * 
 * @author rohit pendbhaje
 * 
 */
@Service
public class ProjectManager implements IProjectManager {

	private static final Logger logger = LoggerFactory
			.getLogger(WorkbenchController.class);

	@Autowired
	@Qualifier("DBConnectionProjectManagerBean")
	private IDBConnectionProjectManager dbConnect;

	@Autowired
	private ICollaboratorRoleManager roleMapper;

	/**
	 * @description: this method takes up userid as an argument of the logged in
	 *               user and returns list of projects for the user
	 * 
	 * @throws QuadrigaStorageException
	 * 
	 * @author rohit pendbhaje
	 * 
	 */

	@Override
	public List<IProject> getProjectsOfUser(String userid) {

		List<IProject> projectList = new ArrayList<IProject>();

		try {
				projectList = dbConnect.getProjectOfUser(userid);
			} catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}

		return projectList;
	}

	/**
	 * @description : This calls dbConnectionProjectManger to update the project
	 *              details.
	 * @param : existingProject - Project object
	 * @param : userName - user who tries to update the details.
	 * @return : errmsg - blank on success and null on failure
	 * @author : Kiran Kumar Batna
	 */
	@Override
	public String updateProjectDetails(Project existingProject, String userName)
			throws QuadrigaStorageException {
		String errmsg;

		try {
			errmsg = dbConnect.editProjectRequest(existingProject, userName);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}

		return errmsg;
	}

	/**
	 * @description : This calls dbConnectionProjectManger to delete the project
	 *              records form the tables.
	 * @param : projectIdList - String of comma(,) separated project Ids.
	 * @return : errmsg - blank on success and null on failure
	 * @author : Kiran Kumar Batna
	 */
	@Override
	public String deleteProject(String projectIdList)
			throws QuadrigaStorageException {
		String errmsg;

		try {
			errmsg = dbConnect.deleteProjectRequest(projectIdList);
		} catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}

		return errmsg;

	}

	/**
	 * This is used to add the project details
	 * 
	 * @param Project
	 *            object
	 * @return Error message on error else a blank string
	 * @author Kiran Kumar Batna
	 * 
	 */
	@Override
	public String addNewProject(IProject newProject)
			throws QuadrigaStorageException {
		return dbConnect.addProjectRequest(newProject);
	}

	/**
	 * @description: this method takes up userid as an argument of the logged in
	 *               user and returns project object containing all project
	 *               details
	 * 
	 * @throws SQLException
	 * 
	 * @author rohit pendbhaje
	 * 
	 */
	@Override
	public IProject getProject(int projectid) throws QuadrigaStorageException {

		IProject project = null;
		// List<ICollaboratorRole> collaboratorRolesList = new
		// ArrayList<ICollaboratorRole>();

		project = dbConnect.getProjectDetails(projectid);

		for (ICollaborator collaborator : project.getCollaborators()) {

			for (ICollaboratorRole collaboratorRole : collaborator.getCollaboratorRoles()) {
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
			}
		}
		return project;
	}

	@Override
	public String addCollaborators(ICollaborator collaborator, int projectid)
			throws QuadrigaStorageException {

		String errmsg = null;
		try {
			errmsg = dbConnect.addCollaboratorRequest(collaborator, projectid);
		}

		catch (QuadrigaStorageException e) {
			throw new QuadrigaStorageException();
		}

		return errmsg;
	}

	@Override
	public List<IUser> getNotCollaboratingUsers(int projectid) throws QuadrigaStorageException {

		List<IUser> userList = null;

		userList = dbConnect.nonCollaboratoringUsersRequest(projectid);
		
		return userList;
	}

	
	
	@Override
	public List<IUser> showExistingCollaborator(int projectid){
		List<IUser> userList = null;
		try {
				userList = dbConnect.showCollaboratorsRequest(projectid);
			} 
		catch (QuadrigaStorageException e) {
				e.printStackTrace();
			}

		return userList;
	
	}

	@Override
	public List<ICollaborator> getProjectCollaborator(int projectid) throws QuadrigaStorageException {

		IProject project = null;
		List<ICollaborator> collaboratorList = null;

		project = dbConnect.getProjectDetails(projectid);

		collaboratorList = project.getCollaborators();

		return collaboratorList;
	}
}
