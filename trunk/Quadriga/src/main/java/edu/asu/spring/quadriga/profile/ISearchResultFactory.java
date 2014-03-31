package edu.asu.spring.quadriga.profile;

/**
 * this interface has method which creates new object of SearchResult class
 * 
 * @author rohit pendbhaje
 *
 */
public interface ISearchResultFactory {

/**
 * this method creates new object of ISearchResult class
 * 	
 * @return  object of ISearchResult class
 */
	public abstract ISearchResult getSearchResultObject();

}
