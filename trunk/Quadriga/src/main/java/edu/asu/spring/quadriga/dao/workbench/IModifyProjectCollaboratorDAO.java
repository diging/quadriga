package edu.asu.spring.quadriga.dao.workbench;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjectCollaboratorDAO {

	public abstract void updateCollaboratorRequest(String projectid, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

	public abstract void deleteColloratorRequest(String userName, String projectid)
			throws QuadrigaStorageException;

	public abstract void addCollaboratorRequest(ICollaborator collaborator, String projectid,
			String userName) throws QuadrigaStorageException;

}
