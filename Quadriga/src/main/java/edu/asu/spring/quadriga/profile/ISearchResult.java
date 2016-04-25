package edu.asu.spring.quadriga.profile;

/**
 * this interface has methods which are getters and setters for all the 
 * attributes of SearchResult class.
 * 
 * @author rohit pendbhaje
 *
 */
public interface ISearchResult {
	
	public void setDescription(String description);
	
	public String getDescription();
	
	public void setId(String id);
	
	public String getId();
	
	public String getName();
	
	public void setName(String name);

}
