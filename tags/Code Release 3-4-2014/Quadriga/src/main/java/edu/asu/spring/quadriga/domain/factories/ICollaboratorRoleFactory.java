package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
/**
 * Factory interface for Collaborator Role factories.
 * 
 */
public interface ICollaboratorRoleFactory {
	
	/**
	 * Returns an empty object of Collaborator Role
	 */
	public abstract ICollaboratorRole createCollaboratorRoleObject();

}
