package edu.asu.spring.quadriga.dspace.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.dspace.ICollection;
import edu.asu.spring.quadriga.domain.dspace.ICommunity;
import edu.asu.spring.quadriga.domain.dspace.IItem;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.ICollectionFactory;
import edu.asu.spring.quadriga.domain.impl.dspace.BitStream;
import edu.asu.spring.quadriga.domain.impl.dspace.Community;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunities;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBitStream;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataBundleEntity;
import edu.asu.spring.quadriga.dspace.service.IDspaceMetadataItemEntity;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * The purpose of the class is to implement proxy pattern for the community class
 * that is to be fetched from dspace. This class is responsbile for the  load of Dspace communities and collections.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service("communityManager")
@Scope(value="session", proxyMode= ScopedProxyMode.INTERFACES)
public class ProxyCommunityManager implements ICommunityManager {

	private List<ICommunity> communities;
	private List<ICollection> collections;
	private List<IBitStream> workspaceBitStreamList;

	@Autowired
	private ICollectionFactory collectionFactory;

	@Override
	public ICollectionFactory getCollectionFactory() {
		return collectionFactory;
	}


	@Override
	public void setCollectionFactory(ICollectionFactory collectionFactory) {
		this.collectionFactory = collectionFactory;
	}


	/**
	 * Used to generate the corresponding url necessary to access the community details
	 * @return			Return the complete REST service url along with all the authentication information
	 * @throws NoSuchAlgorithmException 
	 */
	private String getCompleteUrlPath(Properties dspaceProperties, IDspaceKeys dspaceKeys, String userName, String password) throws NoSuchAlgorithmException
	{
		if(dspaceKeys != null)
		{
			String stringToHash = dspaceProperties.getProperty("dspace.all_community_url") + dspaceKeys.getPrivateKey();
			MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("dspace.algorithm"));
			messageDigest.update(stringToHash.getBytes());
			String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

			return dspaceProperties.getProperty("dspace.dspace_url")+
					dspaceProperties.getProperty("dspace.all_community_url")+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.api_key")+dspaceKeys.getPublicKey()+
					dspaceProperties.getProperty("dspace.&")+dspaceProperties.getProperty("dspace.api_digest")+digestKey;

		}
		else if(userName != null && password != null && !userName.equals("") && !password.equals(""))
		{
			return dspaceProperties.getProperty("dspace.dspace_url")+
					dspaceProperties.getProperty("dspace.all_community_url")+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.email")+userName+
					dspaceProperties.getProperty("dspace.&")+dspaceProperties.getProperty("dspace.password")+password;
		}
		else
		{
			//No username+password and dspacekeys are used. So use public access
			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.all_community_url");
		}
	}

	@Override
	public void addBitStreamToCache(IBitStream bitstream)
	{
		//Check if the list was created
		if(workspaceBitStreamList == null)
		{
			workspaceBitStreamList = new ArrayList<IBitStream>();
		}

		//Check for the file in the existing list
		for(IBitStream workspaceBitStream: workspaceBitStreamList)
		{
			if(workspaceBitStream.getId().equals(bitstream.getId()))
				return;
		}
		
		//File not in the cache, so add it.
		workspaceBitStreamList.add(bitstream);
	}

	@Override
	public IBitStream getBitStream(String fileid, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaStorageException
	{
		IBitStream bitstream = null;

		//Check if the list was created
		if(workspaceBitStreamList == null)
		{
			workspaceBitStreamList = new ArrayList<IBitStream>();
		}


		//Check for the file in the existing list
		for(IBitStream workspaceBitStream: workspaceBitStreamList)
		{
			if(workspaceBitStream.getId().equals(fileid))
				bitstream = workspaceBitStream;
		}

		//File not found in the existing list. So make a REST call to the Dspace.
		if(bitstream == null)
		{
			//Create a bitstream object that will load its metadata information
			bitstream = new BitStream(null,null,null,fileid, restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword);
			Thread bitstreamThread = new Thread(bitstream);
			bitstreamThread.start();

			//Add the bitstream to the list of bitstreams that the user has access
			workspaceBitStreamList.add(bitstream);
		}
		return bitstream;
	}

	public boolean hasAccessToBitStream(String fileid, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws QuadrigaStorageException
	{
		//Check for the file in the existing list
		for(IBitStream workspaceBitStream: workspaceBitStreamList)
		{
			if(workspaceBitStream.getId().equals(fileid))
				return true;
		}

		//Try to make a rest service call to the bitstream
		try {
			String restURLPath = getBitstreamMetadataPath(dspaceProperties, fileid, sUserName, sPassword, dspaceKeys);
			IDspaceMetadataBitStream metadataBitstream = (IDspaceMetadataBitStream) restTemplate.getForObject(restURLPath, DspaceMetadataBitStream.class);

		} catch (Exception e) {
			//Any exception occurred - Can be due to unauthorized access or dspace is down
			return false;
		} 

		return false;

	}

	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<ICommunity> getAllCommunities(RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws NoSuchAlgorithmException {
		if(communities == null)
		{
			String sRestServicePath = getCompleteUrlPath(dspaceProperties, dspaceKeys, sUserName, sPassword);
			IDspaceCommunities dsapceCommunities = null;

			dsapceCommunities = (DspaceCommunities)restTemplate.getForObject(sRestServicePath, DspaceCommunities.class);

			if(dsapceCommunities.getCommunities().size()>0)
			{
				communities = new ArrayList<ICommunity>();

				//Initialize collection specific objects
				collections = new ArrayList<ICollection>();

				ICommunity community = null;
				for(IDspaceCommunity dspaceCommunity: dsapceCommunities.getCommunities())
				{			
					community = new Community();

					//If the data was successfully transferred from Dspace representation to the one used in Quadriga
					if(community.copy(dspaceCommunity))
					{						
						communities.add(community);
					}
				}
			}
		}
		return communities;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICollection> getAllCollections(RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword, String sCommunityId) {

		if(communities != null)
		{
			ICollection collection = null;
			for(ICommunity community: communities)
			{
				if(community.getId().equals(sCommunityId))
				{
					//This is the first time a request for collections has been made for this community
					if(community.getCollections().size() == 0)
					{
						for(String collectionId :community.getCollectionIds()){
							collection = getCollectionFactory().createCollectionObject(collectionId, sCommunityId, restTemplate,dspaceProperties,dspaceKeys,sUserName,sPassword);
							Thread collectionThread = new Thread(collection);
							collectionThread.start();

							this.collections.add(collection);
							community.addCollection(collection);
						}
					}
					return community.getCollections();
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IItem> getAllItems(String sCollectionId)
	{
		//Check if a request for collections has been made to Dspace
		if(this.collections != null)
		{
			for(ICollection collection : this.collections)
			{
				if(collection.getId().equals(sCollectionId))
				{
					return collection.getItems();
				}
			}
		}
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollection getCollection(String sCollectionId, boolean fromCache, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword, String communityid) throws NoSuchAlgorithmException, QuadrigaAccessException
	{
		if(fromCache)
		{
			this.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword, communityid);
		}
		else
		{
			//Get the communit object to which this collection is associated
			ICommunity community = this.getCommunity(communityid, true, null, null, null, null, null);

			//Remove all the collection metadata from the cache
			community.clearCollections();
			for(String collectionid: community.getCollectionIds()){
				Iterator<ICollection> iterator = this.collections.iterator();
				while(iterator.hasNext())
				{
					if(iterator.next().getId().equals(collectionid))
						iterator.remove();
				}
			}

			//Load the collection metadata associated with the community
			this.getAllCollections(restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword, communityid);
		}

		for(ICollection collection : this.collections)
		{
			if(collection.getId().equals(sCollectionId))
			{
				return collection;
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityName(String sCommunityId) 
	{
		if(this.communities!=null)
		{
			for(ICommunity community: communities)
			{
				if(community.getId().equals(sCommunityId))
				{
					return community.getName();
				}
			}
		}
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCollectionName(String sCollectionId) 
	{
		//Check if a request for collections has been made to Dspace
		if(this.collections != null)
		{
			for(ICollection collection : this.collections)
			{
				if(collection.getId().equals(sCollectionId))
				{
					return collection.getName();
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityId(String sCollectionId) 
	{
		//Check if a request for communities has been made to Dspace
		if(this.communities!=null)
		{
			for(ICommunity community: communities)
			{
				if(community.getCollectionIds().contains(sCollectionId))
					return community.getId();
			}
		}
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getItemName(String sCollectionId, String sItemId)
	{
		//Check if a request for communities has been made to Dspace
		if(this.collections!=null)
		{
			for(ICollection collection: collections)
			{
				if(collection.getId().equals(sCollectionId))
				{
					for(IItem item: collection.getItems())
					{
						if(item.getId().equals(sItemId))
						{
							return item.getName();
						}
					}
				}
			}
		}

		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBitStream getBitStream(String sCollectionId, String sItemId, String sBitStreamId)
	{
		//Check if a request for communities has been made to Dspace
		if(this.collections!=null)
		{
			for(ICollection collection: collections)
			{
				if(collection.getId().equals(sCollectionId))
				{
					for(IItem item: collection.getItems())
					{
						if(item.getId().equals(sItemId))
						{
							for(IBitStream bitstream: item.getBitstreams())
							{
								if(bitstream.getId().equals(sBitStreamId))
								{
									return bitstream;
								}
							}
						}
					}
				}
			}
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IBitStream> getAllBitStreams(String sCollectionId, String sItemId)
	{
		//Check if a request for communities has been made to Dspace
		if(this.collections!=null)
		{
			for(ICollection collection: collections)
			{
				if(collection.getId().equals(sCollectionId))
				{
					for(IItem item: collection.getItems())
					{
						if(item.getId().equals(sItemId))
						{
							return item.getBitstreams();
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICommunity getCommunity(String communityId, boolean fromCache, RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws NoSuchAlgorithmException, QuadrigaAccessException
	{		
		//Get the community data from the cache
		if(fromCache)
		{
			if(this.communities != null)
			{
				for(ICommunity community: this.communities)
				{
					if(community.getId().equals(communityId))
						return community;
				}
			}
			else
			{
				//Load all the communities
				this.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword);
			}
		}
		else
		{
			//Reload the community data from dspace
			this.communities = null;
			this.getAllCommunities(restTemplate, dspaceProperties, dspaceKeys, sUserName, sPassword);
		}

		for(ICommunity community: this.communities)
		{
			if(community.getId().equals(communityId))
				return community;
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IItem getItem(String collectionId, String itemId)
	{
		//Check if a request for communities has been made to Dspace
		if(this.collections!=null)
		{
			for(ICollection collection: collections)
			{
				if(collection.getId().equals(collectionId))
				{
					for(IItem item: collection.getItems())
					{
						if(item.getId().equals(itemId))
						{
							return item;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unused")
	public boolean validateDspaceCredentials(RestTemplate restTemplate, Properties dspaceProperties, IDspaceKeys dspaceKeys, String sUserName, String sPassword) throws NoSuchAlgorithmException {
		String sRestServicePath = getCompleteUrlPath(dspaceProperties, dspaceKeys, sUserName, sPassword);
		IDspaceCommunities dsapceCommunities = (DspaceCommunities)restTemplate.getForObject(sRestServicePath, DspaceCommunities.class);
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearCompleteCache()
	{
		this.communities = null;
		this.collections = null;	
		this.workspaceBitStreamList = null;
	}



	private String bytesToHex(byte[] b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}

	private String getBitstreamMetadataPath(Properties dspaceProperties, String fileid, String email, String password, IDspaceKeys dspaceKeys) throws NoSuchAlgorithmException, QuadrigaStorageException
	{
		if(dspaceKeys != null)
		{
			if(dspaceKeys.getPublicKey()!=null && dspaceKeys.getPrivateKey()!=null && !dspaceKeys.getPublicKey().equals("") && !dspaceKeys.getPrivateKey().equals(""))
			{
				//Authenticate based on public and private key
				String stringToHash = dspaceProperties.getProperty("dspace.bitstream_url") + fileid + dspaceProperties.getProperty(".xml") + dspaceKeys.getPrivateKey();
				MessageDigest messageDigest = MessageDigest.getInstance(dspaceProperties.getProperty("dspace.algorithm"));
				messageDigest.update(stringToHash.getBytes());
				String digestKey = bytesToHex(messageDigest.digest()).substring(0, 8);

				return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml")+dspaceProperties.getProperty("dspace.?")+
						dspaceProperties.getProperty("dspace.api_key")+dspaceKeys.getPublicKey()+dspaceProperties.getProperty("dspace.&")+
						dspaceProperties.getProperty("dspace.api_digest")+digestKey;
			}
		}
		else if(email!=null && password!=null && !email.equals("") && !password.equals(""))
		{				
			//Authenticate based on email and password
			return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml")+dspaceProperties.getProperty("dspace.?")+
					dspaceProperties.getProperty("dspace.email")+email+
					dspaceProperties.getProperty("dspace.&")+dspaceProperties.getProperty("dspace.password")+password;
		}

		//No authentication provided
		return dspaceProperties.getProperty("dspace.dspace_url")+dspaceProperties.getProperty("dspace.bitstream_url")+fileid+dspaceProperties.getProperty("dspace.xml");
	}
	
	@Override
	public IDspaceMetadataItemEntity getItemMetadata(RestTemplate restTemplate, Properties dspaceProperties, String fileid, String email, String password, IDspaceKeys dspaceKeys) throws NoSuchAlgorithmException, QuadrigaStorageException
	{
		String restURLPath = getBitstreamMetadataPath(dspaceProperties, fileid, email, password, dspaceKeys);
		IDspaceMetadataBitStream metadataBitstream = (IDspaceMetadataBitStream)restTemplate.getForObject(restURLPath, DspaceMetadataBitStream.class);
		IDspaceMetadataItemEntity itemEntry = null;

		
		// get item handle for bitstream
		if (metadataBitstream.getBundles() != null && metadataBitstream.getBundles().getBundleEntity() != null) {
			IDspaceMetadataBundleEntity entry = metadataBitstream.getBundles().getBundleEntity();
			if (entry != null && entry.getItems() != null && entry.getItems().getItementities() != null) {
				if (entry.getItems().getItementities().size() > 0) {
					// we only get the first item, this might be wrong and needs to be checked.
					itemEntry = entry.getItems().getItementities().get(0);
				}
			}
		}
		
		return itemEntry;
	}

}
