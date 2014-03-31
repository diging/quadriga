package edu.asu.spring.quadriga.profile.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.impl.SearchResult;

/**
 * this class has method which creates new object of ISearchResult type
 * 
 * @author rohit
 *
 */
 
@Service
public class SearchResultFactory implements ISearchResultFactory {

	/**
	 * creates object of class which has ISearchResult reference
	 * 
	 * @return	object of SearchResult class
	 * 
	 */
	@Override
	public ISearchResult getSearchResultObject() {
		return new SearchResult();
	}

}
