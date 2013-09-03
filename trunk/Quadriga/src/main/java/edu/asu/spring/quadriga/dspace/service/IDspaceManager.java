package edu.asu.spring.quadriga.dspace.service;

import java.util.List;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * The interface to the DspaceManger which handles the url, RestTemplate required to make the REST service call.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IDspaceManager{

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	
	public final static String COMMUNITY_EXISTS = "community exists";
	public final static String COLLECTON_EXISTS = "collection exists";
	
	/**
	 * Get all the communities available for the given user
	 * 
	 * @param dspaceKeys	The Dspace Access keys used by the user.
	 * @param sUserName		The dspace username of the user.
	 * @param sPassword		The corresponding dspace password of the user
	 * @return				List of communities retrieved from Dspace.
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	public abstract List<ICommunity> getAllCommunities(IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaException, QuadrigaAccessException;

	/**
	 * Get all the collections available for the given user
	 * 
	 * @param dspaceKeys		The Dspace Access keys used by the user.
	 * @param sUserName			The dspace username of the user.
	 * @param sPassword			The corresponding dspace password of the user
	 * @param sCommunityId		The community id for which the list of collections are to be fetched.
	 * @return					List of collections that belong to the given community id.
	 */
	public abstract List<ICollection> getAllCollections(IDspaceKeys dspaceKeys, String sUserName, String sPassword,String sCommunityId);
	
	/**
	 * Get all items within a collection.
	 * 
	 * @param sCollectionId	The collection id whose items are to be fetched
	 * @return				All the items within the requested collection.
	 */
	public abstract List<IItem> getAllItems(String sCollectionId);

	/**
	 * Get all bitstreams within an item
	 * 
	 * @param dspaceKeys		The Dspace Access keys used by the user.
	 * @param sUserName			The dspace username of the user.
	 * @param sPassword			The corresponding dspace password of the user.
	 * @param sCollectionId		The collection id of the collection to which the item and bitstream belongs to.
	 * @param sItemId			The item id of the item to which the bitstream belongs to.
	 * @return					List of bitstreams that are available within an item.
	 * 
	 */
	public abstract List<IBitStream> getAllBitStreams(IDspaceKeys dspaceKeys, String sUserName, String sPassword, String sCollectionId, String sItemId);
	
	/**
	 * Get the community name for the given communityId.
	 * 
	 * @param sCommunityId	The community id whose community name is to be found.
	 * @return				The community name associated with the id. Will be null if there is no such community with the id.
	 */
	public abstract String getCommunityName(String sCommunityId);
	
	/**
	 * Get the Collection name for the given collectionId.
	 * 
	 * @param sCollectionId	The collection id whose collection name is to be found.
	 * @return				The collection name associated with the id. Will be null if there is no such collection with the id.
	 */
	public abstract String getCollectionName(String sCollectionId);
	
	/**
	 * Get the collection object for a given collection id.
	 * 
	 * @param sCollectionId	The collection id whose collection object is to be fetched.
	 * @return				The collection object for the given collection id. Will be null of there is no such collection with the id.
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	public abstract ICollection getCollection(String sCollectionId) throws QuadrigaException, QuadrigaAccessException;

	/**
	 * Get the community id to which the collection belongs to.
	 * 
	 * @param sCollectionId	The collection id of the collection.
	 * @return				The community id to which the collection belongs to.
	 */
	public abstract String getCommunityId(String sCollectionId);

	/**
	 * Get the item name of the item.
	 * 
	 * @param sCollectionId	The collection id of the collection to which the item belongs to.
	 * @param sItemId		The item id for which the item name is requested.
	 * @return				The item name of the item.
	 */
	public abstract String getItemName(String sCollectionId, String sItemId);

	/**
	 * Get the bitstream name.
	 * 
	 * @param sCollectionId	The collection id of the collection to which the bitstream belongs to.
	 * @param sItemId		The item id to the item to which the item belongs to.
	 * @param sBitStreamId	The bitstream id for which the name is requested.
	 * @return				The bitstream name of the bitstream.
	 */
	public abstract IBitStream getBitStream(String sCollectionId, String sItemId,
			String sBitStreamId);

	/**
	 * Add a list of bitstreams to a workspace. The user must have access to the workspace.
	 * All the fields are required.
	 * 
	 * @param workspaceId					The id of the workspace to which the bitstream(s) are to be added.
	 * @param communityId					The id of the community to which the bitstream(s) belong to.
	 * @param collectionId					The id of the collection to which the bitstream(s) belong to.
	 * @param itemId						The id of the item to which the bitstream(s) belong to.
	 * @param bitstreamIds					An array of bitstream ids which are to be added to the workspace.
	 * @param username						The userid of the user who is trying to add the bitstream(s) to the workspace.
	 * @throws QuadrigaStorageException		Thrown when any unexpected error occurs in the database.
	 * @throws QuadrigaAccessException		Thrown when a user tries to modify a workspace to which he/she does not have access. Also thrown when a user tries to access this method with made-up request paramaters.
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	public abstract void addBitStreamsToWorkspace(String workspaceId, String communityId,
			String collectionId, String itemId, String[] bitstreamIds,
			String username)throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException, QuadrigaAccessException;

	/**
	 * Delete a list of bitstreams from the workspace. The user must have access to the workspace.
	 * 
	 * @param workspaceid					The id of the workspace from which the bitstream(s) are to be deleted.
	 * @param bitstreamids					An array of bitstream ids which are to be deleted from the workspace.
	 * @param workspaceBitStreams			The list of bitstreams that the user is allowed to see in the workspace, based on the user's Dspace access rights.
	 * @param username						The userid of the user who is trying to delete the bitstream(s) to the workspace.
	 * @throws QuadrigaStorageException		Thrown when any unexpected error occurs in the database.
	 * @throws QuadrigaAccessException		Thrown when a user tries to modify a workspace to which he/she does not have access. Also thrown when a user tries to access this method with made-up request paramaters.
	 */
	public abstract void deleteBitstreamFromWorkspace(String workspaceid, String[] bitstreamids, List<IBitStream> workspaceBitStreams,
			String username) throws QuadrigaStorageException,
			QuadrigaAccessException;

	/**
	 * Clear all the cached dspace information
	 */
	public abstract void clearCompleteCache();

	public abstract void setFilePath(String filePath);

	public abstract String getFilePath();

	public abstract void setRestTemplate(RestTemplate restTemplate);

	public abstract RestTemplate getRestTemplate();

	public abstract void setProxyCommunityManager(ICommunityManager proxyCommunityManager);

	public abstract ICommunityManager getProxyCommunityManager();

	public abstract void setDbconnectionManager(IDBConnectionDspaceManager dbconnectionManager);

	public abstract IDBConnectionDspaceManager getDbconnectionManager();

	public abstract void setDspaceProperties(Properties dspaceProperties);

	public abstract Properties getDspaceProperties();

	/**
	 * Add new Dspace keys or update existing keys in the database. Adding a new key to the system will clear the cached Dspace data.
	 * 
	 * @param dspaceKeys					The IDspaceKeys which stores the key values for the user. Must NOT be null.
	 * @param username						The quadriga username of the user. Must NOT be null.
	 * 
	 * @return								SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException		Thrown when database encountered any problem during the operation. Also thrown when the keys are already present in the database.
	 * @throws QuadrigaAccessException		Thrown when the arguments passed do not satisfy the constraints of the method.
	 */
	public abstract int addDspaceKeys(IDspaceKeys dspaceKeys, String username)	throws QuadrigaStorageException, QuadrigaAccessException;

	/**
	 * Get the Dspace Keys for this user from the Quadriga database.
	 * 
	 * @param username					The quadriga username of the user. Must NOT be null.
	 * @return							The DspaceKey object which will contain the users private and public key values for Dspace.
	 * 									Will be NULL if no data was found in the database.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract IDspaceKeys getDspaceKeys(String username) throws QuadrigaStorageException;

	/**
	 * Get the Masked Dspace Keys for this user from the Quadriga database. Only the last for characters of the keys will be visible.
	 * Other characters will be masked by 'x'.
	 * 
	 * @param username					The quadriga username of the user. Must NOT be null.
	 * @return							The DspaceKey object which will contain the users masked private and public key values for Dspace.
	 * 									Will be NULL if no data was found in the database.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract IDspaceKeys getMaskedDspaceKeys(String username)
			throws QuadrigaStorageException;

	/**
	 * Check whether the user has access to the list of bitstreams in Dspace. Checks in the Dspace cache for finding out the access rights.
	 * Sets the corresponding community, collection, item and bitstream name. Will always return then same number of bitstreams as provided in
	 * input.
	 *  
	 * @param bitstreams		The list of bitstreams which are to be tested against the user rights.
	 * @param dspaceKeys		The Dspace Access keys used by the user.
	 * @param username			The dspace username of the user.
	 * @param password			The corresponding dspace password of the user.
	 * 
	 * @return					All loaded bitstreams contain the corresponding names. If the user doesn't have access then the names are changed to restricted.
	 * 							If the cache is yet to be populated then the bitstream names will be 'Checking BitStream Access...'
	 * @throws QuadrigaException 
	 * @throws QuadrigaAccessException 
	 */
	public abstract List<IBitStream> checkDspaceBitstreamAccess(List<IBitStream> bitstreams, IDspaceKeys dspaceKeys, String username, String password) throws QuadrigaException, QuadrigaAccessException;

	/**
	 * Delete the dspace keys stored in dspace for this user.
	 * 
	 * @param username						The quadriga username of the user.
	 * @return								SUCCESS returns 1. FAILURE returns 0.
	 * @throws QuadrigaStorageException		Thrown when database encountered any problem during the operation.
	 */
	public abstract int deleteDspaceKeys(String username) throws QuadrigaStorageException;

}
