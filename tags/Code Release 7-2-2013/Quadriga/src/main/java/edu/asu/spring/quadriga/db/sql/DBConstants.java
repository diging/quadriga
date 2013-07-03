package edu.asu.spring.quadriga.db.sql;

/**
 * @Description   : interface to hold the DB string constants.
 * 
 * @Called By     : DBConnectionManager.java
 * 
 * @author        : Kiran Kumar Batna
 * @author 		  : Ram Kumar Kumaresan
 */
public interface DBConstants 
{
	public final static String SP_CALL = "call";
	
	public final static String USER_DETAILS = "sp_getUserDetails";	
	public final static String ACTIVE_USER_DETAILS="sp_getActiveUsers";
	public final static String INACTIVE_USER_DETAILS="sp_getInActiveUsers";
	public final static String DEACTIVATE_USER="sp_deactivateUser";
	public final static String UPDATE_USER_ROLES="sp_updateUserRoles";
	public final static String GET_USER_REQUESTS="sp_getUserRequests";
	public final static String APPROVE_USER_REQUEST="sp_approveUserRequest";
	public final static String DENY_USER_REQUEST="sp_denyUserRequest";
	public final static String ADD_USER_REQUEST="sp_addUserRequest";
	
	public static final String PROJECT_LIST="sp_getProjectList";
	public static final String PROJECT_DETAILS="sp_getProjectDetails";
	public static final String PROJECT_COLLABORATORS="sp_getProjectCollaborators";

	public static final String ADD_PROJECT_REQUEST = "sp_addProjectDetails";
	public static final String DELETE_PROJECT_REQUEST = "sp_deleteproject";
	public static final String MODIFY_PROJECT_REQUEST = "sp_updateProjectDetails";
	public static final String ADD_COLLABORATOR_REQUEST = "sp_addProjectCollaborators";
	
	
	public static final String SHOW_COLLABORATOR_REQUEST = "sp_showProjectCollaborators";
	public static final String SHOW_NONCOLLABORATOR_REQUEST = "sp_showProjectNonCollaborators";

	
	public static final String ADD_DICTIONARY = "sp_addDictionaryDetails";
	public static final String GET_DICTIONARY_DETAILS = "sp_getDictionaryDetails";
	public static final String SHOW_DICT_COLLABORATORS = "sp_showDictionaryCollaborators";
	public static final String SHOW_DICT_NONCOLLABORATORS = "sp_showDictionaryNonCollaborators";
	public static final String ADD_DICT_COLLABORATORS = "sp_addDictionaryCollaborators";
	

	public static final String GET_CCOWNED_DETAILS = "sp_getConceptCollections";
	public static final String GET_CCCOLLABORATIONS_DETAILS = "sp_getUserCollectionCollaborations";
	public static final String VALIDATE_COLLECTIONID = "sp_validatecollectionid";
	public static final String GET_COLLECTION_DETAILS = "sp_getConceptCollectionDetails";
	public static final String ADD_COLLECTION_ITEM = "sp_addCollectionItems";
	public static final String GET_COLLECTION_COLLABORATOR = "sp_getConceptCollectionCollaborators";
	public static final String ADD_CC_COLLABORATOR_REQUEST = "sp_addCollectionCollaborators";
	public static final String SHOW_CC_COLLABORATOR_REQUEST = "sp_showCollectionCollaborators";
	public static final String SHOW_CC_NONCOLLABORATOR_REQUEST = "sp_showCollectionNonCollaborators";
	public static final String GET_DICTIONARY_ITEMS_DETAILS = "sp_getDictionaryItems";
	public static final String GET_DICTIONARY_NAME = "sp_getDictionaryName";
	public static final String ADD_DICTIONARY_ITEM = "sp_addDictionaryItems";
	public static final String DELETE_DICTIONARY ="sp_deleteDictionary";
	public static final String DELETE_DICTIONARY_ITEM = "sp_deleteDictionaryItems";
	public static final String UPDATE_DICTIONARY_ITEM = "sp_updateDictionaryItems";
	public static final String ADD_CONCEPTCOLLECTION ="sp_addConceptCollections";
	public static final String DELETE_COLLECTION_ITEM ="sp_deleteCollectionItem";
	public static final String UPDATE_COLLECTION_ITEM ="sp_updateCollectionItem";
	
	// WorkSpace database objects
	public static final String LIST_WORKSPACE = "sp_getWorkspaceList";
	public static final String LIST_ACTIVE_WORKSPACE = "sp_getActiveWorkspaceList";
	public static final String LIST_ARCHIVE_WORKSPACE = "sp_getArchiveWorkspaceList";
	public static final String LIST_DEACTIVATED_WORKSPACE = "sp_getDeactivatedWorkspaceList";
	public static final String ADD_WORKSPACE_REQUEST = "sp_createworkspace";
	public static final String ARCHIVE_WORKSPACE_REQUEST = "sp_archiveworkspace";
	public static final String DEACTIVATE_WORKSPACE_REQUEST = "sp_deactivateworkspace";
	public static final String DELETE_WORKSPACE_REQUEST = "sp_deleteworkspace";
	public static final String WORKSPACE_DETAILS = "sp_getWorkspaceDetails";

}
