package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.jaxb.viaf.Item;
import edu.asu.spring.quadriga.profile.impl.ViafReply;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IViafManager;

/**
 * this class sends request to viaf service and gets back object in response
 * 
 * @author rohit pendbhaje
 */

@Service
public class ViafService implements IService {

	@Autowired
	private IViafManager viafmanager;
	
	/*@Autowired
	private ViafManager1 viafmanager;*/
	
	@Autowired
	private ISearchResult searchResult;
	
	@Autowired
	private IDBConnectionProfileManager dbConnectionProfileManager;
	
	@Autowired
	private ISearchResultFactory searchResultFactory;
	
	private String serviceid;
	private String name;
		
	@Inject
	@Named("restTemplateViaf")
	RestTemplate restTemplate;

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
	 * this method searches for the term user entered in viaf service and returns result
	 * @param item			term enterd by user
	 * 		  startindex	index in the REST api of viaf service
	 * 
	 * @return list of searchresults responded by viaf
	 * 
	 * @author rohit pendbhaje
	 * 	
	 */

	@Override
	public List<ISearchResult> search(String word) {
				
		List<Item> items = null;
		String fullUrl;
		

			fullUrl = viafURL.trim() + searchViafURLPath.trim() + " " + word.trim() + searchViafURLPath1.trim() + IService.startIndex.trim() + searchViafURLPath2.trim();
			 
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
