package edu.asu.spring.quadriga.dspace.service.impl;


import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.implementation.Item;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;

/**
 * The purpose of the class is to make rest service calls to dspace
 * and fetch the communities, collections and items.
 * This class manages the rest template, url, username and password.
 *  
 * @author Ram Kumar Kumaresan
 *
 */

@Service
@Scope(value="session", proxyMode= ScopedProxyMode.INTERFACES)
public class DspaceManager implements IDspaceManager{

	@Autowired
	@Qualifier("dspaceFilePath")
	private String filePath;
	
	@Autowired
	@Qualifier("dspaceURL")
	private String url;

	@Autowired
	@Qualifier("restTemplate")
	private RestTemplate restTemplate;
	
	//Handle to the proxy community manager class
	@Autowired
	private ICommunityManager proxyCommunityManager;

	@Override
	public List<ICommunity> getAllCommunities(String sUserName, String sPassword) {
		
		//TODO: Remove this after actual user synchronization to Dspace
		sUserName="ramk@asu.edu";
		sPassword="123456";
		
		return proxyCommunityManager.getAllCommunities(restTemplate, url, sUserName, sPassword);
	}

	@Override
	public List<ICollection> getAllCollections(String sUserName, String sPassword, String sCommunityId) {
		
		//TODO: Remove this after actual user synchronization to Dspace
				sUserName="ramk@asu.edu";
				sPassword="123456";
				
		return proxyCommunityManager.getAllCollections(restTemplate, url, sUserName, sPassword, sCommunityId);
	}

	@Override
	public String getCommunityName(String sCommunityId) 
	{
		return proxyCommunityManager.getCommunityName(sCommunityId);
	}
	
	@Override
	public String getCollectionName(String sCollectionId) 
	{
		return proxyCommunityManager.getCollectionName(sCollectionId);
	}
	
	@Override
	public ICollection getCollection(String sCollectionId)
	{
		return proxyCommunityManager.getCollection(sCollectionId);
	}


	@Override
	public List<IItem> getAllItems(String sCollectionId) {
		return  proxyCommunityManager.getAllItems(sCollectionId); 
	}
	
	
	/**
	 * This method is used to load the Dspace server certificate during the start of the application.
	 * It also overloads the verify method of the hostname verifier to always return TRUE for the dspace hostname.
	 * It will be invoked only once.
	 */
	public void start()
	{
		System.setProperty("javax.net.ssl.trustStore", filePath);

		javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
				new javax.net.ssl.HostnameVerifier(){

					public boolean verify(String hostname,
							javax.net.ssl.SSLSession sslSession) {
						if (hostname.equals(url)) {
							return true;
						}
						return false;
					}
				});
	}

}
