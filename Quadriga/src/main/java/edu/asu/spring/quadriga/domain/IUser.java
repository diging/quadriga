package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * @description   	 interface to implement User class.
 * 
 * @author        	 Kiran Kumar Batna
 * @author 			 Ram Kumar Kumaresan
 */
public interface IUser 
{

	public abstract void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles);

	public abstract List<IQuadrigaRole> getQuadrigaRoles();

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUserName(String userName);

	public abstract String getUserName();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setEmail(String email);

	public abstract String getEmail();
	
	/**
	 * Get the DBid of the quadriga roles for this particular user. The values are separated by commas.
	 * 
	 * @return The Quadriga role DBids separated by commas.
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	public abstract String getQuadrigaRolesDBId();

	/**
	 * Compares this user object to the specified user object. 
	 * The result is true if and only if the argument is not null and is a User object 
	 * that represents the same state(variable values) as this object.
	 * 
	 * @param user The user object to which this object is to be compared with
	 * 
	 * @return TRUE - If both user objects represent the same user. FALSE - If there is any difference in the user values.
	 * 
	 * @author Ram Kumar Kumaresan
	 */
	public abstract boolean compareUserObjects(IUser user);
   
}
