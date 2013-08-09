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
	public final static String GET_USERS="sp_getUsers";
	public final static String GET_USERS_NOT_IN_ROLE="sp_getUsersNotInRole";
	
	//project related objects
	public static final String PROJECT_LIST="sp_getProjectList";
	public static final String PROJECT_DETAILS="sp_getProjectDetails";
	public static final String PROJECT_COLLABORATORS="sp_getProjectCollaborators";
	public static final String ADD_PROJECT_REQUEST = "sp_addProjectDetails";
	public static final String DELETE_PROJECT_REQUEST = "sp_deleteproject";
	public static final String MODIFY_PROJECT_REQUEST = "sp_updateProjectDetails";
	public static final String ADD_COLLABORATOR_REQUEST = "sp_addProjectCollaborators";
	public static final String SHOW_COLLABORATOR_REQUEST = "sp_showProjectCollaborators";
	public static final String SHOW_NONCOLLABORATOR_REQUEST = "sp_showProjectNonCollaborators";
	public static final String DELETE_PROJECT_COLLAB_REQUEST = "sp_deleteProjectCollaborators";
	public static final String TRANSFER_PROJECT_OWNERSHIP = "sp_changeprojectowner";
	public static final String UPDATE_PROJECT_COLLAB_REQUEST = "sp_updateprojectcollaborator";

	public static final String ADD_DICTIONARY = "sp_addDictionaryDetails";
	public static final String GET_DICTIONARY_DETAILS = "sp_getDictionaryDetails";
	public static final String GET_DICTIONARY_PERM = "sp_getDictionaryPerm";
	
	public static final String SHOW_DICT_COLLABORATORS = "sp_showDictionaryCollaborators";
	public static final String SHOW_DICT_NONCOLLABORATORS = "sp_showDictionaryNonCollaborators";
	public static final String ADD_DICT_COLLABORATORS = "sp_addDictionaryCollaborators";
	public static final String DELETE_DICT_COLLABORATORS = "sp_deleteDictionaryCollaborators";
	
	
	public static final String GET_CCOWNED_DETAILS = "sp_getConceptCollections";
	public static final String GET_CCCOLLABORATIONS_DETAILS = "sp_getUserCollectionCollaborations";
	public static final String VALIDATE_COLLECTIONID = "sp_validatecollectionid";
	public static final String GET_COLLECTION_DETAILS = "sp_getConceptCollectionDetails";
	public static final String ADD_COLLECTION_ITEM = "sp_addCollectionItems";
	public static final String GET_COLLECTION_COLLABORATOR = "sp_getConceptCollectionCollaborators";
	public static final String ADD_CC_COLLABORATOR_REQUEST = "sp_addCollectionCollaborators";
	public static final String SHOW_CC_COLLABORATOR_REQUEST = "sp_showCollectionCollaborators";
	public static final String SHOW_CC_NONCOLLABORATOR_REQUEST = "sp_showCollectionNonCollaborators";
	public static final String DELETE_CC_COLLABORATOR_REQUEST = "sp_deleteCollectionCollaborators";
	public static final String GET_DICTIONARY_ITEMS_DETAILS = "sp_getDictionaryItems";
	public static final String GET_DICTIONARY_ITEMS_DETAILS_COLLAB = "sp_getDictionaryItems_collab";
	public static final String GET_DICTIONARY_NAME = "sp_getDictionaryName";
	public static final String GET_DICTIONARY_OWNER = "sp_getDictionaryOwner";
	public static final String ADD_DICTIONARY_ITEM = "sp_addDictionaryItems";
	public static final String GET_DICTIONARY_COLLAB = "sp_getDictionaryCollab";
	public static final String GET_DICTIONARY_COLLAB_PERM = "sp_getDictionaryCollabPerm";
	public static final String DELETE_DICTIONARY ="sp_deleteDictionary";
	public static final String DELETE_DICTIONARY_ITEM = "sp_deleteDictionaryItems";
	public static final String DELETE_DICTIONARY_ITEM_COLLAB= "sp_deleteDictionaryItemsCollab";
	public static final String UPDATE_DICTIONARY_ITEM = "sp_updateDictionaryItems";
	public static final String ADD_CONCEPTCOLLECTION ="sp_addConceptCollections";
	public static final String DELETE_COLLECTION_ITEM ="sp_deleteCollectionItem";
	public static final String UPDATE_COLLECTION_ITEM ="sp_updateCollectionItem";
	
	
	//Networks database objects
	public static final String ADD_NETWORK_DETAILS ="sp_addNetworksDetails";
	public static final String ADD_NETWORK_RELATION ="sp_addNetworks_Relation";
	public static final String ADD_NETWORK_APPELLATION ="sp_addNetworks_Appellation";
	public static final String ADD_NETWORK_STATEMENT ="sp_addNetworks_statements";
	public static final String GET_NETWORK_STATUS ="sp_getNetworkStatus";
	public static final String GET_NETWORK_LIST ="sp_getNetworkList";
	public static final String HAS_NETWORK_NAME ="sp_hasNetworkName";
	public static final String GET_PROJECTID_WORKSPACE ="sp_getProjectIdForWorkspace";
	
	// WorkSpace database objects
	public static final String LIST_WORKSPACE = "sp_getWorkspaceList";
	public static final String LIST_ACTIVE_WORKSPACE = "sp_getActiveWorkspaceList";
	public static final String LIST_ARCHIVE_WORKSPACE = "sp_getArchiveWorkspaceList";
	public static final String LIST_DEACTIVATED_WORKSPACE = "sp_getDeactivatedWorkspaceList";
	public static final String ADD_WORKSPACE_REQUEST = "sp_createworkspace";
	public static final String ARCHIVE_WORKSPACE_REQUEST = "sp_archiveworkspace";
	public static final String DEACTIVATE_WORKSPACE_REQUEST = "sp_deactivateworkspace";
	public static final String DELETE_WORKSPACE_REQUEST = "sp_deleteworkspace";
	public static final String UPDATE_WORKSPACE_REQUEST = "sp_updateWorkspaceDetails";
	public static final String WORKSPACE_DETAILS = "sp_getWorkspaceDetails";
	public static final String ADD_PROJECT_DICTIONARY = "sp_addDictionaryToProject";
	public static final String LIST_PROJECT_DICTIONARY = "sp_getProjectDictionaryList";
	public static final String DELETE_PROJECT_DICTIONARY = "sp_deleteProjectDictionary";
	public static final String ADD_PROJECT_CONCEPT_COLLECTION = "sp_addCCToProject";
	public static final String LIST_PROJECT_CONCEPT_COLLECTION = "sp_getProjectCCList";
	public static final String DELETE_PROJECT_CONCEPT_COLLECTION = "sp_deleteProjectCC";
	public static final String ADD_WORKSPACE_DICTIONARY = "sp_addDictionaryToWorkspace";
	public static final String LIST_WORKSPACE_DICTIONARY = "sp_getWorkspaceDictionaryList";
	public static final String DELETE_WORKSPACE_DICTIONARY = "sp_deleteWorkspaceDictionary";
	public static final String ADD_WORKSPACE_CONCEPT_COLLECTION = "sp_addCCToWorkspace";
	public static final String LIST_WORKSPACE_CONCEPT_COLLECTION = "sp_getWorkspaceCCList";
	public static final String DELETE_WORKSPACE_CONCEPT_COLLECTION = "sp_deleteWorkspaceCC";
	public static final String GET_WORKSPACE_COLLABORATOR = "sp_getWorkspaceCollaborators";
	public static final String GET_WORKSPACE_NON_COLLABORATOR ="sp_getWorkspaceNonCollaborators";
	public static final String ADD_WORKSPACE_COLLABORATOR = "sp_addworkspacecollaborators";
	public static final String DELETE_WORKSPACE_COLLABORATOR = "sp_deletewscollaborators";
	public static final String TRANSFER_WORKSPACE_OWNERSHIP = "sp_changeworkspaceowner";
	public static final String UPDATE_WORKSPACE_COLLABORATOR = "sp_updateworkspacecollaborator";
	
	// Dspace database objects
	public static final String CHECK_DSPACEDATA_NODES="sp_checkDsapceNodes";
	public static final String CHECK_DSPACEDATA_BITSTREAM="sp_checkDsapceBitStream";
	public static final String ADD_DSPACE_COMMUNITY = "sp_insertDspaceCommunity";
	public static final String ADD_DSPACE_COLLECTION = "sp_insertDspaceCollection";
	public static final String ADD_DSPACE_ITEM = "sp_insertDspaceItem";
	public static final String ADD_DSPACE_BITSTREAM = "sp_insertDspaceBitStream";
	public static final String ADD_WORKSPACE_BITSTREAM = "sp_addBitstreamToWorkspace";
	public static final String DELETE_WORKSPACE_BITSTREAM = "sp_deleteBitstreamFromWorkspace";
	public static final String UPDATE_DSPACE_COMMUNITY = "sp_updateDspaceCommunity";
	public static final String UPDATE_DSPACE_COLLECTION = "sp_updateDspaceCollection";
	public static final String UPDATE_DSPACE_ITEM = "sp_updateDspaceItem";
	public static final String UPDATE_DSPACE_BITSTREAM = "sp_updateDspaceBitStream";
	public static final String GET_DSPACE_REFERENCES = "sp_getAllBitStreamReferences";
	
	//project Access objects
	public static final String CHECK_PROJECT_OWNER = "fn_checkProjectOwner";
	public static final String CHECK_PROJECT_UNIX_NAME = "fn_checkDuplicateUnixName";
	
	//workspace access objects
	public static final String CHECK_WORKSPACE_OWNER = "fn_checkWorkspaceOwner";
	public static final String LIST_WORKSPACE_BITSTREAM = "sp_getAllBitStreams";
	
	//workspace networks	
	public static final String LIST_WORKSPACE_NETWORKS = "sp_getWorkspaceNetworkList";
	
}
