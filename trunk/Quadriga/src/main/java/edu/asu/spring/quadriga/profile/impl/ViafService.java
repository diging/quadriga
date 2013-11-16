package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IViafManager;

@Service
public class ViafService implements IService {

	//@Autowired
	//private IViafManager viafmanager;
	
	@Autowired
	private ViafManager1 viafmanager;
	
	@Autowired
	private ISearchResult searchResult;
	
	@Autowired
	private IDBConnectionProfileManager dbConnectionProfileManager;
	
	@Autowired
	private ISearchResultFactory searchResultFactory;
	
	private String serviceid;
	private String name;
		
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


		List<ISearchResult> searchResults = new ArrayList<ISearchResult>();

		//String si = "1";

		
		

		//List<Items> viafEntries = viafmanager.search(word, si);

		//List<ViafReply.Items> viafEntries = viafmanager.search(word, si);

		
		List<HashMap<String,String>> viafEntries = viafmanager.search(word);
		
		
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
		
		/*if(viafEntries!=null)
		{
			
			for(Items vi : viafEntries)
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
	
	}
	
	

}
