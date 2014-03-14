package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.implementation.BitStream;
import edu.asu.spring.quadriga.domain.implementation.Collection;
import edu.asu.spring.quadriga.domain.implementation.Community;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunities;

/**
 * The purpose of the class is to implement proxy pattern for the community class
 * that is to be fetched from dspace
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
	 * Used to generate the corresponding url necessary to access the collection details
	 * @param restPath The REST path required to access the collection in Dspace. This will be appended to the actual domain url.
	 * @return			Return the complete REST service url along with all the authentication information
	 */
	private String getCompleteUrlPath(String restPath, String userName, String password)
	{
		return "https://"+restPath+"?email="+userName+"&password="+password;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICommunity> getAllCommunities(RestTemplate restTemplate, String url, String sUserName, String sPassword) {
		if(communities == null)
		{
			String sRestServicePath = getCompleteUrlPath(url+"/rest/communities.xml", sUserName, sPassword);
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
	public List<ICollection> getAllCollections(RestTemplate restTemplate, String url, String sUserName, String sPassword, String sCommunityId) {

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
							collection = new Collection(collectionId,restTemplate,url,sUserName,sPassword);
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
	public ICollection getCollection(String sCollectionId)
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
	public IBitStream getBitStreamName(String sCollectionId, String sItemId, String sBitStreamId)
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
	public List<IBitStream> getAllBitStreams(RestTemplate restTemplate, String url, String sUserName, String sPassword, String sCollectionId, String sItemId)
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
							//Found the item that user requested !
							//This is the first time this item is accessed
							if(item.getBitstreams().size() == 0)
							{
								IBitStream bitstream = null;
								for(String bitid: item.getBitids())
								{
									bitstream = new BitStream();
									bitstream.setId(bitid);
									item.addBitstream(bitstream);
								}
								
								item.setRestConnectionDetails(restTemplate, url, sUserName, sPassword);
								Thread bitstreamThread = new Thread(item);
								bitstreamThread.start();
								
							}
							return item.getBitstreams();
						}
					}
				}
			}
		}

		return null;
	}
}