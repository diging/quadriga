package edu.asu.spring.quadriga.dao.workspace;

import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;

public interface IRetrieveWSCollabDAO 
{

	/**
	 * This method returns the list of collaborator roles for the supplied string of 
	 * collaborator roles.
	 * @param collabRoles
	 * @return List<ICollaboratorRole> - list of collaborator roles.
	 */
	public abstract List<IQuadrigaRole> getCollaboratorDBRoleIdList(String collabRoles);

}
