package edu.asu.spring.quadriga.profile;

import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

/**
 * this interface has method which is used for creating new object of SearchResultBackBean class.
 * 
 *  @author rohit pendbhaje
 *
 */
public interface ISearchResultBackBeanfactory {

/**
 * this method creates object of SearchresultBackBean class
 * 
 * @return	object of SearchResultBackBean class
 */
	public SearchResultBackBean createSearchResultBackBeanObject();

}
