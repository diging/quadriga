package edu.asu.spring.quadriga.dspace.service;

import org.springframework.web.client.RestTemplate;


public interface ICollectionManager {

	public abstract IDspaceCollection testRestGET(RestTemplate restTemplate, String url, String sUserName, String sPassword);

}