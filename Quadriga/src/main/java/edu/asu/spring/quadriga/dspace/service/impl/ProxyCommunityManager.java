package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.implementation.Collection;
import edu.asu.spring.quadriga.domain.implementation.Community;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunities;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;

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

	/**
	 * Used to generate the corresponding url necessary to access the community details
	 * @return			Return the complete REST service url along with all the authentication information
	 */
	private String getCompleteUrlPath( Properties dspaceProperties, String userName, String password)
	{
		return dspaceProperties.getProperty("https")+dspaceProperties.getProperty("dspace_url")+
				dspaceProperties.getProperty("all_community_url")+dspaceProperties.getProperty("?")+
				dspaceProperties.getProperty("email")+userName+
				dspaceProperties.getProperty("&")+dspaceProperties.getProperty("password")+password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICommunity> getAllCommunities(RestTemplate restTemplate, Properties dspaceProperties, String sUserName, String sPassword) {
		if(communities == null)
		{
			String sRestServicePath = getCompleteUrlPath(dspaceProperties, sUserName, sPassword);
			IDspaceCommunities dsapceCommunities = (DspaceCommunities)restTemplate.getForObject(sRestServicePath, DspaceCommunities.class);

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
	public List<ICollection> getAllCollections(RestTemplate restTemplate, Properties dspaceProperties, String sUserName, String sPassword, String sCommunityId) {

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
							collection = new Collection(collectionId,restTemplate,dspaceProperties,sUserName,sPassword);
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
	public ICollection getCollection(String sCollectionId, boolean fromCache, RestTemplate restTemplate, Properties dspaceProperties, String sUserName, String sPassword, String communityid)
	{
		if(fromCache)
		{
			//Check if a request for collections has been made to Dspace
			if(this.collections != null)
			{
				for(ICollection collection : this.collections)
				{
					if(collection.getId().equals(sCollectionId))
					{
						return collection;
					}
				}
			}
			else
			{
				this.getAllCollections(restTemplate, dspaceProperties, sUserName, sPassword, communityid);
			}
		}
		else
		{
			//Reload the collections data associated with this community
			ICommunity community = this.getCommunity(communityid, true, null, null, null, null);

			//Remove the collection metadata from the cache
			for(String collectionid: community.getCollectionIds()){
				Iterator<ICollection> iterator = this.collections.iterator();
				while(iterator.hasNext())
				{
					if(iterator.next().getId().equals(collectionid))
						iterator.remove();
				}
			}
			
			//Load the collection metadata associated with the community
			this.getAllCollections(restTemplate, dspaceProperties, sUserName, sPassword, communityid);
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
	public ICommunity getCommunity(String communityId, boolean fromCache, RestTemplate restTemplate, Properties dspaceProperties, String sUserName, String sPassword)
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
				this.getAllCommunities(restTemplate, dspaceProperties, sUserName, sPassword);
			}
		}
		else
		{
			//Reload the community data from dspace
			this.communities = null;
			this.getAllCommunities(restTemplate, dspaceProperties, sUserName, sPassword);
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
}
