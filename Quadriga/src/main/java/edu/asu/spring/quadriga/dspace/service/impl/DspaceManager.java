package edu.asu.spring.quadriga.dspace.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * The purpose of the class is to make rest service calls to dspace
 * and fetch the communities, collections, items and bitstreams
 * This class manages the rest template, url, username and password.
 *  
 * @author Ram Kumar Kumaresan
 *
 */

@Service
@Scope(value="session", proxyMode= ScopedProxyMode.INTERFACES)
public class DspaceManager implements IDspaceManager{

	@Autowired
	@Qualifier("dspaceFilePath")
	private String filePath;

	@Autowired
	@Qualifier("dspaceURL")
	private String url;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	//Handle to the proxy community manager class
	@Autowired
	private ICommunityManager proxyCommunityManager;


	@Autowired
	private IDBConnectionDspaceManager dbconnectionManager;


	private static final Logger logger = LoggerFactory
			.getLogger(DspaceManager.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICommunity> getAllCommunities(String sUserName, String sPassword) {

		//TODO: Remove this after actual user synchronization to Dspace
		sUserName="ramk@asu.edu";
		sPassword="123456";

		return proxyCommunityManager.getAllCommunities(restTemplate, url, sUserName, sPassword);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICollection> getAllCollections(String sUserName, String sPassword, String sCommunityId) {

		//TODO: Remove this after actual user synchronization to Dspace
		sUserName="ramk@asu.edu";
		sPassword="123456";

		return proxyCommunityManager.getAllCollections(restTemplate, url, sUserName, sPassword, sCommunityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityName(String sCommunityId) 
	{
		return proxyCommunityManager.getCommunityName(sCommunityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCollectionName(String sCollectionId) 
	{
		return proxyCommunityManager.getCollectionName(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollection getCollection(String sCollectionId)
	{
		return proxyCommunityManager.getCollection(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityId(String sCollectionId)
	{
		return proxyCommunityManager.getCommunityId(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IItem> getAllItems(String sCollectionId) {
		return  proxyCommunityManager.getAllItems(sCollectionId); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IBitStream> getAllBitStreams(String sUserName, String sPassword, String sCollectionId, String sItemId)
	{
		//TODO: Remove this after actual user synchronization to Dspace
		sUserName="ramk@asu.edu";
		sPassword="123456";

		return proxyCommunityManager.getAllBitStreams(restTemplate, url, sUserName, sPassword, sCollectionId, sItemId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getItemName(String sCollectionId, String sItemId)
	{
		return proxyCommunityManager.getItemName(sCollectionId, sItemId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBitStream getBitStream(String sCollectionId, String sItemId, String sBitStreamId)
	{
		return proxyCommunityManager.getBitStream(sCollectionId, sItemId, sBitStreamId);
	}


	@Override
	public int addBitStreamsToWorkspace(String workspaceId, String communityId, String collectionId, String itemId, String[] bitstreamIds, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		ICommunity community = proxyCommunityManager.getCommunity(communityId);
		ICollection collection = proxyCommunityManager.getCollection(collectionId);
		IItem item = proxyCommunityManager.getItem(collectionId, itemId);

		//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
		if(community == null || collection == null || item == null)
		{
			logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
			logger.info("Community id: "+communityId);
			logger.info("Collection id: "+collectionId);
			logger.info("Item id: "+itemId);
			logger.info("Bitstreams selected: "+bitstreamIds.length);
			throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
		}

		//Check the status of community, collection and item in the database
		String status = dbconnectionManager.checkDspaceNodes(communityId, collectionId, itemId);
		if(status == null)
		{
			//Community, Collection and Item metadata does not exist	
			dbconnectionManager.addCommunity(communityId, community.getName(), community.getShortDescription(), community.getIntroductoryText(), community.getHandle(), username);
			dbconnectionManager.addCollection(communityId, collectionId, collection.getName(), collection.getShortDescription(), collection.getEntityReference(), collection.getHandle(), username);
			dbconnectionManager.addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username);

		}
		else
		{
			//Community metadata is found in the database
			if(status.equalsIgnoreCase("community exists"))
			{
				//Collection and Item metadata does not exist. Add the metadata to the database.				
				dbconnectionManager.addCollection(communityId, collectionId, collection.getName(), collection.getShortDescription(), collection.getEntityReference(), collection.getHandle(), username);
				dbconnectionManager.addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username);

			}
			//Collection metadata is found in the database
			else if(status.equalsIgnoreCase("collection exists"))
			{
				//Item metadata does not exist. Add the metadata to the database.
				dbconnectionManager.addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username);
			}
		}

		/**The metadata about community, collections and items are added
		 * Now check if each bitstream is already present in the database.
		 * If yes, then just add the bitstreamid to the workspace.
		 * If no, then add the bitstream metadata and then add the bitstreamid to workspace.
		 */
		for(String bitstreamId: bitstreamIds)
		{			
			IBitStream bitstream;
			if(dbconnectionManager.checkDspaceBitStream(bitstreamId)==null)
			{
				bitstream = proxyCommunityManager.getBitStream(collectionId, itemId, bitstreamId);
				//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
				if(bitstream == null)
				{
					logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
					logger.info("Community id: "+communityId);
					logger.info("Collection id: "+collectionId);
					logger.info("Item id: "+itemId);
					logger.info("Bitstreams selected: "+bitstreamIds.length);
					logger.info("Bitstream id: "+bitstreamId);
					throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
				}
				dbconnectionManager.addBitStream(communityId, collectionId, itemId, bitstreamId, bitstream.getName(), bitstream.getSize(), bitstream.getMimeType(), username);
			}

			//Add bitstream to workspace
			dbconnectionManager.addBitstreamToWorkspace(workspaceId, bitstreamId, username);
		}

		return 0;
	}

	/**
	 * This method is used to load the Dspace server certificate during the start of the application.
	 * It also overloads the verify method of the hostname verifier to always return TRUE for the dspace hostname.
	 * It will be invoked only once.
	 */
	public void start()
	{
		logger.info("The certificate filepath used is: "+filePath);
		System.setProperty("javax.net.ssl.trustStore", filePath);


		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier(){

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals(url)) {
							return true;
						}
						return false;
					}
				});
	}

}
