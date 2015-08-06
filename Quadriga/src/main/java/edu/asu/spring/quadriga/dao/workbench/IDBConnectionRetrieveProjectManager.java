package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveProjectManager 
{

	/**
	 * Retrieves the details of specified project.
	 * @param projectId
	 * @return IProject - Project object containing all its details.
	 * @throws QuadrigaStorageException
	 */
//	public abstract IProject getProjectDetails(String projectId)
//			throws QuadrigaStorageException;

	/**
	 * Retrieve the list of projects associated with the given user as project owner.
	 * @param sUserName
	 * @return List<IProject> - list of project objects associated with the user as project oner.
	 * @throws QuadrigaStorageException
	 */
//	List<IProject> getProjectList(String sUserName)
//			throws QuadrigaStorageException;

	/**
	 * Retrieve the list of projects association with the given user as associated
	 * workspaces owner.
	 * @param sUserName
	 * @returnList<IProject> - list of projects associated with user as owner for the projects.
	 * @throws QuadrigaStorageException
	 */
//	List<IProject> getProjectListAsWorkspaceOwner(String sUserName)
//			throws QuadrigaStorageException;

	/**
	 * Retrieves the list of projects associated with given user as a collaborators to 
	 * associated workspaces.
	 * @param sUserName
	 * @return List<IProject> - list of projects associated with the user as collaborator 
	 * to associated workspaces.
	 * @throws QuadrigaStorageException
	 */
//	List<IProject> getProjectListAsWorkspaceCollaborator(String sUserName)
//			throws QuadrigaStorageException;

	/**
	 * Retrieves the project details associated it unix name.
	 * @param unixName
	 * @return IProject - project object containing details associated with given unix name.
	 * @throws QuadrigaStorageException
	 */
//	IProject getProjectDetailsByUnixName(String unixName)
//			throws QuadrigaStorageException;

	/**
	 * Retrieves list of projects associated with the given user as collaborators to projects.
	 * @param sUserName
	 * @return List<IProject> - list of projects associated with the given user as collaborator
	 * as projects.
	 * @throws QuadrigaStorageException
	 */
//	List<IProject> getCollaboratorProjectList(String sUserName)
//			throws QuadrigaStorageException;

	/**
	 * Retrieve list of projects associated with the given user as collaborator to projects.
	 * @param sUserName
	 * @param collaboratorRole
	 * @return List<IProject> - list of projects associated with given user as collaborator with the 
	 * specified role to projects.
	 * @throws QuadrigaStorageException
	 */
//	List<IProject> getProjectListByCollaboratorRole(String sUserName,
//			String collaboratorRole) throws QuadrigaStorageException;
	
	/**
	 * Retrieve the project to which the workspace is associated. A workspace will be associated
	 * with only one project.
	 * 
	 * @param workspaceid					The workspace for which you need to find the project.
	 * @return								Project object to which the workspace belongs. Will be null if the workspace does not belong to any project.
	 * @throws QuadrigaStorageException		Exception will be thrown when the input paramets do not satisfy the system/database constraints or due to database connection troubles.
	 * @author Ram Kumar Kumaresan
	 */
	public abstract IProject getProject(String workspaceid) throws QuadrigaStorageException;
//
//	public abstract List<IProject> getProjectsByConceptCollection(String ccId)
//			throws QuadrigaStorageException;
	
	public List<ProjectDTO> getProjectDTOList(String sUserName) throws QuadrigaStorageException;

	ProjectDTO getProjectDTO(String projectId) throws QuadrigaStorageException;
	
	
	ProjectDTO getProjectDTO(String projectId,String userId) throws QuadrigaStorageException;

	List<ProjectDTO> getCollaboratorProjectDTOListOfUser(String sUserName)
			throws QuadrigaStorageException;

	List<ProjectDTO> getProjectDTOListAsWorkspaceOwner(String sUserName)
			throws QuadrigaStorageException;

	List<ProjectDTO> getProjectDTOListAsWorkspaceCollaborator(String sUserName)
			throws QuadrigaStorageException;

	List<ProjectDTO> getProjectDTOListByCollaboratorRole(String sUserName,
			String collaboratorRole) throws QuadrigaStorageException;

	ProjectDTO getProjectDTOByUnixName(String unixName)
			throws QuadrigaStorageException;
	

}
