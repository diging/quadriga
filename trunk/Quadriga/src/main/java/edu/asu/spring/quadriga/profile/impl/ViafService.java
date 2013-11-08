package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
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

	@Autowired
	private IViafManager viafmanager;
	
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
		Integer si = 1;
		
		List<ViafReply.Items> viafEntries = viafmanager.search(word, si);
		
		
		
		
		if(viafEntries!=null)
		{
			
			for(ViafReply.Items vi : viafEntries)
			{
				ISearchResult searchResult =searchResultFactory.getSearchResultObject();
				searchResult.setName(vi.getTitle());
				searchResult.setId(vi.getLink());
				//searchResult.setDescription(ce.getDescription());
				searchResults.add(searchResult);
			}
			
		}
		return searchResults;
	
	}
	
	

}
