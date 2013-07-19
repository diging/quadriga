package edu.asu.spring.quadriga.dspace.service.impl;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DspaceUpdateManager implements IDspaceUpdateManager {

	ICommunity community;
	ICollection collection;
	IItem item;
	IBitStream bitstream;

	private String username;

	private IDBConnectionDspaceManager dbconnectionManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DspaceUpdateManager.class);


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

	@Override
	public void run() {

		try {
			//Update the community metadata in Quadriga database
			dbconnectionManager.updateCommunity(bitstream.getCommunityid(), community.getName(), community.getShortDescription(), community.getIntroductoryText(), community.getHandle(), this.username);

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
			dbconnectionManager.updateItem(bitstream.getCommunityid(), bitstream.getCollectionid(), bitstream.getItemid(), item.getName(), item.getHandle(), username);

		} catch (QuadrigaStorageException e) {
			logger.error("Exception occurred while trying to update Dspace Metadata",e);
		}

		//		collection = proxyCommunityManager.getCollection(bitstream.getCollectionid());
		//		item = proxyCommunityManager.getItem(bitstream.getCollectionid(), bitstream.getItemid());
	}
}
