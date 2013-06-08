package edu.asu.spring.quadriga.service.impl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.implementation.Communities;
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

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void checkRestConnection()
	{
		System.out.println("----------------------------------"+url);
		Communities communities = (Communities)restTemplate.getForObject("https://import.hps.ubio.org/rest/communities.xml?email=ramk@asu.edu&password=123456", Communities.class);
		System.out.println(communities.getCommunities().size());
		//		URL wp;
		//		String result = "";
		//		try {
		//			wp = new URL("https://import.hps.ubio.org/rest/communities.xml");
		//			InputStream xml = wp.openStream();
		//			BufferedReader br = new BufferedReader(new InputStreamReader(xml));
		//			String s;
		//			while((s=br.readLine())!=null)
		//			{
		//				result +=s;
		//			}
		//		} catch (Exception e) {
		//			System.out.println("Exception "+e.getMessage());
		//		}
		//
		//		System.out.println(result);


	}

	@Override
	public List<ICommunity> getAllCommunities() {
		//throw new NotImplementedException("getAllCommunities yet to be implemented");
		return null;
	}

	@Override
	public List<ICollection> getAllCollections(String sCommunityTitle) {
		//throw new NotImplementedException("getAllCollections yet to be implemented");
		return null;
	}

	/**
	 * This method id user to load the Dspace server certificate during the start of the application.
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
