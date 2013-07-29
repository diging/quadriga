package edu.asu.spring.quadriga.dspace.service.impl;


import java.util.HashSet;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

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
import edu.asu.spring.quadriga.dspace.service.IDspaceUpdateManager;
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
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;

	//Handle to the proxy community manager class
	@Autowired
	private ICommunityManager proxyCommunityManager;

	@Autowired
	private IDBConnectionDspaceManager dbconnectionManager;
	
	@Resource(name = "dspaceStrings")
	private Properties dspaceProperties;


	private static final Logger logger = LoggerFactory
			.getLogger(DspaceManager.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICommunity> getAllCommunities(String sUserName, String sPassword) {

		return proxyCommunityManager.getAllCommunities(restTemplate, dspaceProperties, sUserName, sPassword);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICollection> getAllCollections(String sUserName, String sPassword, String sCommunityId) {

		return proxyCommunityManager.getAllCollections(restTemplate, dspaceProperties, sUserName, sPassword, sCommunityId);
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
		return proxyCommunityManager.getCollection(sCollectionId,true,null,null,null,null,null);
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
		return proxyCommunityManager.getAllBitStreams(sCollectionId, sItemId);
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


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addBitStreamsToWorkspace(String workspaceId, String communityId, String collectionId, String itemId, String[] bitstreamIds, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		try
		{
			//Passing null values for other arguments because the community details must have already been fetched from dspace
			ICommunity community = proxyCommunityManager.getCommunity(communityId,true,null,null,null,null);
			ICollection collection = proxyCommunityManager.getCollection(collectionId,true,null,null,null,null,null);
			IItem item = proxyCommunityManager.getItem(collectionId, itemId);

			//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
			if(community == null || collection == null || item == null)
			{
				logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
				throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
			}

			//Check the status of community, collection and item in the database
			String status = dbconnectionManager.checkDspaceNodes(communityId, collectionId, itemId);
			if(status == null)
			{
				//Community, Collection and Item metadata does not exist. So add them to the database
				if(dbconnectionManager.addCommunity(communityId, community.getName(), community.getShortDescription(), community.getIntroductoryText(), community.getHandle(), username) == FAILURE)
				{
					logger.info("The user "+username+" got this exception when trying to insert dspace community metadata with the following values:");
					throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
				}
				if(dbconnectionManager.addCollection(communityId, collectionId, collection.getName(), collection.getShortDescription(), collection.getEntityReference(), collection.getHandle(), username)==FAILURE)
				{
					logger.info("The user "+username+" got this exception when trying to insert dspace collection metadata with the following values:");
					throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
				}
				if(dbconnectionManager.addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username)==FAILURE)
				{
					logger.info("The user "+username+" got this exception when trying to insert dspace item metadata with the following values:");
					throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
				}

			}
			else
			{
				//Community metadata is found in the database
				if(status.equalsIgnoreCase(COMMUNITY_EXISTS))
				{
					//Collection and Item metadata does not exist. Add the metadata to the database.				
					if(dbconnectionManager.addCollection(communityId, collectionId, collection.getName(), collection.getShortDescription(), collection.getEntityReference(), collection.getHandle(), username)==FAILURE)
					{
						logger.info("The user "+username+" got this exception when trying to insert dspace collection metadata with the following values:");
						throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
					}
					if(dbconnectionManager.addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username)==FAILURE)
					{
						logger.info("The user "+username+" got this exception when trying to insert dspace item metadata with the following values:");
						throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
					}

				}
				//Collection metadata is found in the database
				else if(status.equalsIgnoreCase(COLLECTON_EXISTS))
				{
					//Item metadata does not exist. Add the metadata to the database.
					if(dbconnectionManager.addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username)==FAILURE)
					{
						logger.info("The user "+username+" got this exception when trying to insert dspace item metadata with the following values:");
						throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
					}
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

				//Bitstream is not present in the database
				if(dbconnectionManager.checkDspaceBitStream(bitstreamId)==null)
				{
					bitstream = proxyCommunityManager.getBitStream(collectionId, itemId, bitstreamId);
					//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
					if(bitstream == null)
					{
						logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
						logger.info("Bitstream id: "+bitstreamId);
						throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
					}
					if(dbconnectionManager.addBitStream(communityId, collectionId, itemId, bitstreamId, bitstream.getName(), bitstream.getSize(), bitstream.getMimeType(), username)==FAILURE)
					{
						logger.info("The user "+username+" got this exception when trying to insert dspace bitstream metadata with the following values:");
						logger.info("Bitstream id: "+bitstreamId);
						throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
					}
				}

				//Add bitstream to workspace
				dbconnectionManager.addBitstreamToWorkspace(workspaceId, bitstreamId, username);
			}
		}
		catch(QuadrigaStorageException e)
		{
			logger.info("Class Name: DspaceManager");
			logger.info("Method Name: addBitStreamsToWorkspace");
			logger.info("Community id: "+communityId);
			logger.info("Collection id: "+collectionId);
			logger.info("Item id: "+itemId);
			logger.info("Bitstreams selected: "+bitstreamIds.length);
			logger.error("The logged exception is: ",e);
			throw e;
		}
		catch(QuadrigaAccessException e)
		{
			logger.info("Class Name: DspaceManager");
			logger.info("Method Name: addBitStreamsToWorkspace");
			logger.info("Community id: "+communityId);
			logger.info("Collection id: "+collectionId);
			logger.info("Item id: "+itemId);
			logger.info("Bitstreams selected: "+bitstreamIds.length);
			logger.error("The logged exception is: ",e);
			throw e;
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteBitstreamFromWorkspace(String workspaceid, String[] bitstreamids, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		try
		{
			for(String bitstreamid: bitstreamids)
				dbconnectionManager.deleteBitstreamFromWorkspace(workspaceid, bitstreamid, username);
		}
		catch(QuadrigaAccessException e)
		{
			logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
			logger.info("Class Name: DspaceManager");
			logger.info("Method Name: deleteBitstreamFromWorkspace");
			logger.info("Workspace id: "+workspaceid);
			logger.info("Bitstream selected: "+bitstreamids.length);
			logger.error("The logged exception is: ",e);
			throw e;
		}
	}



	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateDspaceMetadata(String workspaceid, String quadrigaUsername, String dspaceUsername, String password) throws QuadrigaAccessException, QuadrigaStorageException
	{

		HashSet<String> reloadedCollectionIds = new HashSet<String>();

		//Reload all the communities by making only one call to dspace
		proxyCommunityManager.getCommunity(null, false, restTemplate, dspaceProperties, dspaceUsername, password);
		for(IBitStream bitstream : dbconnectionManager.getBitStreamReferences(workspaceid, quadrigaUsername))
		{
			ICommunity community = proxyCommunityManager.getCommunity(bitstream.getCommunityid(), true, null, null, null,null);
			ICollection collection = null;

			if(reloadedCollectionIds.contains(bitstream.getCollectionid()))
			{
				//Collection has been reloaded already
				collection = proxyCommunityManager.getCollection(bitstream.getCollectionid(), true, null, null, null, null, null);
			}
			else
			{
				//This is the first call to reload the collection
				collection = proxyCommunityManager.getCollection(bitstream.getCollectionid(), false, restTemplate, dspaceProperties, dspaceUsername, password, bitstream.getCommunityid());
				reloadedCollectionIds.add(bitstream.getCollectionid());
			}

			IDspaceUpdateManager dspaceUpdateManager = new DspaceUpdateManager(this.dbconnectionManager.getDataSource(), community, collection, null, bitstream, quadrigaUsername);
			Thread bitstreamUpdateThread = new Thread(dspaceUpdateManager);
			bitstreamUpdateThread.start();
		}
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
						if (hostname.equals(dspaceProperties.getProperty("dspace_url").split("//")[1])) {
							return true;
						}
						return false;
					}
				});
	}

}
