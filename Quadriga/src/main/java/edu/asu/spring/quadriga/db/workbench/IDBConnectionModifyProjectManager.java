package edu.asu.spring.quadriga.db.workbench;

import java.util.ArrayList;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjectManager 
{
	
	/**
	 * This method changes the owner of the project.
	 * The new user will become the owner of the project and existing owner will become collaborator with 'ADMIN' 
	 * role to the project.
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 * @author kiran kumar batna
	 */
	public abstract void transferProjectOwnerRequest(String projectId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException;

	/**
	 * This methods adds the editor role to a project
	 * @param projectId
	 * @param owner
	 * @throws QuadrigaStorageException
	 * @author kiran kumar batna
	 */
	public abstract void assignProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

	/**
	 * This method deletes the associated editor role of the project
	 * @param projectId
	 * @param owner
	 * @throws QuadrigaStorageException
	 * @author kiran kumar batna
	 */
	public abstract void deleteProjectOwnerEditor(String projectId, String owner)
			throws QuadrigaStorageException;

    /**
     * This method creates a project
     * @param project
     * @param userName
     * @throws QuadrigaStorageException
     * @author kiran kumar batna
     */
	public abstract void addProjectRequest(IProject project, String userName)
			throws QuadrigaStorageException;

	
	/**
	 * This method update the project details
	 * @param projID
	 * @param projName
	 * @param projDesc
	 * @param projAccess
	 * @param unixName
	 * @param userName
	 * @throws QuadrigaStorageException
	 * @author kiran kumar batna
	 */
	public abstract void updateProjectRequest(String projID, String projName, String projDesc,
			String projAccess, String unixName, String userName)
			throws QuadrigaStorageException;

	/**
	 * This method deletes the supplied project id's.
	 * @param projectIdList
	 * @throws QuadrigaStorageException
	 * @author kiran kumar batna
	 */
	public abstract void deleteProjectRequest(ArrayList<String> projectIdList)
			throws QuadrigaStorageException;

	public abstract void deleteProjectEditorMapping(String projectId);

	public abstract void deleteProjectCollaboratorMapping(String projectId);

	public abstract void deleteProjectConceptCollectionMapping(String projectId);

	public abstract void deleteProjectDictionaryMapping(String projectId);

	public abstract void deleteProjectWorkspaceMapping(String projectId);

}
