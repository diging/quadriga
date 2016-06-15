package edu.asu.spring.quadriga.domain;

/**
 * @description   : interface to implement QuadrigaRoles class.
 * 
 * @author        : Kiran Kumar Batna
 * @author 		  : Ram Kumar Kumaresan
 */
public interface IQuadrigaRole 
{

	/**
	 * Method to retrieve quadriga role id stored in the database. 
	 * @return dBid- text containing the role id stored in the database. 
	 */
	public abstract String getDBid();
	
	/**
	 * Method to set the quadriga role id stored in the database. 
	 * @param dBid - text containing the role id stored in the database.  
	 */
	public abstract void setDBid(String dBid);
	
	/**
	 * Method to set the quadriga role id displayed in the UI pages.
	 * @param id - text containing the role id to be displayed in UI pages. 
	 */
	public abstract void setId(String id);

	/**
	 * Method to retrieve the quadriga role id displayed in the UI pages.
	 * @return id - text containing the role id to be displayed in UI pages. 
	 */
	public abstract String getId();
	
	/**
	 * Method to set the name of the quadriga role
	 * @param name - text contating the name associated with quadriga role.
	 */
	public abstract void setName(String name);

	/**
	 * Method to retrieve the name of the quadriga role
	 * @return name - text contating the name associated with quadriga role.
	 */
	public abstract String getName();
	
	/**
	 * Method to retrieve the display name of the quadriga role
	 * @return displayName text containing the name to be displayed for the quadriga role.
	 */
	public abstract String getDisplayName();
	
	/**
	 * Method to set the display name of the quadriga role
	 * @return displayName text containing the name to be displayed for the quadriga role.
	 */
	public abstract void setDisplayName(String displayName);
	
	/**
	 * Method to set the description associated with quadriga role
	 * @param description text containing the description of quadriga role.
	 */
	public abstract void setDescription(String description);

	/**
	 * Method to retrieve the description associated with quadriga role
	 * @return description text containing the description of quadriga role.
	 */
	public abstract String getDescription();

    public abstract void setSelectable(boolean selectable);

    public abstract boolean isSelectable();

}
