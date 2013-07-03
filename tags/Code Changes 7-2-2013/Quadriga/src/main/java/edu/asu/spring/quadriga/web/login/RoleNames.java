package edu.asu.spring.quadriga.web.login;

/**
 *  @description   : Constant string values representing special Quariga roles.
 *  
 *  @Called by     : UserManager.java
 *                 : CollaboratorController.java
 *  
 *  @author        : Kiran Kumar Batna
 *  
 */
public interface RoleNames {

	public static final String ROLE_QUADRIGA_NOACCOUNT = "ROLE_QUADRIGA_NOACCOUNT";
	public static final String ROLE_QUADRIGA_DEACTIVATED = "ROLE_QUADRIGA_DEACTIVATED";
	public static final String ROLE_QUADRIGA_RESTRICTED = "ROLE_QUADRIGA_USER_RESTRICTED_USER";
	public static final String DB_ROLE_QUADRIGA_NOACCOUNT = "role_noaccount";
	public static final String ROLE_COLLABORATOR_ADMIN = "ADMIN";
	
	
}
