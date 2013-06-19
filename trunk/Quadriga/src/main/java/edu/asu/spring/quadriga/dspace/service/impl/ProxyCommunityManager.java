package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
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
public class ProxyCommunityManager implements ICommunityManager, Runnable {

	private List<ICommunity> communities;
	private List<ICollection> collections;

	//create a list to hold the Future object associated with Callable
	private List<Future<ICollection>> futureList;

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
				this.futureList = new ArrayList<Future<ICollection>>();


				ICommunity community = null;
				for(IDspaceCommunity dspaceCommunity: dsapceCommunities.getCommunities())
				{			
					community = new Community();
					if(community.copy(dspaceCommunity))
					{
						//For each community get the collection names associated with it
						//Create a thread pool that equals the size of the collection
						ExecutorService executor = Executors.newFixedThreadPool(community.getCollectionIds().size());

						for(String collectionId :community.getCollectionIds()){
							//Create a thread to retrieve the collection object from Dspace
							Callable<ICollection> callable = new ProxyCollectionManager(restTemplate, url, sUserName, sPassword, collectionId);
							Future<ICollection> future = executor.submit(callable);
							futureList.add(future);
						}
						communities.add(community);
					}
				}

				//After creating all the collection threads, create a thread to check their status
				Thread checkCollectionThread = new Thread(this);
				checkCollectionThread.start();
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

	@Override
	public void run() {
		Iterator<Future<ICollection>> iteratorFuture;
		Future<ICollection> future;
		
		//Do this until all collection threads end
		while(this.futureList.size()>0)
		{
			//Communities are already fetched from Dspace			
			//Get an iterator so that the list can be modified while iterating
			iteratorFuture = this.futureList.iterator();

			//Iterate through each thread and check if the thread returned a collection object.
			while(iteratorFuture.hasNext())
			{
				future = iteratorFuture.next();
				if(future.isDone())
				{
					try {
						//Add the collection object to the class variable and remove the thread reference from the list
						ICollection collection = future.get();
						this.collections.add(collection);

						for(ICommunity community: communities)
						{
							if(community.getCollectionIds().contains(collection.getId()))
							{
								community.addCollection(collection);
							}
						}
						iteratorFuture.remove();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
