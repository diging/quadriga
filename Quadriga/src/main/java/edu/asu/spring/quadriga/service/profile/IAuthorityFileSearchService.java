package edu.asu.spring.quadriga.service.profile;

import java.util.List;

import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

/**
 * the interface has methods which are used to copy all the contents from ISearchResult object to
 * SearchResultBackBean class object.
 * 
 * @author rohit pendbhaje
 *
 */
public interface IAuthorityFileSearchService {
	
/**
 * this method copies all the content of ISearchResult object to SearchResultBackBean object
 * 	
 * @param serviceId		serviceid of the SearchResultBackBean objects 
 * @param term			the term for which the searchresults need to be retrieved 
 * @return				List of SearchResultBackBean objects
 */
	
	public abstract List<SearchResultBackBean> searchInAuthorityFile(String serviceId, String term);

}
