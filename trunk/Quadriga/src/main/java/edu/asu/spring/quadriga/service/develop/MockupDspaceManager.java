package edu.asu.spring.quadriga.service.develop;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.implementation.Collection;
import edu.asu.spring.quadriga.domain.implementation.Community;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;

//@Service
public class MockupDspaceManager {

//	List<ICommunity> communities;
//
//	public MockupDspaceManager()
//	{
//		ICollection collection = new Collection();
//
//		communities = new ArrayList<ICommunity>();	
//
//		ICommunity community = new Community();
//		community.setCollections(new ArrayList<ICollection>());
//
//		//Create a community - History of Evolutionary Ecology
//		community.setTitle("History of Evolutionary Ecology");
//		community.setDescription("History of Evolutionary Ecology - Community Description to go here");
//
//		//Create a collection - Genecology Publications
//		collection.setTitle("Genecology Publications");
//		collection.setDescription("Genecology Publications - Collection Description to go here");		
//		community.addCollection(collection);
//
//		//Create a collection - Anthony David Bradshaw (1926-2008) papers
//		collection = new Collection();
//		collection.setTitle("Anthony David Bradshaw (1926-2008) papers");
//		collection.setDescription("Anthony David Bradshaw (1926-2008) papers - Collection Description to go here");		
//		community.addCollection(collection);
//
//		communities.add(community);
//
//		//Create a community - The Embryo Project
//		community = new Community();
//		community.setCollections(new ArrayList<ICollection>());
//		community.setTitle("The Embryo Project");
//		community.setDescription("The Embryo Project - Community Description to go here");
//
//		//Create a collection - Embryo Project Articles
//		collection = new Collection();
//		collection.setTitle("Embryo Project Articles");
//		collection.setDescription("Embryo Project Articles - Collection Description to go here");		
//		community.addCollection(collection);
//
//		//Create a collection - Harold Heath Collection
//		collection = new Collection();
//		collection.setTitle("Harold Heath Collection");
//		collection.setDescription("Harold Heath Collection - Collection Description to go here");		
//		community.addCollection(collection);
//
//		communities.add(community);
//
//
//	}
//
//	@Override
//	public List<ICommunity> getAllCommunities()
//	{
//		return this.communities;
//	}
//	
//	@Override
//	public List<ICollection> getAllCollections(String sCommunityTitle)
//	{
//		for(ICommunity communityInList: this.communities)
//		{
//			if(communityInList.getTitle().equals(sCommunityTitle))
//				return communityInList.getCollections();			
//		}
//		
//		return null;
//	}
//
//	@Override
//	public void checkRestConnection() {
//		throw new NotImplementedException("Not yet implemented");		
//	}
}
