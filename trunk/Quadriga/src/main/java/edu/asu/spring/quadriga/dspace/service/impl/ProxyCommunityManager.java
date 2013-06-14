package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICollections;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;



/**
 * The purpose of the class is to implement proxy pattern for the community class
 * that is to be fetched from dspace
 * 
 * @author Ram Kumar Kumaresan
 *
 */

@Service("proxyCommunityManager")
public class ProxyCommunityManager implements ICommunityManager {

	List<ICommunity> communities;
	List<ICollection> collection;
	
	@Autowired
	@Qualifier("communityManager")
	ICommunityManager communityManager;
	
	@Override
	public List<ICommunity> getAllCommunities(String sUserName, String sPassword) {
		if(communities==null)
		{
			System.out.println("Proxy community manager making a rest call....");
			communities = communityManager.getAllCommunities(sUserName, sPassword);
			
			for(ICommunity community: communities)
			{
				//Get the collections that store a list of collection
				ICollections collections = community.getCollections();
				
				//Do this for each collection in the list
				ExecutorService executor = Executors.newFixedThreadPool(collections.getCollections().size());
				
				System.out.println("Created "+collections.getCollections().size()+" Collection Threads.....");
				int iThreadCount=0;
				for(ICollection collection: collections.getCollections())
				{
					System.out.print(collection.getId()+" ");
					
					//TODO: Create a proxy collections to get that collection name
					//Pass the collection object as input and the thread should set the collection name
					
					Callable<ICollection> callable = new ProxyCollectionManager(collection.getId());
					Future<ICollection> future = executor.submit(callable);
					
					try {
						this.collection.add(future.get());
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Started Thread - "+iThreadCount);
					iThreadCount++;
				}
				System.out.println();
			}
		}
		System.out.println("Proxy manager returning its list of communities....");
		return communities;
	}	
	
}
