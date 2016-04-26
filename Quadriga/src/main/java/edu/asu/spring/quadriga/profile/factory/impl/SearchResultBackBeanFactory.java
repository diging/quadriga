package edu.asu.spring.quadriga.profile.factory.impl;

import edu.asu.spring.quadriga.profile.ISearchResultBackBeanfactory;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

public class SearchResultBackBeanFactory implements ISearchResultBackBeanfactory {

	@Override
	public SearchResultBackBean createSearchResultBackBeanObject() {
		return new SearchResultBackBean();
	}
	
	

}
