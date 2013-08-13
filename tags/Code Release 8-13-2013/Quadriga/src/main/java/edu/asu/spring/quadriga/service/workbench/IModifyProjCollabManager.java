package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyProjCollabManager {

	public abstract String addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName)
			throws QuadrigaStorageException;
	
	public abstract String deleteCollaboratorRequest(String userName, String Projectid) throws QuadrigaStorageException;

	public abstract void updateCollaboratorRequest(String projectid, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

}
