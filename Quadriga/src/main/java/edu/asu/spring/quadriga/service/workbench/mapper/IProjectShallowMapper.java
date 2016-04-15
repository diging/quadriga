package edu.asu.spring.quadriga.service.workbench.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.proxy.ProjectProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface has methods to implement the mapping of DTO object to Domain objects for the service layer for Project.
 * These methods need to map {@link ProjectDTO} to {@link ProjectProxy} object.
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IProjectShallowMapper {

	/**
	 * This class should get a {@link List} of {@link IProject} of domain class type {@link ProjectProxy} of a User.
	 * 
	 * @param userName									{@link IUser}'s username of type {@link String}
	 * @return											Returns {@link List} of {@link IProject} object
	 * @throws QuadrigaStorageException					Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IProject> getProjectList(String userName)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link IProject} of domain class type {@link ProjectProxy} for a {@link IProject} ID.
	 * @param projectId									{@link IProject} ID of type {@link String}
	 * @return											Returns the {@link IProject} object
	 * @throws QuadrigaStorageException					Throws the storage exception when the method has issues to access the database
	 */
	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of {@link IProject} of domain class type {@link ProjectProxy} to which the user is collaborator based on username.
	 * @param userName									{@link IUser} name of type {@link String}
	 * @return											{@link List} of {@link IProject}
	 * @throws QuadrigaStorageException					Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IProject> getCollaboratorProjectListOfUser(String userName)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of {@link IProject} of domain class type {@link ProjectProxy} of a workspace owner based on username
	 * @param userName									{@link IUser} name of type {@link String}
	 * @return											{@link List} of {@link IProject}
	 * @throws QuadrigaStorageException					Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IProject> getProjectListAsWorkspaceOwner(String userName)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of {@link IProject} of domain class type {@link ProjectProxy} of a workspace collaborator based on username
	 * @param userName									{@link IUser} name of type {@link String}
	 * @return											{@link List} of {@link IProject}
	 * @throws QuadrigaStorageException					Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IProject> getProjectListAsWorkspaceCollaborator(String userName)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of {@link IProject} of domain class type {@link ProjectProxy} by username and collaborator role.
	 * @param userName									{@link IUser} name of type {@link String}
	 * @param collaboratorRole							Collaborator role of type {@link String}
	 * @return											{@link List} of {@link IProject}
	 * @throws QuadrigaStorageException					Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IProject> getProjectListByCollaboratorRole(String userName,
			String collaboratorRole) throws QuadrigaStorageException;

	/**
	 * This class should get a {@link IProject} of domain class type {@link ProjectProxy} for a {@link ProjectDTO}.
	 * @param projectDTO								{@link ProjectDTO} object
	 * @return											Returns the {@link IProject} object
	 * @throws QuadrigaStorageException					Throws the storage exception when the method has issues to access the database
	 */
	public abstract IProject getProjectDetails(ProjectDTO projectDTO)
			throws QuadrigaStorageException;

	IProject getProjectDetailsForSearch(ProjectDTO projectDTO, String pattern)
			throws QuadrigaStorageException;

	
}