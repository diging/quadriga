package edu.asu.spring.quadriga.db.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveWSCollabManager {

	public abstract List<ICollaborator> getWorkspaceCollaborators(String workspaceId)
			throws QuadrigaStorageException;

	public abstract List<IUser> getWorkspaceNonCollaborators(String workspaceId)
			throws QuadrigaStorageException;

}
