package edu.asu.spring.quadriga.dspace.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DspaceUpdateManager implements IDspaceUpdateManager {

	IBitStream bitstream;
	String dspaceUsername;
	String quadrigaUsername;
	String password;
	RestTemplate restTemplate;
	String url;
	private static final Logger logger = LoggerFactory
			.getLogger(DspaceUpdateManager.class);
	
	private ICommunityManager proxyCommunityManager;
	private IDBConnectionDspaceManager dbconnectionManager;
	
	public DspaceUpdateManager(ICommunityManager proxyCommunityManager, IDBConnectionDspaceManager dbconnectionManager, RestTemplate restTemplate, String url, String quadrigaUsername, String dspaceUsername, String password,IBitStream bitstream)
	{
		this.proxyCommunityManager = proxyCommunityManager;
		this.dbconnectionManager = dbconnectionManager;
		this.restTemplate = restTemplate;
		this.url = url;
		this.quadrigaUsername = quadrigaUsername;
		this.dspaceUsername = dspaceUsername;
		this.password = password;
		this.bitstream = bitstream;		
	}
	
	@Override
	public void run() {
		ICommunity community;
		ICollection collection;
		IItem item;
		
		System.out.println("Inside thread for: "+bitstream.getId());
		//Retrieve the new community metadata fetched from Dspace
		community = proxyCommunityManager.getCommunity(bitstream.getCommunityid());
		if(community == null)
		{
			//The values from Dspace are to be fetched for the first time.
			proxyCommunityManager.getAllCommunities(restTemplate, url, dspaceUsername, password);
			community = proxyCommunityManager.getCommunity(bitstream.getCommunityid());
		}
		
		//Update the community metadata in Quadriga database
		try {
			dbconnectionManager.updateCommunity(bitstream.getCommunityid(), community.getName(), community.getShortDescription(), community.getIntroductoryText(), community.getHandle(), quadrigaUsername);
		} catch (QuadrigaStorageException e) {
			logger.error("Exception occurred while trying to update Dspace Metadata",e);
		}
		
//		collection = proxyCommunityManager.getCollection(bitstream.getCollectionid());
//		item = proxyCommunityManager.getItem(bitstream.getCollectionid(), bitstream.getItemid());
	}
}
