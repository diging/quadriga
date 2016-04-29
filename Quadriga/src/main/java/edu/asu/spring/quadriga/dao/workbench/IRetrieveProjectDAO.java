package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjectDAO {
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

	List<ProjectDTO> getAllProjectsDTOByAccessibility(String accessibility)
			throws QuadrigaStorageException;
	
	List<ProjectDTO> getAllProjectsDTOBySearchTermAndAccessiblity(String searchTerm, String accessibility)
			throws QuadrigaStorageException;
}

