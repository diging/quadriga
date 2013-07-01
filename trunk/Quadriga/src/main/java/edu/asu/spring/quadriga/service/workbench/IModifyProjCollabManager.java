package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjCollabManager {

	public abstract String addCollaboratorRequest(ICollaborator collaborator, String projectid)
			throws QuadrigaStorageException;

}
