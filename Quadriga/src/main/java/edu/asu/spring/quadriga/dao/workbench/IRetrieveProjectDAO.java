package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjectDAO extends IBaseDAO<ProjectDTO> {
    
    /**
     * Returns a list of {@link ProjectDTO}s for the given username. If there is
     * no username given, returns all {@link ProjectDTO}s.
     * @param sUserName Username of user owning a project or null to return all projects.
     * @return list of {@link ProjectDTO}s
     * @throws QuadrigaStorageException If there is something wrong in the query or with the database.
     */
    List<ProjectDTO> getProjectDTOList(String sUserName) throws QuadrigaStorageException;

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

