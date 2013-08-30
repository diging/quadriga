package edu.asu.spring.quadriga.dspace.service.impl;


import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
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
	
	@Autowired
	private IBitStreamFactory bitstreamFactory;

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
	 * 
	 */
	@Override
	public List<ICommunity> getAllCommunities(IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaException {

		try {
			return getProxyCommunityManager().getAllCommunities(getRestTemplate(), getDspaceProperties(), dspaceKeys, sUserName, sPassword);
		} catch (NoSuchAlgorithmException e) {
			throw new QuadrigaException("Error in Dspace Access. We got our best minds working on it. Please check back later");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICollection> getAllCollections(IDspaceKeys dspaceKeys, String sUserName, String sPassword, String sCommunityId) {

		return getProxyCommunityManager().getAllCollections(getRestTemplate(), getDspaceProperties(), dspaceKeys, sUserName, sPassword, sCommunityId);
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
	 * 
	 */
	@Override
	public ICollection getCollection(String sCollectionId) throws QuadrigaException
	{
		try {
			return getProxyCommunityManager().getCollection(sCollectionId,true,null,null,null,null,null,null);
		} catch (NoSuchAlgorithmException e) {
			throw new QuadrigaException("Error in Dspace Access. We got our best minds working on it. Please check back later");
		}
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
	public List<IBitStream> getAllBitStreams(IDspaceKeys dspaceKeys, String sUserName, String sPassword, String sCollectionId, String sItemId)
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
	 * 
	 */
	@Override
	public void addBitStreamsToWorkspace(String workspaceId, String communityId, String collectionId, String itemId, String[] bitstreamIds, String username) throws QuadrigaStorageException, QuadrigaAccessException, QuadrigaException
	{

		try
		{
			//Passing null values for other arguments because the community details must have already been fetched from dspace
			ICommunity community = getProxyCommunityManager().getCommunity(communityId,true,null,null,null,null,null);
			ICollection collection = getProxyCommunityManager().getCollection(collectionId,true,null,null,null,null,null,null);
			IItem item = getProxyCommunityManager().getItem(collectionId, itemId);

			//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
			if(community == null || collection == null || item == null)
			{
				logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
				throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
			}

			/**	The supplied community, collection and item are valid.
			 *  Check if each supplied bitstream is valid.
			 */
			for(String bitstreamId: bitstreamIds)
			{			
				IBitStream bitstream = getProxyCommunityManager().getBitStream(collectionId, itemId, bitstreamId);;
				//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
				if(bitstream == null)
				{
					logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
					logger.info("Bitstream id: "+bitstreamId);
					throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
				}

				//Add bitstream to workspace. For security, the id's are gotten from Dspace Objects.
				dbconnectionManager.addBitstreamToWorkspace(workspaceId, community.getId(), collection.getId(), item.getId(), bitstream.getId(), username);
			}
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
		} catch (NoSuchAlgorithmException e) {
			throw new QuadrigaException("Error in Dspace Access. We got our best minds working on it. Please check back later");
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteBitstreamFromWorkspace(String workspaceid, String[] bitstreamids, List<IBitStream> workspaceBitStreams, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		try
		{	
			//Check if the selected bitstream ids belong to the authorized list of bitstreams.
			List<String> authorizedBitStreamsForDelete = new ArrayList<String>();
			for(String bitstreamid: bitstreamids)
			{
				for(IBitStream workspaceBitStream: workspaceBitStreams)
				{
					if(workspaceBitStream.getId()!=null)
						if(workspaceBitStream.getId().equals(bitstreamid))
						{
							if(!(workspaceBitStream.getName().equals(dspaceProperties.getProperty("restricted_bitstream")) && workspaceBitStream.getName().equals(dspaceProperties.getProperty("access_check_bitstream"))))
							{
								authorizedBitStreamsForDelete.add(bitstreamid);
								break;
							}
						}
				}
			}

			//Delete all the authorized bitstreams for the user from the workspace
			for(String bitstreamid: authorizedBitStreamsForDelete)
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
		proxyCommunityManager.clearCompleteCache();
		return SUCCESS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteDspaceKeys(String username) throws QuadrigaStorageException
	{
		return dbconnectionManager.deleteDspaceKeys(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IBitStream> checkDspaceBitstreamAccess(List<IBitStream> bitstreams, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaException
	{
		List<IBitStream> checkedBitStreams = new ArrayList<IBitStream>();

		//User tries to access workspace without Dspace Login Credentials
		if(dspaceKeys == null && (sUserName == null || sPassword == null))
		{
			IBitStream restrictedBitStream = bitstreamFactory.createBitStreamObject();
			restrictedBitStream.setCommunityName(dspaceProperties.getProperty("need_authentication"));
			restrictedBitStream.setCollectionName(dspaceProperties.getProperty("need_authentication"));
			restrictedBitStream.setItemName(dspaceProperties.getProperty("need_authentication"));
			restrictedBitStream.setName(dspaceProperties.getProperty("need_authentication"));

			//Created Empty Bitstream data
			for(int i=0;i<bitstreams.size();i++)
			{
				checkedBitStreams.add(restrictedBitStream);
			}
			return checkedBitStreams;
		}

		IBitStream restrictedBitStream = bitstreamFactory.createBitStreamObject();
		restrictedBitStream.setCommunityName(dspaceProperties.getProperty("restricted_community"));
		restrictedBitStream.setCollectionName(dspaceProperties.getProperty("restricted_collection"));
		restrictedBitStream.setItemName(dspaceProperties.getProperty("restricted_item"));
		restrictedBitStream.setName(dspaceProperties.getProperty("restricted_bitstream"));
		
		try
		{
			for(IBitStream bitstream: bitstreams)
			{
				boolean isloading = false;
				IBitStream loadingBitStream = null;

				//Check access rights for community
				ICommunity community = proxyCommunityManager.getCommunity(bitstream.getCommunityid(), true, restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword);
				if(community != null)
				{
					//The user can access the community
					//Check access rights for collection
					isloading = true;
					loadingBitStream = bitstreamFactory.createBitStreamObject();

					loadingBitStream.setId(bitstream.getId());
					loadingBitStream.setCommunityid(bitstream.getCommunityid());
					loadingBitStream.setCollectionid(bitstream.getCollectionid());
					loadingBitStream.setItemid(bitstream.getItemid());
					loadingBitStream.setCommunityName(community.getName());

					ICollection collection = proxyCommunityManager.getCollection(bitstream.getCollectionid(), true, restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword, community.getId());
					if(collection!=null)
					{
						if(collection.getName()!=null)
						{
							//User has access to the collection and the collection is loaded from dspace.
							loadingBitStream.setCollectionName(collection.getName());
							//Check access rights for item
							IItem item = proxyCommunityManager.getItem(collection.getId(), bitstream.getItemid());
							if(item != null)
							{
								if(item.getName()!=null)
								{
									//User has access to the item and the item is loaded from dspace.
									loadingBitStream.setItemName(item.getName());
									//Check access rights for bitstream
									IBitStream dspaceBitstream = proxyCommunityManager.getBitStream(collection.getId(), item.getId(), bitstream.getId());
									if(dspaceBitstream != null)
									{
										if(dspaceBitstream.getName() != null)
										{
											//User has access to the bitstream and the bitstream is loaded from dspace.
											loadingBitStream.setName(dspaceBitstream.getName());
											checkedBitStreams.add(loadingBitStream);
											isloading = false;
										}
									}
								} //End of if item name is null	
							} //End of if item is null
						} //End of if collection name is null
					} //End of if collection is null
				}
				else
				{
					//User does not have access to the community
					checkedBitStreams.add(restrictedBitStream);
					isloading = false;
				}

				// The complete hierarchy is yet to be loaded.
				if(isloading)
				{
					/*
					 * The collection/item/bitstream data is to be loaded from dspace. 
					 * Separate threads are still working on loading them.
					 */
					loadingBitStream.setCollectionName(dspaceProperties.getProperty("access_check_collection"));
					loadingBitStream.setItemName(dspaceProperties.getProperty("access_check_item"));
					loadingBitStream.setName(dspaceProperties.getProperty("access_check_bitstream"));

					checkedBitStreams.add(loadingBitStream);
				}
			} //End of for each loop for bitstream
		} catch (NoSuchAlgorithmException e) {
			throw new QuadrigaException("Error in Dspace Access. We got our best minds working on it. Please check back later");
		}
		catch(HttpServerErrorException e)
		{
			/* Dspace can't be accessed using the supplied public and private key.
			 * This exception happens when long random strings are provided for public/private keys.
			 */
			checkedBitStreams.clear();
			for(int i=0;i<bitstreams.size();i++)
			{
				checkedBitStreams.add(restrictedBitStream);
			}
		}		
		return checkedBitStreams;		
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
