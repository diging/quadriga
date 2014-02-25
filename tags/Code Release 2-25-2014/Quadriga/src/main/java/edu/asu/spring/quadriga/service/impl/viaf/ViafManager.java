package edu.asu.spring.quadriga.service.impl.viaf;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.jaxb.viaf.Item;
import edu.asu.spring.quadriga.profile.impl.ViafReply;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IViafManager;


@Service
public class ViafManager implements IViafManager {
	
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
	
	@Autowired
	private ISearchResultFactory searchResultFactory;
	
	

	@Override
	public List<ISearchResult> search (String item, String startIndex) {
		
		List<Item> items = null;
		String fullUrl;
		

			fullUrl = viafURL.trim() + searchViafURLPath.trim() + " " + item.trim() + searchViafURLPath1.trim() + startIndex.trim() + searchViafURLPath2.trim();

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
