package edu.asu.spring.quadriga.service.workspace;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyWSManager {

	public abstract String deleteWorkspaceRequest(String workspaceIdList)
			throws QuadrigaStorageException;

	public abstract String addWorkSpaceRequest(IWorkSpace workspace, String projectId)
			throws QuadrigaStorageException;

}
