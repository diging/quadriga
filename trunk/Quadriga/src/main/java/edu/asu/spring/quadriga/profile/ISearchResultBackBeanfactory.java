package edu.asu.spring.quadriga.profile;

import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

/**
 * this interface is used for creating SearchResultBackBean new object
 * 
 * methods:
 * createSearchResultBackBeanObject() : creates object of SearchResultBackBean
 * 
 * @author rohit pendbhaje
 *
 */
public interface ISearchResultBackBeanfactory {
	
	public SearchResultBackBean createSearchResultBackBeanObject();

}
