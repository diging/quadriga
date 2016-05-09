package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjectDAO {
	
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

