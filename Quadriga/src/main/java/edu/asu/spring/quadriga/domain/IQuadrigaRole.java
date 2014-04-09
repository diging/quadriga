package edu.asu.spring.quadriga.domain;

/**
 * @description   : interface to implement QuadrigaRoles class.
 * 
 * @author        : Kiran Kumar Batna
 * @author 		  : Ram Kumar Kumaresan
 */
public interface IQuadrigaRole 
{

	public abstract String getDBid();
	
	public abstract void setDBid(String dBid);
	
	public abstract void setId(String id);

	public abstract String getId();
	
	public abstract void setName(String name);

	public abstract String getName();
	
	public abstract String getDisplayName();
	
	public abstract void setDisplayName(String displayName);
	
	public abstract void setDescription(String description);

	public abstract String getDescription();

}
