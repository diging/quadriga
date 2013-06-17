package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import org.springframework.web.client.RestTemplate;


public interface ICommunityManager {

	public abstract List<IDspaceCommunity> getAllCommunities(RestTemplate restTemplate, String url, String sUserName, String sPassword);
}