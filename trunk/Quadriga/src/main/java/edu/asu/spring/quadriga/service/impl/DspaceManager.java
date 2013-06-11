package edu.asu.spring.quadriga.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunities;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.implementation.Collection;
import edu.asu.spring.quadriga.domain.implementation.Communities;
import edu.asu.spring.quadriga.domain.implementation.Community;
import edu.asu.spring.quadriga.service.IDspaceManager;

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
	@Qualifier("dspaceURL")
	private String url;
	private String userName;
	private String password;

	@Inject
	@Named("restTemplate")
	RestTemplate restTemplate;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public DspaceManager() {
		userName = "ramk@asu.edu";
		password = "123456";
	}

	private String getCompleteUrlPath(String restPath)
	{
		return "https://"+url+restPath+"?email="+userName+"&password="+password;
	}
	@Override
	public void checkRestConnection(String sUserName, String sPassword)
	{
		//TODO: Uncomment to user the correct username and password
		//		this.userName = sUserName;
		//		this.password = sPassword;
	}

	@Override
	public List<ICommunity> getAllCommunities(String sUserName, String sPassword) {
		//TODO: Uncomment to user the correct username and password
		//		this.userName = sUserName;
		//		this.password = sPassword;
		String sRestServicePath = getCompleteUrlPath("/rest/communities.xml");
		ICommunities communities = (Communities)restTemplate.getForObject(sRestServicePath, Communities.class);

		for(ICommunity c:communities.getCommunities())
		{
			System.out.println(c.getId());			
			System.out.println(c.getName());
			System.out.println(c.getDescription());
			System.out.println(c.getIntroductoryText());
			System.out.println(c.getCountItems());
			System.out.println(c.getEntityReference());
			System.out.println(c.getHandle());
			System.out.println(c.getEntityId());
			System.out.println("Total Collections: "+c.getCollections().getCollections().size());
			for(ICollection collection: c.getCollections().getCollections())
			{
//				System.out.print(collection.getId()+"-"+getCollectionName(sUserName, sPassword, collection.getId()));
				System.out.print(collection.getId()+" ");
			}
			System.out.println("\n-----------------------------------------------------------");
		}
		return communities.getCommunities();
	}

	@Override
	public List<ICollection> getAllCollections(String sUserName, String sPassword, String sCommunityTitle) {
		//throw new NotImplementedException("getAllCollections yet to be implemented");
		return null;
	}

	public String getCollectionName(String sUserName, String sPassword, String sCollectionId)
	{
		//TODO: Uncomment to user the correct username and password
		//		this.userName = sUserName;
		//		this.password = sPassword;
		String sRestServicePath = getCompleteUrlPath("/rest/collections/"+sCollectionId+".xml");
		ICollection collection = (Collection)restTemplate.getForObject(sRestServicePath, Collection.class);

		return collection.getName();
	}
	/**
	 * This method is used to load the Dspace server certificate during the start of the application.
	 * It also overloads the verify method of the hostname verifier to always return TRUE for the dspace hostname.
	 * 
	 */
	public void start()
	{
		//TODO: Change the path
		System.setProperty("javax.net.ssl.trustStore", "C:\\Users\\Ram\\Documents\\EclipseProjects\\Quadriga\\src\\main\\resources\\jssecacerts");
		//		System.setProperty("javax.net.ssl.trustStore", "file:/Quadriga/src/main/resources/jssecacerts");

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
