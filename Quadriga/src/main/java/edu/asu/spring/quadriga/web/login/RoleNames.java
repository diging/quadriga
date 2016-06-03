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

	//Quadriga Role Constants
    /**
     * Quadriga-wide role: users with no account yet. Not yet used anymore, but useful
     * when connecting an LDAP server.
     */
	public static final String ROLE_QUADRIGA_NOACCOUNT = "ROLE_QUADRIGA_NOACCOUNT";
	/**
	 * Quadriga-wide role: users with an deactivated account.
	 */
	public static final String ROLE_QUADRIGA_DEACTIVATED = "ROLE_QUADRIGA_DEACTIVATED";
	/**
     * Quadriga-wide role: users with limited access.
     */
	public static final String ROLE_QUADRIGA_RESTRICTED = "ROLE_QUADRIGA_USER_RESTRICTED_USER";
	/**
     * Quadriga-wide role: users with admin privileges.
     */
	public static final String ROLE_QUADRIGA_ADMIN = "ROLE_QUADRIGA_USER_ADMIN";
	/**
     * FIXME: do we need this?
     */
	public static final String DB_ROLE_QUADRIGA_NOACCOUNT = "role_noaccount";
	/**
     * Quadriga-wide role: users with standard privileges. Allows creation of projects,
     * concept collections, dictionaries, workspaces.
     */
	public static final String ROLE_QUADRIGA_USER_STANDARD = "ROLE_QUADRIGA_USER_STANDARD";
	/**
     * Quadriga-wide role: users can collaborate on projects but can't create their own projects, 
     * concept collections, or dictionaries.
     */
	public static final String ROLE_QUADRIGA_USER_COLLABORATOR = "ROLE_QUADRIGA_USER_COLLABORATOR";
	
	//project collaborator roles constants
	/**
     * Project role: owner of a project.
     */
	public static final String ROLE_COLLABORATOR_OWNER = "ADMIN";
	/**
     * Project role: admin of a project.
     */
	public static final String ROLE_PROJ_COLLABORATOR_ADMIN = "PROJECT_ADMIN";
	/**
     * Project role: contributor of a project.
     */
	public static final String ROLE_PROJ_COLLABORATOR_CONTRIBUTOR = "CONTRIBUTOR";
	/**
     * Project role: editor on a project.
     */
	public static final String ROLE_PROJ_COLLABORATOR_EDITOR = "EDITOR";
	
	//Workspace collaborator roles constants
	/**
     * Workspace role: admin on a workspace.
     */
	public static final String ROLE_WORKSPACE_COLLABORATOR_ADMIN = "SINGLE_WORKSPACE_ADMIN";
	/**
     * Workspace role: contributor on a workspace.
     */
	public static final String ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR = "SINGLE_WORKSPACE_CONTRIBUTOR";
	/**
     * Workspace role: editor on a workspace.
     */
	public static final String ROLE_WORKSPACE_COLLABORATOR_EDITOR = "EDITOR";
	
	//dictionary collaborator roles constants
	/**
     * Dictionary role: admin on a dictionary.
     */
	public static final String ROLE_DICTIONARY_COLLABORATOR_ADMIN = "ADMIN";
	/**
     * Dictionary role: read a dictionary.
     */
	public static final String ROLE_DICTIONARY_COLLABORATOR_READ = "DICT_READ_ACCESS";
	/**
     * Dictionary role: read and write a dictionary.
     */
	public static final String ROLE_DICTIONARY_COLLABORATOR_READ_WRITE = "DICT_READ/WRITE_ACCESS";
	
	//concept collection roles constants
	/**
     * Concept Collection role: admin on a concept collection.
     */
	public static final String ROLE_CC_COLLABORATOR_ADMIN = "ADMIN";
	/**
     * Concept Collection role: read and write a concept collection.
     */
	public static final String ROLE_CC_COLLABORATOR_READ_WRITE = "CC_READ_WRITE_ACCESS";
	/**
     * Concept Collection role: read a concept collection.
     */
	public static final String ROLE_CC_COLLABORATOR_READ = "CC_READ_ACCESS";
	
}
