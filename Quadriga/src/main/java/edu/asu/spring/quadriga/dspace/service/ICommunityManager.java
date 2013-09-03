package edu.asu.spring.quadriga.dspace.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;

/**
 * The interface to the CommunityManager which handles the request for 
 * Community, Collection, Item and Bitstreams.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface ICommunityManager {

	/**
	 * Get all the communities available for the given user. For the first call, this method makes a request to Dspace.
	 * After the first call, it loads the values from the cache.
	 * 
	 * @param restTemplate			The RestTemplate variable containing the information on parsers.
	 * @param dspaceProperties		The property strings related to dspace REST service connection.
	 * @param dspaceKeys			The Dspace Access keys used by the user.
	 * @param sUserName				The dspace username of the user.
	 * @param sPassword				The corresponding dspace password of the user
	 * @return						List of communities retrieved from Dspace.
	 * @throws NoSuchAlgorithmException 
	 * @throws QuadrigaAccessException 
	 */
	public abstract List<ICommunity> getAllCommunities(RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws NoSuchAlgorithmException, QuadrigaAccessException;

	/**
	 * Get all the collections available for the given user. For the first call, this method makes a request to Dspace.
	 * The method {@link #getAllCommunities(RestTemplate, Properties, String, String)} should have loaded all 
	 * the community data before this method can be called for the first time. After the first call, it loads the values from the cache. 
	 * 
	 * @param restTemplate				The RestTemplate variable containing the information on parsers.
	 * @param dspaceProperties			The property strings related to dspace REST service connection.
	 * @param dspaceKeys				The Dspace Access keys used by the user.
	 * @param sUserName					The dspace username of the user.
	 * @param sPassword					The corresponding dspace password of the user
	 * @param sCommunityId				The community id for which the list of collections are to be fetched.
	 * @return							List of collections that belong to the given community id.
	 */
	public abstract List<ICollection> getAllCollections(RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys,
			String sUserName, String sPassword, String sCommunityId);

	/**
	 * Get the community name for the given communityId. The method {@link #getAllCommunities(RestTemplate, Properties, String, String)} should have loaded all 
	 * the community data before this method can be called.
	 * 
	 * @param sCommunityId	The community id whose community name is to be found.
	 * @return				The community name associated with the id.  Will be NULL if there no matching community id was found.
	 */
	public abstract String getCommunityName(String sCommunityId);

	/**
	 * Get the collection object for a given collection id. Information can be loaded from cache or again loaded directly from 
	 * the dspace server. This method can also be used to refresh the existing dspace data.
	 * 
	 * @param sCollectionId				The collection id whose collection object is to be fetched.
	 * @param fromCache					FALSE - If the collection object is to be loaded from dspace. TRUE - If the collection object 
	 * 									can be fetched from Quadriga cache. 
	 * @param restTemplate				The RestTemplate object containing the details about the parser. Only needed if fromCache is set to FALSE.
	 * @param dspaceProperties			The property strings related to dspace REST service connection. Only needed if fromCache is set to FALSE.
	 * @param dspaceKeys				The Dspace Access keys used by the user.
	 * @param sUserName					The username of the authorized user. Only needed if fromCache is set to FALSE.
	 * @param sPassword					The password of the authorized user. Only needed if fromCache is set to FALSE.
	 * @param communityid				The id of the community to which the collection belongs to.
	 * @return							The collection object for the given collection id.  Will be NULL if there no matching collection id was found.
	 * @throws NoSuchAlgorithmException 
	 * @throws QuadrigaAccessException 
	 */
	public abstract ICollection getCollection(String sCollectionId, boolean fromCache, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword,String communityid) throws NoSuchAlgorithmException, QuadrigaAccessException;

	/**
	 * Get all items within a collection. The methods {@link #getAllCommunities(RestTemplate, Properties, String, String)} and {@link #getAllCollections(RestTemplate, Properties, String, String, String)} 
	 * should have loaded all the community and collection data before this method can be called.
	 * 
	 * @param sCollectionId	The collection id whose items are to be fetched
	 * @return				All the items within the requested collection.
	 */
	public abstract List<IItem> getAllItems(String sCollectionId);

	/**
	 * Get the Collection name for the given collectionId. The methods {@link #getAllCommunities(RestTemplate, Properties, String, String)} and {@link #getAllCollections(RestTemplate, Properties, String, String, String)} 
	 * should have loaded all the community and collection data before this method can be called.
	 * 
	 * @param sCollectionId	The collection id whose collection name is to be found.
	 * @return				The collection name associated with the id.  Will be NULL if there no matching collection id was found.
	 */
	public abstract String getCollectionName(String sCollectionId);

	/**
	 * Get the community id to which the collection belongs to. The methods {@link #getAllCommunities(RestTemplate, Properties, String, String)} and {@link #getAllCollections(RestTemplate, Properties, String, String, String)} 
	 * should have loaded all the community and collection data before this method can be called.
	 * 
	 * @param sCollectionId	The collection id of the collection.
	 * @return				The community id to which the collection belongs to.  Will be NULL if there no matching collection id was found.
	 */
	public abstract String getCommunityId(String sCollectionId);

	/**
	 * Get the item name of the item. The methods {@link #getAllCommunities(RestTemplate, Properties, String, String)} and {@link #getAllCollections(RestTemplate, Properties, String, String, String)} 
	 * should have loaded all the community and collection data before this method can be called.
	 * 
	 * @param sCollectionId	The collection id of the collection to which the item belongs to.
	 * @param sItemId		The item id for which the item name is requested.
	 * @return				The item name of the item.  Will be NULL if there no matching collection/item id was found.
	 */
	public abstract String getItemName(String sCollectionId, String sItemId);

	/**
	 * Get all bitstreams within an item. The methods {@link #getAllCommunities(RestTemplate, Properties, String, String)} and {@link #getAllCollections(RestTemplate, Properties, String, String, String)} 
	 * should have loaded all the community and collection data before this method can be called.
	 * 
	 * @param sCollectionId		The collection id of the collection to which the item and bitstream belongs to.
	 * @param sItemId			The item id of the item to which the bitstream belongs to.
	 * @return					List of bitstreams that are available within an item.
	 * 
	 */
	public abstract List<IBitStream> getAllBitStreams(String sCollectionId,String sItemId);

	/**
	 * Get the bitstream name. The methods {@link #getAllCommunities(RestTemplate, Properties, String, String)} and {@link #getAllCollections(RestTemplate, Properties, String, String, String)} 
	 * should have loaded all the community and collection data before this method can be called.
	 * 
	 * @param sCollectionId	The collection id of the collection to which the bitstream belongs to.
	 * @param sItemId		The item id to the item to which the item belongs to.
	 * @param sBitStreamId	The bitstream id for which the name is requested.
	 * @return				The bitstream name of the bitstream. Will be NULL if there no matching collection/item/bitstream id was found.
	 */
	public abstract IBitStream getBitStream(String sCollectionId, String sItemId,
			String sBitStreamId);

	/**
	 * Get the community object for a given collection id. Information can be loaded from cache or again loaded directly from 
	 * the dspace server. This method can also be used to refresh the existing dspace data.
	 * 
	 * @param communityId				The id of the community whose data is requested.
	 * @param fromCache					FALSE - If the community object is to be loaded from dspace. TRUE - If the community object 
	 * 									can be fetched from Quadriga cache.
	 * @param restTemplate				The RestTemplate object containing the details about the parser. Only needed if fromCache is set to FALSE.
	 * @param dspaceProperties			The property strings related to dspace REST service connection. Only needed if fromCache is set to FALSE.
	 * @param dspaceKeys				The Dspace Access keys used by the user.
	 * @param sUserName					The username of the authorized user. Only needed if fromCache is set to FALSE.
	 * @param sPassword					The password of the authorized user. Only needed if fromCache is set to FALSE.
	 * @return							The community object for the corresponding community id. Will be NULL if there is no matching community id was found.
	 * @throws NoSuchAlgorithmException 
	 * @throws QuadrigaAccessException 
	 */
	public abstract ICommunity getCommunity(String communityId, boolean fromCache, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws NoSuchAlgorithmException, QuadrigaAccessException;

	/**
	 * Get the item object for the corresponding item id.
	 * 
	 * @param collectionId		The id of the collection to which the item belongs to.
	 * @param itemId			The id of the item which is requested.
	 * @return					The item object matching the id provided.  Will be NULL if there no matching collection/item id was found.
	 */
	public abstract IItem getItem(String collectionId, String itemId);

	/**
	 * Clear all the cached dspace information
	 */
	public abstract void clearCompleteCache();
}