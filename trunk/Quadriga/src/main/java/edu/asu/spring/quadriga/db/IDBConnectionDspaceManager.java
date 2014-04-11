package edu.asu.spring.quadriga.db;

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
	public abstract int addBitstreamToWorkspace(String workspaceid, String bitstreamid, String itemHandle, String username) throws QuadrigaStorageException;

	/**
	 * Delete a bitstream from a workspace in Quadriga.
	 * 
	 * @param workspaceid				The workspace id from which the bitstream is to be deleted.
	 * @param bitstreamid				The bitstream id to be deleted from the workspace.
	 * @param username					The quadriga username of the user trying to delete the bitstream.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation.
	 * @throws QuadrigaAccessException  Thrown when the user tries to modify a workspace to which he/she does not have access to.
	 */
	public abstract void deleteBitstreamFromWorkspace(String workspaceid, String bitstreamids, String username) throws QuadrigaStorageException, QuadrigaAccessException;
	
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
	 * @param dspaceKeys				The IDspaceKeys which stores the key values for the user. Must NOT be null.
	 * @param username					The quadriga username of the user. Must NOT be null.
	 * `
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation. Also thrown when the keys are already present in the database.
	 */
	public abstract int addDspaceKeys(IDspaceKeys dspaceKeys, String username)
			throws QuadrigaStorageException;

	/**
	 * Update the Dspace Keys used by the user in Quadriga. The keys must already exist for this user in the database.
	 * 
	 * @param dspaceKeys				The IDspaceKeys which stores the key values for the user. Must NOT be null.
	 * @param username					The quadriga username of the user. Must NOT be null.
	 * 
	 * @return							SUCCESS when the data was inserted into the database. FAILURE if the insert operation failed.
	 * @throws QuadrigaStorageException Thrown when database encountered any problem during the operation. Also thrown when the keys are not found in the database.
	 */
	public abstract int updateDspaceKeys(IDspaceKeys dspaceKeys, String username)
			throws QuadrigaStorageException;

	public abstract int deleteDspaceKeys(String username) throws QuadrigaStorageException;

	int saveOrUpdateDspaceKeys(IDspaceKeys dspaceKeys, String username)
			throws QuadrigaStorageException;

}