package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * The interface to the DspaceManger which handles the url, RestTemplate required to make the REST service call.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IDspaceManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;
	
	public final static String COMMUNITY_EXISTS = "community exists";
	public final static String COLLECTON_EXISTS = "collection exists";
	
	/**
	 * Get all the communities available for the given user
	 * 
	 * @param sUserName		The dspace username of the user.
	 * @param sPassword		The corresponding dspace password of the user
	 * @return				List of communities retrieved from Dspace.
	 */
	public abstract List<ICommunity> getAllCommunities(String sUserName, String sPassword);

	/**
	 * Get all the collections available for the given user
	 * 
	 * @param sUserName			The dspace username of the user.
	 * @param sPassword			The corresponding dspace password of the user
	 * @param sCommunityId		The community id for which the list of collections are to be fetched.
	 * @return					List of collections that belong to the given community id.
	 */
	public abstract List<ICollection> getAllCollections(String sUserName, String sPassword,String sCommunityId);
	
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
	 * @param sUserName			The dspace username of the user.
	 * @param sPassword			The corresponding dspace password of the user.
	 * @param sCollectionId		The collection id of the collection to which the item and bitstream belongs to.
	 * @param sItemId			The item id of the item to which the bitstream belongs to.
	 * @return					List of bitstreams that are available within an item.
	 * 
	 */
	public abstract List<IBitStream> getAllBitStreams(String sUserName, String sPassword, String sCollectionId, String sItemId);
	
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
	 */
	public abstract ICollection getCollection(String sCollectionId);

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

	public abstract void addBitStreamsToWorkspace(String workspaceId, String communityId,
			String collectionId, String itemId, String[] bitstreamIds,
			String username)throws QuadrigaStorageException, QuadrigaAccessException;

	public abstract void deleteBitstreamFromWorkspace(String workspaceid, String[] bitstreamids,
			String username) throws QuadrigaStorageException,
			QuadrigaAccessException;

}
