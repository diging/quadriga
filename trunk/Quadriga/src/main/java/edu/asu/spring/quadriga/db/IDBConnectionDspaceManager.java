package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class handles all the requests to Qaudriga database
 * regarding the dspace data.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public interface IDBConnectionDspaceManager {

	public final static int SUCCESS = 1;
	public final static int FAILURE = 0;

	/**
	 * Add a new dspace community to the Quadriga database. The 
	 * community must NOT already exist in the Quadriga database.
	 * 
	 * @param communityid				The id of the community to be inserted. Must NOT be null.
	 * @param name						The name of the community to be inserted. Must NOT be null.
	 * @param shortDescription			The description of the community.
	 * @param introductoryText			A short introductory text of the community.
	 * @param handle					The community handle used in dspace.
	 * @param username					The quadriga username of the user trying to insert the community. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int addCommunity(String communityid, String name,
			String shortDescription, String introductoryText, String handle,
			String username) throws QuadrigaStorageException;

	/**
	 * Add a new dspace collection to the Quadriga database. The 
	 * collection must NOT already exist in the Quadriga database. 
	 * 
	 * @param communityid				The community id to which the collection belongs to. Must NOT be null.
	 * @param collectionid				The id of the collection to be inserted. Must NOT be null.
	 * @param name						The name of the collection to be inserted.
	 * @param shortDescription			The description of the collection.
	 * @param entityReference			The entity reference provided by dspace.
	 * @param handle					The collection handle provided by dspace.
	 * @param username					The quadriga username of the user trying to insert the collection. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int addCollection(String communityid, String collectionid,
			String name, String shortDescription, String entityReference,
			String handle, String username) throws QuadrigaStorageException;

	/**
	 * Add a new dspace item to the Quadriga database. The 
	 * item must NOT already exist in the Quadriga database.
	 * 
	 * @param communityid				The community id to which the item belongs to. Must NOT be null.
	 * @param collectionid				The collection id to which the item belongs to. Must NOT be null.
	 * @param itemid					The id of the item to be inserted. Must NOT be null.
	 * @param name						The name of the item to be inserted.
	 * @param handle					The item handle provided by dspace.
	 * @param username					The quadriga username of the user trying to insert the item. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int addItem(String communityid, String collectionid,
			String itemid, String name, String handle, String username) throws QuadrigaStorageException;

	/**
	 * Add a new dspace bitstream to the Quadriga database. The 
	 * bitstream must NOT already exist in the Quadriga database.
	 * 
	 * @param communityid				The community id to which the bitstream belongs to. Must NOT be null.
	 * @param collectionid				The collection id to which the bitstream belongs to. Must NOT be null.
	 * @param itemid					The item id to which the bitstream belongs to. Must NOT be null.
	 * @param bitstreamid				The id of the bitstream to be insterted. Must NOT be null.
	 * @param name						The name of the bitstream to be inserted.
	 * @param size						The size of the bistream.
	 * @param mimeType					The type of the bitstream.
	 * @param username					The quadriga username of the user trying to insert the bitstream. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int addBitStream(String communityid, String collectionid,
			String itemid, String bitstreamid, String name, String size,
			String mimeType, String username) throws QuadrigaStorageException;

	/**
	 * This method is used to findout if the metadata of the community, collection and item are already present in
	 * the database.
	 * 
	 * @param communityid				The community id to which the item belongs to. Must NOT be null.
	 * @param collectionid				The collection id to which the item belongs to. Must NOT be null.
	 * @param itemid					The item id to which the bitstream belongs to. Must NOT be null.
	 * 
	 * @return							String mentioning whether community/collection/item exist. NULL if none are present.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract String checkDspaceNodes(String communityid, String collectionid,
			String itemid) throws QuadrigaStorageException;

	/**
	 * This method is used to findout if the metadata of the bitstream is already present in the database.
	 * 
	 * @param bitstreamid				The bitstream id got from dspace.
	 * @return							String mentioning bitstream exists if data is already present. NULL if no data is present.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract String checkDspaceBitStream(String bitstreamid)
			throws QuadrigaStorageException;
	
	/**
	 * Add a bitstream to a workspace in Quadriga.
	 * 
	 * @param workspaceid				The workspace id to which the bitstream is to be added.
	 * @param bitstreamid				The bitstream id to be added from dspace.
	 * @param username					The quadriga username of the user trying to add the bitstream.
	 * 
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insertion operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 * @throws QuadrigaAccessException  Thrown when the user tries to modify a workspace to which he/she does not have access to.
	 */
	public abstract int addBitstreamToWorkspace(String workspaceid, String bitstreamid,
			String username) throws QuadrigaStorageException, QuadrigaAccessException;

	/**
	 * Delete a bitstream from a workspace in Quadriga.
	 * 
	 * @param workspaceid				The workspace id from which the bitstream is to be deleted.
	 * @param bitstreamid				The bitstream id to be deleted from the workspace.
	 * @param username					The quadriga username of the user trying to delete the bitstream.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 * @throws QuadrigaAccessException  Thrown when the user tries to modify a workspace to which he/she does not have access to.
	 */
	public abstract void deleteBitstreamFromWorkspace(String workspaceid, String bitstreamids,
			String username) throws QuadrigaStorageException, QuadrigaAccessException;

	/**
	 * List all the bitstreams in a workspace. The associated community, collection and item names are also fetched.
	 * 
	 * @param workspaceId				The workspace id for which the bitstreams are to be fetched.
	 * @param username					The quadriga username of the user trying to access the workspace.
	 * 
	 * @return							The list of bitstream objects found in the workspace.
	 * @throws QuadrigaAccessException  Thrown when the user tries to modify a workspace to which he/she does not have access to.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract List<IBitStream> getBitStreamReferences(String workspaceId, String username)
			throws QuadrigaAccessException, QuadrigaStorageException;

	/**
	 * Update the bitstream metadata in the Quadriga database. The bitstream data must already be present in the database.
	 * 
	 * @param communityid				The community id to which the bitstream belongs to. Must NOT be null.
	 * @param collectionid				The collection id to which the bitstream belongs to. Must NOT be null.
	 * @param itemid					The item id to which the bitstream belongs to. Must NOT be null.
	 * @param bitstreamid				The id of the bitstream to be updated. Must NOT be null.
	 * @param name						The name of the bitstream to be inserted.
	 * @param size						The size of the bistream.
	 * @param mimeType					The type of the bitstream.
	 * @param username					The quadriga username of the user trying to update the bitstream. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was updated into the database. FAILURE if the update operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int updateBitStream(String communityid, String collectionid,
			String itemid, String bitstreamid, String name, String size, String mimeType,
			String username) throws QuadrigaStorageException;

	/**
	 * Update the item metadata in the Quadriga database. The item data must already be present in the database.
	 * 
	 * @param communityid				The community id to which the item belongs to. Must NOT be null.
	 * @param collectionid				The collection id to which the item belongs to. Must NOT be null.
	 * @param itemid					The id of the item to be inserted. Must NOT be null.
	 * @param name						The name of the item to be inserted.
	 * @param handle					The item handle provided by dspace.
	 * @param username					The quadriga username of the user trying to update the item. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was updated into the database. FAILURE if the update operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int updateItem(String communityid, String collectionid, String itemid,
			String name, String handle, String username)
			throws QuadrigaStorageException;

	/**
	 * Update the collection metadata in the Quadriga database. The collection data must already be present in the database.
	 * 
	 * @param communityid				The community id to which the collection belongs to. Must NOT be null.
	 * @param collectionid				The id of the collection to be updated. Must NOT be null.
	 * @param name						The name of the collection to be updated.
	 * @param shortDescription			The description of the collection.
	 * @param entityReference			The entity reference provided by dspace.
	 * @param handle					The collection handle provided by dspace.
	 * @param username					The quadriga username of the user trying to update the collection. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was updated into the database. FAILURE if the update operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int updateCollection(String communityid, String collectionid,
			String name, String shortDescription, String entityReference, String handle, String username)
			throws QuadrigaStorageException;

	/**
	 * Update the community metadata in the Quadriga database. The community data must already be present in the database.
	 * 
	 * @param communityid				The community id whose data is to be updated. Must NOT be null.
	 * @param name						The name of the community to be inserted. Must NOT be null.
	 * @param shortDescription			The description of the community.
	 * @param introductoryText			A short introductory text of the community.
	 * @param handle					The community handle used in dspace.
	 * @param username					The quadriga username of the user trying to update the community. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was updated into the database. FAILURE if the update operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int updateCommunity(String communityid, String name,
			String shortDescription, String introductoryText, String handle, String username)
			throws QuadrigaStorageException;

	/**
	 * Set the datasource used in making a connection to the database.
	 * 
	 */
	public abstract void setDataSource(DataSource dataSource);

	/**
	 * Get the datasource used by this class to make a connection to the database. 
	 * @return
	 */
	public abstract DataSource getDataSource();

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
	 * Add new Dspace Keys for a user in Quadriga database. The keys must NOT exist for this user already in the database.
	 * 
	 * @param publicKey					The public key value for the user. Must NOT be null.
	 * @param privateKey				The private key value for the user. Must NOT be null.
	 * @param username					The quadriga username of the user. Must NOT be null.
	 * `
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 */
	public abstract int addDspaceKeys(String publicKey, String privateKey, String username)
			throws QuadrigaStorageException;

}