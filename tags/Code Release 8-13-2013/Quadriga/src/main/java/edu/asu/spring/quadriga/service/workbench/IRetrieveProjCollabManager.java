package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IRetrieveProjCollabManager {

	public abstract List<IUser> getProjectNonCollaborators(String projectid)
			throws QuadrigaStorageException;

	public abstract List<ICollaborator> getProjectCollaborators(String projectId)
			throws QuadrigaStorageException;

}
