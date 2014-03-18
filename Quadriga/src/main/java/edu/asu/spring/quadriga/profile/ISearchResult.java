package edu.asu.spring.quadriga.profile;

/**
 * this interface is implemented by the domain searchresult class 
 * 
 * methods:
 * contains getters and setters for description, id and name attributes of searchresult class 
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
