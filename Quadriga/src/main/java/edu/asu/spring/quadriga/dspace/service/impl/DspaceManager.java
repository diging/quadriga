package edu.asu.spring.quadriga.dspace.service.impl;


import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;

/**
 * The purpose of the class is to make rest service calls to dspace
 * and fetch the communities, collections and items.
 * 
 * @author Ram Kumar Kumaresan
 *
 */

@Service
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

	private String userName;
	private String password;
	
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
	public List<IDspaceCollection> getAllCollections(String sUserName, String sPassword, String sCommunityTitle) {
		throw new NotImplementedException("getAllCollections yet to be implemented");
	}

	@Override
	public String getCollectionName(String sUserName, String sPassword, String sCollectionId)
	{
		throw new NotImplementedException("getAllCollectionName yet to be implemented");
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
