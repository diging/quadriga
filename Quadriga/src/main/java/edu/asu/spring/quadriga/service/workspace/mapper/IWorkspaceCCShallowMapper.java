package edu.asu.spring.quadriga.service.workspace.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceCCShallowMapper {
	public abstract List<IWorkspaceConceptCollection> getProjectWorkspaceList(IWorkSpace workspace,WorkspaceDTO workspaceDTO) throws QuadrigaStorageException;

}
