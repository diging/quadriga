package edu.asu.spring.quadriga.service.profile;

import java.util.List;

import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

/**
 * the interface used for setting the searchresults  retrieved from viaf service 
 * of SearchResult class type to SearchResultBackBean class
 * 
 * @author rohit pendbhaje
 *
 */
public interface ISearchResultBackBeanFormManager {
	
	public abstract List<SearchResultBackBean> getsearchResultBackBeanList(String serviceId, String term);

}
