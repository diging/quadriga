package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWSManagerDAO {

	List<IWorkSpace> listDeactivatedWorkspace(String projectid, String user)
			throws QuadrigaStorageException;

	List<IWorkSpace> listActiveWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

	List<IWorkSpace> listWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

	List<IWorkSpace> listWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	List<IWorkSpace> listActiveWorkspaceOfOwner(String projectid,
			String username) throws QuadrigaStorageException;

	List<IWorkSpace> listActiveWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	List<IWorkSpace> listArchivedWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

	IWorkSpace getWorkspaceDetails(String workspaceId, String username)
			throws QuadrigaStorageException;

	List<IBitStream> getBitStreams(String workspaceId, String username)
			throws QuadrigaAccessException, QuadrigaStorageException;

}
