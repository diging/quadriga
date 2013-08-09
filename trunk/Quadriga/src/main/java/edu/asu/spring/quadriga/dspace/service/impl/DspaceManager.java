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
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceUpdateManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 *	The purpose of the class is to make rest service calls to dspace
 * 	and fetch the communities, collections, items and bitstreams
 * 	This class manages the rest template, url, username and password.
 *  
 * 	@author Ram Kumar Kumaresan
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

	@Override
	public Properties getDspaceProperties() {
		return dspaceProperties;
	}

	@Override
	public void setDspaceProperties(Properties dspaceProperties) {
		this.dspaceProperties = dspaceProperties;
	}

	@Override
	public IDBConnectionDspaceManager getDbconnectionManager() {
		return dbconnectionManager;
	}

	@Override
	public void setDbconnectionManager(IDBConnectionDspaceManager dbconnectionManager) {
		this.dbconnectionManager = dbconnectionManager;
	}

	@Override
	public ICommunityManager getProxyCommunityManager() {
		return proxyCommunityManager;
	}

	@Override
	public void setProxyCommunityManager(ICommunityManager proxyCommunityManager) {
		this.proxyCommunityManager = proxyCommunityManager;
	}

	@Override
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	@Override
	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public String getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICommunity> getAllCommunities(String sUserName, String sPassword) {

		return getProxyCommunityManager().getAllCommunities(getRestTemplate(), getDspaceProperties(), sUserName, sPassword);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICollection> getAllCollections(String sUserName, String sPassword, String sCommunityId) {

		return getProxyCommunityManager().getAllCollections(getRestTemplate(), getDspaceProperties(), sUserName, sPassword, sCommunityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityName(String sCommunityId) 
	{
		return getProxyCommunityManager().getCommunityName(sCommunityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCollectionName(String sCollectionId) 
	{
		return getProxyCommunityManager().getCollectionName(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollection getCollection(String sCollectionId)
	{
		return getProxyCommunityManager().getCollection(sCollectionId,true,null,null,null,null,null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityId(String sCollectionId)
	{
		return getProxyCommunityManager().getCommunityId(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IItem> getAllItems(String sCollectionId) {
		return  getProxyCommunityManager().getAllItems(sCollectionId); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IBitStream> getAllBitStreams(String sUserName, String sPassword, String sCollectionId, String sItemId)
	{
		return getProxyCommunityManager().getAllBitStreams(sCollectionId, sItemId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getItemName(String sCollectionId, String sItemId)
	{
		return getProxyCommunityManager().getItemName(sCollectionId, sItemId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBitStream getBitStream(String sCollectionId, String sItemId, String sBitStreamId)
	{

		return getProxyCommunityManager().getBitStream(sCollectionId, sItemId, sBitStreamId);
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
			ICommunity community = getProxyCommunityManager().getCommunity(communityId,true,null,null,null,null);
			ICollection collection = getProxyCommunityManager().getCollection(collectionId,true,null,null,null,null,null);
			IItem item = getProxyCommunityManager().getItem(collectionId, itemId);

			//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
			if(community == null || collection == null || item == null)
			{
				logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
				throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
			}

			//Check the status of community, collection and item in the database
			String status = getDbconnectionManager().checkDspaceNodes(communityId, collectionId, itemId);
			if(status == null)
			{
				//Community, Collection and Item metadata does not exist. So add them to the database
				if(getDbconnectionManager().addCommunity(communityId, community.getName(), community.getShortDescription(), community.getIntroductoryText(), community.getHandle(), username) == FAILURE)
				{
					logger.info("The user "+username+" got this exception when trying to insert dspace community metadata with the following values:");
					throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
				}
				if(getDbconnectionManager().addCollection(communityId, collectionId, collection.getName(), collection.getShortDescription(), collection.getEntityReference(), collection.getHandle(), username)==FAILURE)
				{
					logger.info("The user "+username+" got this exception when trying to insert dspace collection metadata with the following values:");
					throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
				}
				if(getDbconnectionManager().addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username)==FAILURE)
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
					if(getDbconnectionManager().addCollection(communityId, collectionId, collection.getName(), collection.getShortDescription(), collection.getEntityReference(), collection.getHandle(), username)==FAILURE)
					{
						logger.info("The user "+username+" got this exception when trying to insert dspace collection metadata with the following values:");
						throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
					}
					if(getDbconnectionManager().addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username)==FAILURE)
					{
						logger.info("The user "+username+" got this exception when trying to insert dspace item metadata with the following values:");
						throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
					}

				}
				//Collection metadata is found in the database
				else if(status.equalsIgnoreCase(COLLECTON_EXISTS))
				{
					//Item metadata does not exist. Add the metadata to the database.
					if(getDbconnectionManager().addItem(communityId, collectionId, itemId, item.getName(), item.getHandle(), username)==FAILURE)
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
				if(getDbconnectionManager().checkDspaceBitStream(bitstreamId)==null)
				{
					bitstream = getProxyCommunityManager().getBitStream(collectionId, itemId, bitstreamId);
					//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
					if(bitstream == null)
					{
						logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
						logger.info("Bitstream id: "+bitstreamId);
						throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
					}
					if(getDbconnectionManager().addBitStream(communityId, collectionId, itemId, bitstreamId, bitstream.getName(), bitstream.getSize(), bitstream.getMimeType(), username)==FAILURE)
					{
						logger.info("The user "+username+" got this exception when trying to insert dspace bitstream metadata with the following values:");
						logger.info("Bitstream id: "+bitstreamId);
						throw new QuadrigaStorageException("OOPS ! There seems to be a problem in the database. We got our best minds working on it. Please check back later");
					}
				}

				//Add bitstream to workspace
				getDbconnectionManager().addBitstreamToWorkspace(workspaceId, bitstreamId, username);
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
				getDbconnectionManager().deleteBitstreamFromWorkspace(workspaceid, bitstreamid, username);
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
		getProxyCommunityManager().getCommunity(null, false, getRestTemplate(), getDspaceProperties(), dspaceUsername, password);
		for(IBitStream bitstream : getDbconnectionManager().getBitStreamReferences(workspaceid, quadrigaUsername))
		{
			ICommunity community = getProxyCommunityManager().getCommunity(bitstream.getCommunityid(), true, null, null, null,null);
			ICollection collection = null;

			if(reloadedCollectionIds.contains(bitstream.getCollectionid()))
			{
				//Collection has been reloaded already
				collection = getProxyCommunityManager().getCollection(bitstream.getCollectionid(), true, null, null, null, null, null);
			}
			else
			{
				//This is the first call to reload the collection
				collection = getProxyCommunityManager().getCollection(bitstream.getCollectionid(), false, getRestTemplate(), getDspaceProperties(), dspaceUsername, password, bitstream.getCommunityid());
				reloadedCollectionIds.add(bitstream.getCollectionid());
			}

			IDspaceUpdateManager dspaceUpdateManager = new DspaceUpdateManager(this.getDbconnectionManager().getDataSource(), community, collection, null, bitstream, quadrigaUsername);
			Thread bitstreamUpdateThread = new Thread(dspaceUpdateManager);
			bitstreamUpdateThread.start();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearCompleteCache()
	{
		getProxyCommunityManager().clearCompleteCache();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDspaceKeys getDspaceKeys(String username) throws QuadrigaStorageException
	{
		return dbconnectionManager.getDspaceKeys(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IDspaceKeys getMaskedDspaceKeys(String username) throws QuadrigaStorageException
	{
		IDspaceKeys dspaceKeys = dbconnectionManager.getDspaceKeys(username);
		if(dspaceKeys != null)
		{			
			StringBuilder maskedPublicKey = new StringBuilder();
			StringBuilder maskedPrivateKey = new StringBuilder();

			if(dspaceKeys.getPublicKey().length()>4)
			{
				for(int i=0;i<dspaceKeys.getPublicKey().length()-4;i++)
				{
					maskedPublicKey.append('x');
				}
				maskedPublicKey.append(dspaceKeys.getPublicKey().subSequence(dspaceKeys.getPublicKey().length()-4, dspaceKeys.getPublicKey().length()));
			}
			else
			{
				maskedPublicKey.append(dspaceKeys.getPublicKey());
			}

			if(dspaceKeys.getPrivateKey().length()>4)
			{
				for(int i=0;i<dspaceKeys.getPrivateKey().length()-4;i++)
				{
					maskedPrivateKey.append('x');
				}
				maskedPrivateKey.append(dspaceKeys.getPrivateKey().subSequence(dspaceKeys.getPrivateKey().length()-4, dspaceKeys.getPrivateKey().length()));
			}
			else
			{
				maskedPrivateKey.append(dspaceKeys.getPrivateKey());
			}

			dspaceKeys.setPublicKey(maskedPublicKey.toString());
			dspaceKeys.setPrivateKey(maskedPrivateKey.toString());
		}
		return dspaceKeys;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addDspaceKeys(IDspaceKeys dspaceKeys, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		IDspaceKeys dbDspaceKeys = dbconnectionManager.getDspaceKeys(username);
		if(dbDspaceKeys == null)
		{
			//No key was found for the user. Hence the keys will be inserted.
			if(dbconnectionManager.addDspaceKeys(dspaceKeys, username)==FAILURE)
			{
				logger.info("The user "+username+" got this exception when trying to insert dspace keys with the following values:");
				if(dspaceKeys != null)
				{
					logger.info("Public Key: "+dspaceKeys.getPublicKey());
					logger.info("Private Key: "+dspaceKeys.getPrivateKey());
				}
				throw new QuadrigaAccessException("OOPS ! There seems to be a problem in the system. We got our best minds working on it. Please check back later");
			}
		}
		else
		{
			//Key already exists in database. Update the existing key.
			if(dbconnectionManager.updateDspaceKeys(dspaceKeys, username)==FAILURE)
			{
				throw new QuadrigaAccessException("The values passed to updateDspaceKeys do not satify the method constraints !");
			}
		}
		return SUCCESS;
	}


	/**
	 * This method is used to load the Dspace server certificate during the start of the application.
	 * It also overloads the verify method of the hostname verifier to always return TRUE for the dspace hostname.
	 * It will be invoked only once.
	 */
	public void start()
	{
		logger.info("The certificate filepath used is: "+getFilePath());
		System.setProperty("javax.net.ssl.trustStore", getFilePath());


		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier(){

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals(getDspaceProperties().getProperty("dspace_url").split("//")[1])) {
							return true;
						}
						return false;
					}
				});
	}

}
