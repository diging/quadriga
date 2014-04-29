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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionDspaceManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.dspace.ICollection;
import edu.asu.spring.quadriga.domain.dspace.ICommunity;
import edu.asu.spring.quadriga.domain.dspace.IItem;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
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

	@Resource(name = "uiMessages")
	private Properties dspaceMessages;

	private static final Logger logger = LoggerFactory
			.getLogger(DspaceManager.class);

	@Autowired
	private IDBConnectionListWSManager dbconnectListWSManager;

	@Override
	public Properties getDspaceMessages() {
		return dspaceMessages;
	}

	@Override
	public void setDspaceMessages(Properties dspaceMessages) {
		this.dspaceMessages = dspaceMessages;
	}

	@Override
	public IBitStreamFactory getBitstreamFactory() {
		return bitstreamFactory;
	}

	@Override
	public void setBitstreamFactory(IBitStreamFactory bitstreamFactory) {
		this.bitstreamFactory = bitstreamFactory;
	}

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICommunity> getAllCommunities(IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaException, QuadrigaAccessException {

		try {
			return getProxyCommunityManager().getAllCommunities(getRestTemplate(), getDspaceProperties(), dspaceKeys, sUserName, sPassword);
		}
		catch(ResourceAccessException e)
		{
			logger.error("Dspace is not accessible. Check the url used and if dspace is up !");
			throw new QuadrigaAccessException("Quadriga is having trouble accessing Dspace. Please check back later.");
		}
		catch(HttpClientErrorException e)
		{
			logger.error("User "+sUserName+" tried to log in to dspace with wrong credentials !");
			throw new QuadrigaAccessException("Wrong Dspace Login Credentials !");
		}
		catch(HttpServerErrorException e)
		{
			logger.info("The dspace server is down !");
			throw new QuadrigaAccessException("The dspace server is down ! Please check back later.");
		}		
		catch (NoSuchAlgorithmException e) {
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
	 * @throws QuadrigaAccessException 
	 * 
	 */
	@Override
	public ICollection getCollection(String sCollectionId, String sCommunityId) throws QuadrigaException, QuadrigaAccessException
	{
		try {
			return getProxyCommunityManager().getCollection(sCollectionId,true,this.restTemplate, this.dspaceProperties,null,null,null,sCommunityId);
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
	public ICommunity getCommunity(IDspaceKeys dspaceKeys, String username, String password,boolean fromCache, String communityid ) throws NoSuchAlgorithmException, QuadrigaAccessException
	{
		return getProxyCommunityManager().getCommunity(communityid, true, restTemplate, dspaceProperties, dspaceKeys, username, password);
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
	 */
	@Override
	@Transactional
	public List<IBitStream> getBitstreamsInWorkspace(String workspaceid, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		return dbconnectListWSManager.getBitStreams(workspaceid, username);
	}


	/**
	 * {@inheritDoc}
	 * @throws QuadrigaAccessException 
	 * 
	 */
	@Override
	@Transactional
	public void addBitStreamsToWorkspace(String workspaceId, String communityId, String collectionId, String itemId, String[] bitstreamIds, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		try
		{
			//Passing null values for other arguments because the community details must have already been fetched from dspace
			IItem item = getProxyCommunityManager().getItem(collectionId, itemId);

			//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
			if(item == null)
			{
				logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
				throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
			}

			/**	The supplied item is valid.
			 *  Check if each supplied bitstream is valid.
			 */
			for(String bitstreamId: bitstreamIds)
			{			
				IBitStream bitstream = getProxyCommunityManager().getBitStream(collectionId, itemId, bitstreamId);;
				//Cache the bitstream
				getProxyCommunityManager().addBitStreamToCache(bitstream);

				//Catch the Wrong or Illegal ids provided by the user. This will never happen through the system UI.
				if(bitstream == null)
				{
					logger.info("The user "+username+" tried to hack into the dspace system with the following values:");
					logger.info("Bitstream id: "+bitstreamId);
					throw new QuadrigaAccessException("This action has been logged. Please don't try to hack into the system !!!");
				}

				//Add bitstream to workspace. For security, the id's are gotten from Dspace Objects.
				dbconnectionManager.addBitstreamToWorkspace(workspaceId,  bitstream.getId(), item.getHandle(), username);
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
		}
		catch(HttpClientErrorException e)
		{
			logger.error("User "+username+" tried to log in to dspace with wrong credentials !");
			throw new QuadrigaAccessException("Wrong Dspace Login Credentials !");
		}
		catch(HttpServerErrorException e)
		{
			logger.info("The dspace server is down !");
			throw new QuadrigaAccessException("The dspace server is down ! Please check back later.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void addBitStreamsToWorkspaceThroughRestInterface(String workspaceId, String bitstreamId, String itemHandle, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		dbconnectionManager.addBitstreamToWorkspace(workspaceId, bitstreamId, itemHandle, username);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void deleteBitstreamFromWorkspace(String workspaceid, String[] bitstreamids, List<IWorkspaceBitStream> workspaceBitStreams, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		try
		{	
			//Check if the selected bitstream ids belong to the authorized list of bitstreams.
			List<String> authorizedBitStreamsForDelete = new ArrayList<String>();
			if(bitstreamids==null || workspaceBitStreams == null || workspaceid == null || username == null)
			{
				throw new QuadrigaAccessException("Error in deleting the bitstreams. Please check back later.");
			}

			for(String bitstreamid: bitstreamids)
			{
				for(IWorkspaceBitStream workspaceBitStream: workspaceBitStreams)
				{
					if(workspaceBitStream.getBitStream().getId()!=null)
						if(workspaceBitStream.getBitStream().getId().equals(bitstreamid))
						{
							if(!(workspaceBitStream.getBitStream().getName().equals(getDspaceMessages().getProperty("dspace.restricted_bitstream")) && workspaceBitStream.getBitStream().getName().equals(getDspaceMessages().getProperty("dspace.access_check_bitstream"))))
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
	@Transactional
	public IDspaceKeys getDspaceKeys(String username) throws QuadrigaStorageException
	{
		return dbconnectionManager.getDspaceKeys(username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
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
	@Transactional
	public int addDspaceKeys(IDspaceKeys dspaceKeys, String username) throws QuadrigaStorageException, QuadrigaAccessException
	{
		/*IDspaceKeys dbDspaceKeys = dbconnectionManager.getDspaceKeys(username);
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
		return SUCCESS;*/
		return dbconnectionManager.saveOrUpdateDspaceKeys(dspaceKeys, username);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public int deleteDspaceKeys(String username) throws QuadrigaStorageException
	{
		return dbconnectionManager.deleteDspaceKeys(username);
	}

	/**
	 * {@inheritDoc}
	 * @throws QuadrigaAccessException 
	 * @throws QuadrigaStorageException 
	 */
	@Override
	@Transactional
	public List<IWorkspaceBitStream> checkDspaceBitstreamAccess(List<IWorkspaceBitStream> workspaceBitstreams, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaStorageException
	{
		List<IWorkspaceBitStream> checkedBitStreams = null;

			if(workspaceBitstreams!=null){
				for(IWorkspaceBitStream workspaceBitstream: workspaceBitstreams)
				{
					//Check if the bitstream was already loaded.
					IBitStream bitstreamFromDspace = proxyCommunityManager.getBitStream(workspaceBitstream.getBitStream().getId(), restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword);
					if(bitstreamFromDspace != null)
					{
						if(bitstreamFromDspace.getName() == null)
						{
							if(bitstreamFromDspace.getLoadStatus() == false)
							{
								//Rest call is not yet serviced
								bitstreamFromDspace.setName(getDspaceMessages().getProperty("dspace.access_check_bitstream"));
								bitstreamFromDspace.setItemName(getDspaceMessages().getProperty("dspace.access_check_item"));
							}
							else
							{
								//Rest call was serviced and the action threw an exception 
								bitstreamFromDspace.setName(getDspaceMessages().getProperty("dspace.restricted_bitstream"));
								bitstreamFromDspace.setItemName(getDspaceMessages().getProperty("dspace.restricted_item"));
							}
							if(checkedBitStreams == null){
								checkedBitStreams = new ArrayList<IWorkspaceBitStream>();
							}
							checkedBitStreams.add(workspaceBitstream);
						}
						else
						{
							if(checkedBitStreams == null){
								checkedBitStreams = new ArrayList<IWorkspaceBitStream>();
							}
							checkedBitStreams.add(workspaceBitstream);
						}						
					}
				} //End of for each loop for bitstream
			}

		return checkedBitStreams;		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public boolean validateDspaceCredentials(String username, String password, IDspaceKeys dspacekeys)
	{
		try
		{
			return proxyCommunityManager.validateDspaceCredentials(restTemplate, dspaceProperties, dspacekeys, username, password);
		}
		catch(HttpClientErrorException e)
		{
			/* 
			 * This exception happens for wrong username and password.
			 * Also thrown for wrong public/private key 
			 */
			return false;

		}
		catch(Exception e)
		{

		}
		return true;		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public IBitStream getWorkspaceItems(String fileid, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaStorageException
	{
		//Get the bitstream and load it into the cache before returning it.
		IBitStream bitstream = proxyCommunityManager.getBitStream(fileid, restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword);
		return bitstream;
	}
	
	@Override
	public IDspaceMetadataItemEntity getItemMetadata(String fileid, String sUserName, String sPassword, IDspaceKeys dspaceKeys) throws NoSuchAlgorithmException, QuadrigaStorageException
	{
		return proxyCommunityManager.getItemMetadata(restTemplate, dspaceProperties, fileid, sUserName, sPassword, dspaceKeys);		
	}

}
