package edu.asu.spring.quadriga.service;

import java.util.List;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;

/**
 * Interface class that places restraints on the QuadrigaRoleManager class to implement
 * the required behaviors.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public interface IQuadrigaRoleManager {

	/**
	 * Method to set the QuadrigaRoles list.
	 * @param quadrigaRoles The list of QuadrigaRoles which will be used by the QuadrigaRoleManager
	 */
	public abstract void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles);
	
	/**
	 * Method to get the QuadrigaRole object based on RoleId.
	 * @param sQuadrigaRoleDBId		The RoleId for which the corresponding QuadrigaRole object should be returned
	 * @return						The corresponding QuadrigaRole object.
	 */
	public abstract IQuadrigaRole getQuadrigaRole(String sQuadrigaRoleDBId);
	
	
	/**
	 * Returns the list of QuadrigaRoles used in the application context.
	 * @return 			List of QuadrigaRoles
	 */
	public abstract List<IQuadrigaRole> getQuadrigaRoles();

	/**
	 * Returns the role id used in the database for the given user role.
	 * @param sQuadrigaRoleId	The quadriga role id of the user
	 * @return	The database role id corresponding to the quadriga role
	 */
	public abstract String getQuadrigaRoleDBId(String sQuadrigaRoleId);
	
}
