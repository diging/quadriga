package edu.asu.spring.quadriga.mapper.workspace;

import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDeepMapper extends IBaseWorkspaceMapper {

	IWorkspace mapWorkspaceDTO(WorkspaceDTO workspaceDTO)
			throws QuadrigaStorageException;

	void fillWorkspace(WorkspaceDTO workspaceDTO, IWorkspace workspace) throws QuadrigaStorageException;

}