package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.IDspaceUpdateManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class updates the dspace metadata associated with the given dspace objects.
 * It has a thread to connect to the database and update the information.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public class DspaceUpdateManager implements IDspaceUpdateManager {

	ICommunity community;
	ICollection collection;
	IItem item;
	IBitStream bitstream;

	private String username;

	private IDBConnectionDspaceManager dbconnectionManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DspaceUpdateManager.class);


	/**
	 * The class objects are to set during the creation of the object. 
	 * 
	 * @param dataSource		The datasource object to be used for the database connection.
	 * @param community			The community object with the updated metadata.
	 * @param collection		The collection object with the updated metadata.
	 * @param item				The item object with the updated metadata.
	 * @param bitstream			The bitstream object with the updated metadata.
	 * @param username			The userid of the user who is trying to update the information.
	 */
	public DspaceUpdateManager(DataSource dataSource, ICommunity community, ICollection collection, IItem item, IBitStream bitstream, String username)
	{
		this.community = community;
		this.collection = collection;
		this.item = item;
		this.bitstream = bitstream;
		this.dbconnectionManager = new DBConnectionDspaceManager();
		this.dbconnectionManager.setDataSource(dataSource);
		this.username = username;
	}

	/**
	 * Once the dspace objects are set using the constructor. This thread is responsible to update the information in the database.
	 * It makes sure that the collection and bitstream objects are fetched from the dspace before trying to push the values to the database.
	 */
	@Override
	public void run() {

		try {
			//Update the community metadata in Quadriga database
			dbconnectionManager.updateCommunity(bitstream.getCommunityid(), community.getName(), community.getShortDescription(), community.getIntroductoryText(), community.getHandle(), this.username);

			//Wait for the collection to load
			while(collection.getName() == null)
			{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			//Update the collection metadata in Quadriga database
			dbconnectionManager.updateCollection(bitstream.getCommunityid(), collection.getId(), collection.getName(), collection.getShortDescription(), collection.getEntityReference(), collection.getHandle(), this.username);
			IItem item = null;
			for(IItem collectionItem : collection.getItems())
			{
				if(collectionItem.getId().equals(bitstream.getItemid()))
				{
					item = collectionItem;
					break;
				}
			}

			//Update the item metadata in Quadriga database
			dbconnectionManager.updateItem(bitstream.getCommunityid(), bitstream.getCollectionid(), bitstream.getItemid(), item.getName(), item.getHandle(), username);

			//Get all the bitstreams within this item
			List<IBitStream> bitstreams = item.getBitstreams();
			IBitStream updatedBitstream = null;
			for(IBitStream stream : bitstreams)
			{
				if(stream.getId().equals(bitstream.getId()))
				{
					//Found the required bitstream
					updatedBitstream = stream;
					break;
				}
			}
			
			//Wait for the bitstream to load
			while(updatedBitstream.getName() == null)
			{
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			//Update the bitstream metadata in Quadriga database
			dbconnectionManager.updateBitStream(bitstream.getCommunityid(), bitstream.getCollectionid(), bitstream.getItemid(), bitstream.getId(), updatedBitstream.getName(), updatedBitstream.getSize(), updatedBitstream.getMimeType(), username);
			

		} catch (QuadrigaStorageException e) {
			logger.error("Exception occurred while trying to update Dspace Metadata",e);
		}
	}
}
