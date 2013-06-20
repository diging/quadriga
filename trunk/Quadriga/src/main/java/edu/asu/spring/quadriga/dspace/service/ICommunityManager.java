package edu.asu.spring.quadriga.dspace.service;

import java.util.List;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;


public interface ICommunityManager {

	public abstract List<ICommunity> getAllCommunities(RestTemplate restTemplate, String url, String sUserName, String sPassword);

	public abstract List<ICollection> getAllCollections(RestTemplate restTemplate, String url,
			String sUserName, String sPassword, String sCommunityId);

	public abstract String getCommunityName(String sCommunityId);
	
	public abstract ICollection getCollection(String sCollectionId);
}