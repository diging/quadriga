package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.profile.IServiceRegistry;

@Service
public class SearchResultBackBeanFormManager {
	
	
	@Autowired
	private IServiceRegistry serviceRegistry;
	
	@Autowired
	private SearchResultBackBeanForm backBeanForm;
	
	public List<SearchResultBackBean> getsearchResultBackBeanList(String serviceId, String term){
		
		
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
