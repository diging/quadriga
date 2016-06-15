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

    public final static String MAIN_ROLES = "main_roles";
    public final static String PROJECT_ROLES = "project_roles";
    public final static String CONCEPT_COLLECTION_ROLES = "collection_roles";
    public final static String DICT_ROLES = "dict_roles";
    public final static String WORKSPACE_ROLES = "workspace_roles";
    
	/**
	 * Method to get the QuadrigaRole object based on RoleId.
	 * @param sQuadrigaRoleDBId		The RoleId for which the corresponding QuadrigaRole object should be returned
	 * @return						The corresponding QuadrigaRole object.
	 */
	public abstract IQuadrigaRole getQuadrigaRoleByDbId(String type, String sQuadrigaRoleDBId);
	
	
	/**
	 * Returns the list of QuadrigaRoles used in the application context.
	 * @return 			List of QuadrigaRoles
	 */
	public abstract List<IQuadrigaRole> getQuadrigaRoles(String type);

	/**
	 * Returns the role id used in the database for the given user role.
	 * @param sQuadrigaRoleId	The quadriga role id of the user
	 * @return	The database role id corresponding to the quadriga role
	 */
	public abstract String getQuadrigaRoleDBId(String type, String sQuadrigaRoleId);
	
	public void fillQuadrigaRole(String type, IQuadrigaRole collaboratorRole);


    public abstract IQuadrigaRole getQuadrigaRoleById(String type, String id);


    public abstract List<IQuadrigaRole> getSelectableQuarigaRoles(String type);
}
