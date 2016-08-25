package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDAO extends IBaseDAO<WorkspaceDTO> {

    public List<WorkspaceDTO> listWorkspaceDTO(String projectid) throws QuadrigaStorageException;

    List<WorkspaceDTO> listWorkspaceDTO(String projectid, String userName) throws QuadrigaStorageException;

    List<WorkspaceDTO> listWorkspaceDTOofCollaborator(String projectid, String username)
            throws QuadrigaStorageException;

    List<WorkspaceDTO> listActiveWorkspaceDTOofOwner(String projectid, String username) throws QuadrigaStorageException;

    List<WorkspaceDTO> listActiveWorkspaceDTOofCollaborator(String projectid, String username)
            throws QuadrigaStorageException;

    List<WorkspaceDTO> listArchivedWorkspaceDTO(String projectid, String username) throws QuadrigaStorageException;

    List<WorkspaceDTO> listDeactivatedWorkspaceDTO(String projectid, String username) throws QuadrigaStorageException;

    boolean deleteWorkspace(String wsId);

}