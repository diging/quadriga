package edu.asu.spring.quadriga.db.workbench;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjCollabManager {

	public abstract String addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName)
			throws QuadrigaStorageException;
	
	public abstract String deleteColloratorRequest(String userName, String projectid) throws QuadrigaStorageException;

	public abstract void setupTestEnvironment(String sQuery) throws QuadrigaStorageException;

}
