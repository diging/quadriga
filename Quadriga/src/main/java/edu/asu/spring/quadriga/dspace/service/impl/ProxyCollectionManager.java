package edu.asu.spring.quadriga.dspace.service.impl;

import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICollectionsIdList;
import edu.asu.spring.quadriga.domain.implementation.Collection;
import edu.asu.spring.quadriga.domain.implementation.CollectionsIdList;
import edu.asu.spring.quadriga.dspace.service.ICollectionManager;

@Service
public class ProxyCollectionManager implements Callable<ICollection>, ICollectionManager {

	private String userName;
	private String password;
	private RestTemplate restTemplate;
	private String url;	
	private String CollectionId;

	//TODO: Remove after developmend and testing
	public ProxyCollectionManager()
	{
		url="import.hps.ubio.org";
		userName = "ramk@asu.edu";
		password = "123456";
	}
	public ProxyCollectionManager(RestTemplate restTemplate, String sUrl, String sUserName, String sPassword, String sCollectionId)
	{
		this.userName = sUserName;
		this.password = sPassword;
		this.url = sUrl;
		this.restTemplate = restTemplate;
		this.CollectionId = sCollectionId;
	}

	private String getCompleteUrlPath(String restPath)
	{
		return "https://"+url+restPath+"?email="+userName+"&password="+password;
	}

	@Override
	public ICollection call() throws Exception {

		String sRestServicePath = getCompleteUrlPath("/rest/collections/"+CollectionId+".xml");
		ICollection collection = (Collection) restTemplate.getForObject(sRestServicePath, Collection.class);

		return collection;

//		ICollection collectionTest = new Collection();
//		collectionTest.setId(CollectionId);
//		collectionTest.setName(CollectionId+" Name");
//		return collectionTest;
	}


	@Override
	public ICollection testRestGET(RestTemplate restTemplate, String url, String sUserName, String sPassword)
	{
		String sRestServicePath = getCompleteUrlPath("/rest/collections/43.xml");
		ICollection collection = (Collection) restTemplate.getForObject(sRestServicePath, Collection.class);

		return collection;
	}

}
