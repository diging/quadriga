package edu.asu.spring.quadriga.profile.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.impl.SearchResult;

@Service
public class SearchResultFactory implements ISearchResultFactory {

	@Override
	public ISearchResult getSearchResultObject() {
		return new SearchResult();
	}

}
