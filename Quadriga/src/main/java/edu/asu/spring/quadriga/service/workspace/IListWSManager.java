package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWSManager {

	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspaceByCollaborator(String projectid,
			String user) throws QuadrigaStorageException;

	List<IWorkSpace> listWorkspaceOfCollaborator(String projectid, String user)
			throws QuadrigaStorageException;

}
