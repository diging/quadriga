package edu.asu.spring.quadriga.dspace.service;

import java.util.List;
import org.springframework.web.client.RestTemplate;
import edu.asu.spring.quadriga.domain.ICommunity;


public interface ICommunityManager {

	public abstract List<ICommunity> getAllCommunities(RestTemplate restTemplate, String url, String sUserName, String sPassword);
}