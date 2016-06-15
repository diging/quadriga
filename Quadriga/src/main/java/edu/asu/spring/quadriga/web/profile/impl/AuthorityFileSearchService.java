package edu.asu.spring.quadriga.web.profile.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceRegistry;
import edu.asu.spring.quadriga.service.profile.IAuthorityFileSearchService;

/**
 * this class retrieves items returned by authority service and copies it into the searchresultbackbean 
 * 
 * @author rohit pendbhaje
 *
 */

@Service
public class AuthorityFileSearchService implements IAuthorityFileSearchService {
	
	
	@Autowired
	private IServiceRegistry serviceRegistry;
	
	@Autowired
	private SearchResultBackBeanForm backBeanForm;

/**
 * this method takes results from authority service (e.g. conceptpower) method and copy 
 * it in the SearchResultBackBean 
 * 	
 * @param serviceId		id of the service selected by the user
 * @param term			term entered by the user
 * @return	list of copied searchresults of SearchResultBackBean class returned from viaf service
 */
	@Override
	public List<SearchResultBackBean> searchInAuthorityFile(String serviceId, String term){
		 	
		IService serviceObj = serviceRegistry.getServiceObject(serviceId);
		List<ISearchResult> searchResults = serviceObj.search(term);
		List<SearchResultBackBean> searchResultBackBeansList = new ArrayList<SearchResultBackBean>();
		
		for(ISearchResult searchResult:searchResults){
			
				SearchResultBackBean searchResultBackBean = new SearchResultBackBean();
				searchResultBackBean.setWord(searchResult.getName());
				searchResultBackBean.setId(searchResult.getId());
				searchResultBackBean.setDescription(searchResult.getDescription());
				searchResultBackBeansList.add(searchResultBackBean);	
		}
		
		return searchResultBackBeansList;
		
	}
	
}
