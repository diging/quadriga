package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICommunities;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.implementation.Communities;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;



/**
 * The purpose of the class is to implement proxy pattern for the community class
 * that is to be fetched from dspace
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service("communityManager")
public class CommunityManager implements ICommunityManager {

	private String getCompleteUrlPath(String restPath, String userName, String password)
	{
		return "https://"+restPath+"?email="+userName+"&password="+password;
	}

	@Override
	public List<ICommunity> getAllCommunities(RestTemplate restTemplate, String url, String sUserName, String sPassword) {
		//TODO: Uncomment to user the correct username and password
		//		this.userName = sUserName;
		//		this.password = sPassword;
		
		System.out.println("Community Manager connecting to Dspace....");
		String sRestServicePath = getCompleteUrlPath(url+"/rest/communities.xml", sUserName, sPassword);
		ICommunities communities = (Communities)restTemplate.getForObject(sRestServicePath, Communities.class);

		return communities.getCommunities();
	}	

}
