package edu.asu.spring.quadriga.service.workspace.mapper;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceDeepMapper {

	public abstract IWorkSpace getWorkSpaceDetails(String workspaceId)
			throws QuadrigaStorageException;

	IWorkSpace getWorkSpaceDetails(String workspaceId, String userName)
			throws QuadrigaStorageException;

}