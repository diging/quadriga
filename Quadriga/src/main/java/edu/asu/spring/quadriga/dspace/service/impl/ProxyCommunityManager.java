package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.text.IconView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.dspace.service.ICollectionManager;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollectionEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollectionsIdList;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;



/**
 * The purpose of the class is to implement proxy pattern for the community class
 * that is to be fetched from dspace
 * 
 * @author Ram Kumar Kumaresan
 *
 */

@Service("proxyCommunityManager")
public class ProxyCommunityManager implements ICommunityManager {

	private List<IDspaceCommunity> communities;
	private List<IDspaceCollection> collections;

	@Autowired
	@Qualifier("communityManager")
	private ICommunityManager communityManager;

	//create a list to hold the Future object associated with Callable
	private List<Future<IDspaceCollection>> futureList;


	@Override
	public List<IDspaceCommunity> getAllCommunities(RestTemplate restTemplate, String url, String sUserName, String sPassword) {
		if(communities==null)
		{
			communities = communityManager.getAllCommunities(restTemplate, url, sUserName, sPassword);

			//Initialize collection specific objects
			collections = new ArrayList<IDspaceCollection>();
			futureList = new ArrayList<Future<IDspaceCollection>>();

			//For each community get the collection names associated with it
			for(IDspaceCommunity community: communities)
			{
				IDspaceCollectionsIdList collectionIDList = community.getCollectionsIDList();

				//Create a thread pool that equals the size of the collection
				ExecutorService executor = Executors.newFixedThreadPool(collectionIDList.getCollectionid().size());

				int iThreadCount=0;
				for(IDspaceCollectionEntityId collectionEntity :collectionIDList.getCollectionid()){
					//Create a thread to retrieve the collection object from Dspace
					Callable<IDspaceCollection> callable = new ProxyCollectionManager(restTemplate, url, sUserName, sPassword, collectionEntity.getId());
					Future<IDspaceCollection> future = executor.submit(callable);
					futureList.add(future);
					iThreadCount++;
				}
			}
		}
		else
		{
			//Communities are already fetched from Dspace			
			//Get an iterator so that the list can be modified while iterating
			Iterator<Future<IDspaceCollection>> iteratorFuture = futureList.iterator();

			//Iterate through each thread and check if the thread returned a collection object.
			while(iteratorFuture.hasNext())
			{
				Future<IDspaceCollection> future = iteratorFuture.next();
				if(future.isDone())
				{
					try {
						//Add the collection object to the class variable and remove the thread reference from the list
						this.collections.add(future.get());
						iteratorFuture.remove();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (ExecutionException e) {
						e.printStackTrace();
					}
				}
			}			
			System.out.println("Collections retrieved so far: "+this.collections.size());

			//Add the collection to the corresponding community
			for(IDspaceCollection collection: this.collections)
			{
				for(IDspaceCommunity community: this.communities)
				{
					IDspaceCollectionsIdList collectionIDList = community.getCollectionsIDList();
					for(IDspaceCollectionEntityId collectionEntity :collectionIDList.getCollectionid()){
						//The collection id is found in the list within the community
						if(collectionEntity.getId().equals(collection.getId()))
						{
							//TODO: Add the collection to the community
						}
					}
				}
			}
		}
		System.out.println("Proxy manager returning its list of communities....");


		return communities;
	}	

}
