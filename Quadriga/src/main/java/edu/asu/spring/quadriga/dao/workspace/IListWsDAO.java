package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IListWsDAO extends IBaseDAO<WorkspaceDTO> {

	/**
	 * Retrieve the list of deactivated workspace for a given user associated to given project
	 * @param projectid
	 * @param user
	 * @return - the list of deactivated workspaces for given user and given project.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	/**
	 * Retrieve the list of archived workspace for the given user and given project.
	 * @param projectid
	 * @param user
	 * @return - the list of archived workspace for the given user and given project.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	/**
	 * Retrieve the workspace details associated to a workspace.
	 * @param workspaceId
	 * @param user
	 * @return - IWorkspace object containing the details of the workspace.
	 * @throws QuadrigaStorageException
	 */
	public abstract IWorkSpace getWorkspaceDetails(String workspaceId,String user)
			throws QuadrigaStorageException;

	/**
	 * Retrieve the list of workspace associated with a project and for a given user.
	 * @param projectid
	 * @param user
	 * @return - List<IWorkspace>  List of workspace associated with project for given user 
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IWorkSpace> listWorkspace(String projectid,String user)
			throws QuadrigaStorageException;

	/**
	 * Retrieve bit streams associated with workspace for a given user.
	 * @param workspaceId
	 * @param username
	 * @return List<IBitStreams> Retrieve bit streams associated with a given workspace and user.
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	public abstract List<IBitStream> getBitStreams(String workspaceId, String username)
			throws QuadrigaStorageException, QuadrigaAccessException;

	/**
	 * Retrieve networks associated with the given workspace
	 * @param workspaceid
	 * @return - List<INetwork> retrieve the list of Networks associated with the workspace.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getWorkspaceNetworkList(String workspaceid)
			throws QuadrigaStorageException;

	/**
	 * Retrieve the workspace name for the given workspace id.
	 * @param workspaceId
	 * @return - Retrieves workspace name for the given workspaceid.
	 * @throws QuadrigaStorageException
	 */
	public abstract String getWorkspaceName(String workspaceId) throws QuadrigaStorageException;

	/**
	 * Retrieve list of rejected networks for a given workspace.
	 * @param workspaceid
	 * @return - Fetches the list of rejected networks for a given workspaceid
	 * @throws QuadrigaStorageException
	 */
	public abstract List<INetwork> getWorkspaceRejectedNetworkList(String workspaceid)
			throws QuadrigaStorageException;
	
	/**
	 * Retrieve list of accepted networks for a given workspace.
	 * @param workspaceid						{@link IWorkSpace} ID in form of {@link String}
	 * @return									{@link List} of {@link INetwork}
	 * @throws QuadrigaStorageException			Throws Storage Exception if there is any issue while accessing Database
	 */
	public abstract List<INetwork> getWorkspaceApprovedNetworkList(String workspaceid)
			throws QuadrigaStorageException;

	/**
	 * Retrieve list of workspace associated to a project for which the user is collaborator.
	 * @param projectid
	 * @param user
	 * @return - List<IWorkSpace> - list of workspace associated to given project for which the 
	 * given user is collaborator.
	 * @throws QuadrigaStorageException
	 */
	public abstract List<IWorkSpace> listWorkspaceOfCollaborator(String projectid, String user)
			throws QuadrigaStorageException;

	
	/**
	 * Retrieve list of workspace associated to given project for which the given user 
	 * is owner. 
	 * @param projectid
	 * @param username
	 * @return - List<IWorkSpace> - list of workspace associated to given project for which
	 * the given user is owner.
	 * @throws QuadrigaStorageException
	 */
	List<IWorkSpace> listActiveWorkspaceOfOwner(String projectid,
			String username) throws QuadrigaStorageException;

	/**
	 * Retrieve list of active workspace associated to given project for which the given user is 
	 * collaborator.
	 * @param projectid
	 * @param username
	 * @return - List<IWorkSpace> - list of active workspace associated to given project for
	 * which the given user is collaborator.
	 * @throws QuadrigaStorageException
	 */
	List<IWorkSpace> listActiveWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	/**
	 * Retrieve list of active workspace associated with a project for a given user.
	 * @param projectid
	 * @param username
	 * @return - List<IWorkSpace> - list of active workspace associated to a project for
	 * given user.
	 * @throws QuadrigaStorageException
	 */
	List<IWorkSpace> listActiveWorkspace(String projectid, String username)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> getWorkspaceByConceptCollection(String ccId)
			throws QuadrigaStorageException;

	public List<WorkspaceDTO> listWorkspaceDTO(String projectid) throws QuadrigaStorageException;

	List<WorkspaceDTO> listWorkspaceDTO(String projectid, String userName)
			throws QuadrigaStorageException;

	List<WorkspaceDTO> listWorkspaceDTOofCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	List<WorkspaceDTO> listActiveWorkspaceDTOofOwner(String projectid,
			String username) throws QuadrigaStorageException;

	List<WorkspaceDTO> listActiveWorkspaceDTOofCollaborator(String projectid,
			String username) throws QuadrigaStorageException;

	List<WorkspaceDTO> listArchivedWorkspaceDTO(String projectid,
			String username) throws QuadrigaStorageException;

	List<WorkspaceDTO> listDeactivatedWorkspaceDTO(String projectid,
			String username) throws QuadrigaStorageException;

	public String getProjectWorkspaceDTO(String workspaceId) throws QuadrigaStorageException;
}
