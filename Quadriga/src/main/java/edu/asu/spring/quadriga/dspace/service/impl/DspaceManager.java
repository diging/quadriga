package edu.asu.spring.quadriga.dspace.service.impl;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.dspace.service.ICommunityManager;
import edu.asu.spring.quadriga.dspace.service.IDspaceManager;
import edu.asu.spring.quadriga.service.impl.DictionaryManager;

/**
 * The purpose of the class is to make rest service calls to dspace
 * and fetch the communities, collections, items and bitstreams
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
	
	private static final Logger logger = LoggerFactory
			.getLogger(DspaceManager.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICommunity> getAllCommunities(String sUserName, String sPassword) {

		//TODO: Remove this after actual user synchronization to Dspace
		sUserName="ramk@asu.edu";
		sPassword="123456";

		return proxyCommunityManager.getAllCommunities(restTemplate, url, sUserName, sPassword);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ICollection> getAllCollections(String sUserName, String sPassword, String sCommunityId) {

		//TODO: Remove this after actual user synchronization to Dspace
		sUserName="ramk@asu.edu";
		sPassword="123456";

		return proxyCommunityManager.getAllCollections(restTemplate, url, sUserName, sPassword, sCommunityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityName(String sCommunityId) 
	{
		return proxyCommunityManager.getCommunityName(sCommunityId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCollectionName(String sCollectionId) 
	{
		return proxyCommunityManager.getCollectionName(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollection getCollection(String sCollectionId)
	{
		return proxyCommunityManager.getCollection(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommunityId(String sCollectionId)
	{
		return proxyCommunityManager.getCommunityId(sCollectionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IItem> getAllItems(String sCollectionId) {
		return  proxyCommunityManager.getAllItems(sCollectionId); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IBitStream> getAllBitStreams(String sUserName, String sPassword, String sCollectionId, String sItemId)
	{
		//TODO: Remove this after actual user synchronization to Dspace
		sUserName="ramk@asu.edu";
		sPassword="123456";

		return proxyCommunityManager.getAllBitStreams(restTemplate, url, sUserName, sPassword, sCollectionId, sItemId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getItemName(String sCollectionId, String sItemId)
	{
		return proxyCommunityManager.getItemName(sCollectionId, sItemId);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBitStream getBitStreamName(String sCollectionId, String sItemId, String sBitStreamId)
	{
		return proxyCommunityManager.getBitStreamName(sCollectionId, sItemId, sBitStreamId);
	}

	/**
	 * This method is used to load the Dspace server certificate during the start of the application.
	 * It also overloads the verify method of the hostname verifier to always return TRUE for the dspace hostname.
	 * It will be invoked only once.
	 */
	public void start()
	{
		logger.info("The filepath used is: "+filePath);
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
