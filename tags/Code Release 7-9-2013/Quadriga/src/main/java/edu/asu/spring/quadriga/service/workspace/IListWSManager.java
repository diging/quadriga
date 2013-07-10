package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWSManager {

	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspace(String projectid)
			throws QuadrigaStorageException;

	public abstract IWorkSpace getWorkspaceDetails(String workspaceId)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspace(String projectid)
			throws QuadrigaStorageException;

}
