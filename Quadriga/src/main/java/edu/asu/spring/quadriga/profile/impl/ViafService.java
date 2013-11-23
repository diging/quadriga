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
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IViafManager;

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

	@Override
	public List<ISearchResult> search(String word) {
		
		
		String startIndex="1";

/*
		List<ISearchResult> searchResults = new ArrayList<ISearchResult>();

		String si = "1";

		
		

		List<Item> viafEntries = viafmanager.search(word, si);
		

		//List<ViafReply.Items> viafEntries = viafmanager.search(word, si);

		
		//List<HashMap<String,String>> viafEntries = viafmanager.search(word);
		
		
		if(viafEntries!=null){

			for(HashMap<String,String> entries : viafEntries)
			{
				ISearchResult searchResult =searchResultFactory.getSearchResultObject();
				searchResult.setName(entries.get("title"));
				searchResult.setId(entries.get("link"));
				searchResult.setDescription(entries.get("pubDate"));
				//searchResult.setDescription(ce.getDescription());
				searchResults.add(searchResult);
			}
			
		
		}
		
		return searchResults;
		
		if(viafEntries!=null)
		{
			
			for(Item vi : viafEntries)
			{
				ISearchResult searchResult =searchResultFactory.getSearchResultObject();
//				searchResult.setName(vi.getTitle());
//				searchResult.setId(vi.getLink());
				searchResult.setDescription(vi.getPubDate());
				//searchResult.setDescription(ce.getDescription());
				searchResults.add(searchResult);
			}
			
		}
		return searchResults;*/
		
		List<Item> items = null;
		String fullUrl;
		

			fullUrl = viafURL.trim() + searchViafURLPath.trim() + " " + word.trim() + searchViafURLPath1.trim() + startIndex.trim() + searchViafURLPath2.trim();
			 
			//fullUrl = fullUrl.replaceAll("\n", "");
			//fullUrl = fullUrl.replaceAll("\t", "");

			

			ViafReply rep = (ViafReply) restTemplate.getForObject(fullUrl, ViafReply.class);
			//String ex = restTemplate.getForObject(fullUrl, String.class);
			//System.out.println(ex);
			//String rep = restTemplate.getForObject(fullUrl, String.class);
			//System.out.println(rep);
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
