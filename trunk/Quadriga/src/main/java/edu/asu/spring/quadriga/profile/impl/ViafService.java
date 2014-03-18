package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.jaxb.viaf.Item;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;

/**
 * this class sends request to viaf service and gets back object in response
 * 
 * @author rohit pendbhaje
 */

@Service
public class ViafService implements IService {

	@Autowired
	private ISearchResult searchResult;
	
	@Autowired
	private IDBConnectionProfileManager dbConnectionProfileManager;
	
	@Autowired
	private ISearchResultFactory searchResultFactory;
	
	private String serviceid;
	private String name;
		
	@Autowired
	@Named("restTemplateViaf")
	private RestTemplate restTemplate;

	@Autowired
	@Qualifier("viafURL")
	private String viafURL;

	@Autowired
	@Qualifier("searchViafURLPath")
	private String searchViafURLPath;
	
	@Autowired
	@Qualifier("searchViafURLPath1")
	private String searchViafURLPath1;
	
	@Autowired
	@Qualifier("searchViafURLPath2")
	private String searchViafURLPath2;
	

	@Override
	public void setServiceId(String serviceid) {
		this.serviceid = serviceid;
		
	}

	@Override
	public String getServiceId() {
		return "edu.asu.viaf";
	}

	@Override
	public void setName(String name) {
		this.name = name;	
	}

	@Override
	public String getName() {

		return "Viaf";
	}
	
	/**
	 * this method takes user entered term and calls viaf REST API to retrieve search results
	 * @param item			term entered by user
	 * 		  startindex	index in the REST API of viaf service
	 * @return list of searchresults retrieved from viaf 
	 * @author rohit pendbhaje
	 * 	
	 */

	@Override
	public List<ISearchResult> search(String word) {
				
		List<Item> items = null;
		String fullUrl;

			fullUrl = viafURL.trim() + searchViafURLPath.trim() + " " + word.trim() + searchViafURLPath1.trim() + IService.STARTINDEX.trim() + searchViafURLPath2.trim();
			ViafReply rep = (ViafReply) restTemplate.getForObject(fullUrl, ViafReply.class);
			items = rep.getChannel().getItems();
			
			List<ISearchResult> searchResults = new ArrayList<ISearchResult>();
			for(Item i : items)
			{
				ISearchResult searchResult = searchResultFactory.getSearchResultObject();
				searchResult.setDescription(i.getPubDate());
				searchResult.setId(i.getLink());
				searchResult.setName(i.getTitle());
				searchResults.add(searchResult);
			}
			
		return searchResults;
	}
	
}
