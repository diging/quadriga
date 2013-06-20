package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.implementation.Collection;
import edu.asu.spring.quadriga.domain.implementation.Community;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;
import edu.asu.spring.quadriga.dspace.service.IDspacecCommunities;



/**
 * The purpose of the class is to implement proxy pattern for the community class
 * that is to be fetched from dspace
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class ProxyCommunityManager implements ICommunityManager {

	private List<ICommunity> communities;
	private List<ICollection> collections;

	private String getCompleteUrlPath(String restPath, String userName, String password)
	{
		return "https://"+restPath+"?email="+userName+"&password="+password;
	}

	@Override
	public List<ICommunity> getAllCommunities(RestTemplate restTemplate, String url, String sUserName, String sPassword) {
		//TODO: Uncomment to use the correct username and password
		//		this.userName = sUserName;
		//		this.password = sPassword;
		if(communities == null)
		{
			String sRestServicePath = getCompleteUrlPath(url+"/rest/communities.xml", sUserName, sPassword);
			IDspacecCommunities dsapceCommunities = (DspaceCommunities)restTemplate.getForObject(sRestServicePath, DspaceCommunities.class);

			if(dsapceCommunities.getCommunities().size()>0)
			{
				communities = new ArrayList<ICommunity>();

				//Initialize collection specific objects
				collections = new ArrayList<ICollection>();

				ICommunity community = null;
				ICollection collection = null;
				for(IDspaceCommunity dspaceCommunity: dsapceCommunities.getCommunities())
				{			
					community = new Community();
					
					//If the data was successfully transferred from Dspace representation to the one used in Quadriga
					if(community.copy(dspaceCommunity))
					{						
						for(String collectionId :community.getCollectionIds()){
							collection = new Collection(collectionId,restTemplate,url,sUserName,sPassword);
							Thread collectionThread = new Thread(collection);
							collectionThread.start();
							
							this.collections.add(collection);
							community.addCollection(collection);
						}
						communities.add(community);
					}
				}
			}
		}
		return communities;
	}

	//TODO: Push Down to collection class and remove the list of collections in this class
	@Override
	public ICollection getCollection(String sCollectionId)
	{
		//Check if a request for collections has been made to Dspace
		if(this.collections != null)
		{
			for(ICollection collection : this.collections)
			{
				if(collection.getId().equals(sCollectionId))
					return collection;
			}
		}
		return null;
	}

	
}
