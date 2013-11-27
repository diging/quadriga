package edu.asu.spring.quadriga.domain;

/**
 * @description   : interface to implement QuadrigaRoles class.
 * 
 * @author        : Kiran Kumar Batna
 * @author 		  : Ram Kumar Kumaresan
 */
public interface IQuadrigaRole 
{

	public String getDBid();
	
	public void setDBid(String dBid);
	
	public abstract void setDescription(String description);

	public abstract String getDescription();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setId(String id);

	public abstract String getId();

}
