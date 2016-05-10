package edu.asu.spring.quadriga.mapper.workspace;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDeepMapper extends IBaseWorkspaceMapper {

	IWorkSpace mapWorkspaceDTO(WorkspaceDTO workspaceDTO)
			throws QuadrigaStorageException;

	void fillWorkspace(WorkspaceDTO workspaceDTO, IWorkSpace workspace) throws QuadrigaStorageException;

}