package edu.asu.spring.quadriga.service;

import java.util.List;
import edu.asu.spring.quadriga.domain.IQuadrigaRoles;

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
	public abstract void setQuadrigaRoles(List<IQuadrigaRoles> quadrigaRoles);
	
	/**
	 * Method to get the QuadrigaRole object based on RoleId.
	 * @param sQuadrigaRoleDBId		The RoleId for which the corresponding QuadrigaRole object should be returned
	 * @return						The corresponding QuadrigaRole object.
	 */
	public abstract IQuadrigaRoles getQuadrigaRole(String sQuadrigaRoleDBId);
	
	
	/**
	 * Returns the list of QuadrigaRoles used in the application context.
	 * @return 			List of QuadrigaRoles
	 */
	public abstract List<IQuadrigaRoles> getQuadrigaRoles();
	
}
