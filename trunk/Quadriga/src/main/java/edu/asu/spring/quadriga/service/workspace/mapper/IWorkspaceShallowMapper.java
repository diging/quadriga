package edu.asu.spring.quadriga.service.workspace.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.proxy.WorkSpaceProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface has methods to implement the mapping of DTO object to Domain objects for the service layer for Workspace.
 * These methods need to map {@link WorkspaceDTO} to {@link WorkSpaceProxy} object.
 * 
 * @author Lohith Dwaraka
 *
 */
public interface IWorkspaceShallowMapper {

	/**
	 * This class should get a {@link List} of {@link IWorkSpace} of domain class type {@link WorkSpaceProxy} of a {@link IProject} ID.
	 * @param projectId										{@link IProject} ID of type {@link String}
	 * @return												Returns a {@link List} of {@link IWorkSpace} object
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IWorkSpace> getWorkSpaceList(String projectId)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link IWorkSpace} of domain class type {@link WorkSpaceProxy} for a {@link IWorkSpace} ID.
	 * @param workspaceId									{@link IWorkSpace} ID of type {@link String}
	 * @return												Returns {@link IWorkSpace} object of domain class typ {@link WorkSpaceProxy}
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract  IWorkSpace getWorkSpaceDetails(String workspaceId)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of {@link IWorkSpace} of domain class type {@link WorkSpaceProxy} based on {@link IProject} ID and {@link IUser} name.
	 * @param projectId										{@link IProject} ID of type {@link String}
	 * @param userName										{@link IUser} name of type String
	 * @return												Returns a {@link List} of {@link IWorkSpace} object
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IWorkSpace> getWorkSpaceList(String projectId, String userName)
			throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of {@link IWorkSpace} of domain class type {@link WorkSpaceProxy} on the user has collaborator role assigned. This method could be based on {@link IProject} ID and {@link IUser} name.
	 * @param projectid										{@link IProject} ID of type {@link String}
	 * @param username										{@link IUser} name of type String
	 * @return												Returns a {@link List} of {@link IWorkSpace} object
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IWorkSpace> listWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of active {@link IWorkSpace} of domain class type {@link WorkSpaceProxy} of a user based on {@link IProject} ID and {@link IUser} name.
	 * @param projectid										{@link IProject} ID of type {@link String}
	 * @param username										{@link IUser} name of type String
	 * @return												Returns a {@link List} of {@link IWorkSpace} object
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IWorkSpace> listActiveWorkspaceOfOwner(String projectid,
			String username) throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of active {@link IWorkSpace} of domain class type {@link WorkSpaceProxy} of a user based on {@link IProject} ID and collaborator {@link IUser} name.
	 * @param projectid										{@link IProject} ID of type {@link String}
	 * @param username										{@link IUser} name of type String
	 * @return												Returns a {@link List} of {@link IWorkSpace} object
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IWorkSpace> listActiveWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of archived {@link IWorkSpace} object of domain class type {@link WorkSpaceProxy} based on {@link IProject} ID and {@link IUser} name.
	 * @param projectid										{@link IProject} ID of type {@link String}
	 * @param username										{@link IUser} name of type String
	 * @return												Returns a {@link List} of {@link IWorkSpace} object
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid,
			String username) throws QuadrigaStorageException;

	/**
	 * This class should get a {@link List} of deactived {@link IWorkSpace} object of domain class type {@link WorkSpaceProxy} based on {@link IProject} ID and {@link IUser} name.
	 * @param projectid										{@link IProject} ID of type {@link String}
	 * @param username										{@link IUser} name of type String
	 * @return												Returns a {@link List} of {@link IWorkSpace} object
	 * @throws QuadrigaStorageException						Throws the storage exception when the method has issues to access the database
	 */
	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

}