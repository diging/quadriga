package edu.asu.spring.quadriga.service.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWSManager {

	public abstract List<IWorkspace> listDeactivatedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkspace> listArchivedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkspace> listActiveWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkspace> listWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkspace> listActiveWorkspaceByCollaborator(String projectid,
			String user) throws QuadrigaStorageException;

	List<IWorkspace> listWorkspaceOfCollaborator(String projectid, String user)
			throws QuadrigaStorageException;

}
