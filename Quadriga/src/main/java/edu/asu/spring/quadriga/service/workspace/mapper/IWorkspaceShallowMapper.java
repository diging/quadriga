package edu.asu.spring.quadriga.service.workspace.mapper;

import java.util.List;

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

}