package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWSManagerDAO {

	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid, String user)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

	public abstract  List<IWorkSpace> listWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspaceOfOwner(String projectid,
			String username) throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

	public abstract IWorkSpace getWorkspaceDetails(String workspaceId, String username)
			throws QuadrigaStorageException;

	public abstract List<IBitStream> getBitStreams(String workspaceId, String username)
			throws QuadrigaAccessException, QuadrigaStorageException;

}
