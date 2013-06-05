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

	/**
	 * Compares this QuadrigaRole object to the specified QuadrigaRole object. 
	 * The result is true if and only if the argument is not null and is a QuadrigaRole object 
	 * that represents the same state(variable values) as this object.
	 * 
	 * @param role The QuadrigaRole object to which this object is to be compared with
	 * 
	 * @return TRUE - If both QuadrigaRole objects represent the same QuadrigaRole. FALSE - If there is any difference in the QuadrigaRole values.
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	public abstract boolean compareQuadrigaRole(IQuadrigaRole role);

}
