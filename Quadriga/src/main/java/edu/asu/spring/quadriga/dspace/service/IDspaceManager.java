package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;

public interface IDspaceManager {

	public abstract List<ICommunity> getAllCommunities(String sUserName, String sPassword);

	public abstract List<ICollection> getAllCollections(String sUserName, String sPassword,String sCommunityId);

	public abstract String getCommunityName(String sCommunityId);
	
	public abstract ICollection getCollection(String sCollectionId);

}
