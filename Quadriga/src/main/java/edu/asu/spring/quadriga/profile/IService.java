package edu.asu.spring.quadriga.profile;

import java.util.List;

/**
 * this interface has methods like search and getters and setters used by all service classes
 * 
 * @author rohit pendbhaje
 *
 */
public interface IService {
	
	public abstract String getServiceId();
	
	public abstract String getName();
	
	/**
	 * this method is used by all the service classes which implements this interface for searching the 
     * content for the particular service
	 * 
	 * @param word		the word user wants to search in the authority service like viaf.
	 * @return			list of search results.
	 */
	public abstract List<ISearchResult> search(String word);
	
	public static final String STARTINDEX = "1";

}
